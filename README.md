# Projekt TKiK: Kompilator opisu CV do formatu HTML

## Autorzy
* **Filip Sobala** fsobala@student.agh.edu.pl
* **Bartłomiej Przytuła** bartprzyt@student.agh.edu.pl

---

# CvDsl — Autorski język opisu CV

## 1. Założenia Projektu

* **Cel:** Tłumaczenie autorskiego formatu opisu CV (DSL) na nowoczesną stronę HTML z wbudowanymi stylami CSS.
* **Rodzaj translatora:** Kompilator.
* **Język implementacji:** Java.
* **Narzędzia:** Generator parserów **ANTLR v4**.
* **Główny atut:** Generowanie pojedynczego, gotowego do druku (PDF) pliku .html, który jest całkowicie niezależny od zewnętrznych plików stylów.

---

## 🏗️ Architektura kompilatora — wzorzec Visitor i drzewo `Node`

Serce kompilatora to klasyczny potok **Lexer → Parser → AST → Generator wyjścia**, zaimplementowany przy pomocy wzorca projektowego **Visitor**, w pełni wygenerowanego i wspieranego przez ANTLR v4.

### Przepływ danych

```
plik .cv
   │
   ▼
CvDslLexer        (generowany przez ANTLR z CvDsl.g4)
   │  strumień tokenów (T_SECTION, T_KEY, T_STRING, ...)
   ▼
CvDslParser       (generowany przez ANTLR z CvDsl.g4)
   │  drzewo rozbioru (ParseTree)
   ▼
CvBuilder         (implementacja CvDslBaseVisitor<Node>)
   │  drzewo obiektów Node (AST naszego DSL)
   ▼
Cv.toHtml()       (rekurencyjne wywołania toHtml() na drzewie)
   │
   ▼
output_<nazwa>.html  (+ output_<nazwa>.pdf jeśli EXPORT_PDF: TRUE)
```

`CvBuilder` odwiedza drzewo rozbioru wygenerowane przez ANTLR (`ParseTree`) i dla każdego węzła gramatyki (`cv_document`, `section`, `pair`, `list_field`, `object_block`, ...) tworzy odpowiadający mu obiekt z **własnej hierarchii klas `Node`** — czyli drugie, "nasze" drzewo (AST), niezależne od wewnętrznej reprezentacji ANTLR. To na tym drugim drzewie operuje cała dalsza logika: generowanie HTML, CSS i PDF.

### Hierarchia klas `Node`

Wszystko w drzewie wynikowym implementuje jeden, bardzo prosty interfejs:

```java
public interface Node {
    String toHtml();
}
```

Dzięki temu **każdy element CV — od całego dokumentu, przez sekcję, po pojedyncze pole — wie, jak wyrenderować samego siebie do HTML**. Generowanie strony to po prostu rekurencyjne wywołanie `toHtml()` na korzeniu drzewa, które kaskadowo wywołuje `toHtml()` na wszystkich elementach potomnych.

| Klasa | Reprezentuje | Przykład z `.cv` |
| :--- | :--- | :--- |
| **`Cv`** | Cały dokument CV — korzeń drzewa. Przechowuje `ConfigNode` oraz listę sekcji `SectionNode`. To tutaj generowany jest cały szkielet HTML (`<html>`, `<head>`, style CSS zależne od `THEME`/`ACCENT_COLOR`) | Cały plik `.cv` od `CV_START` do `CV_END` |
| **`ConfigNode`** | Blok globalnej konfiguracji — lista par klucz-wartość steruje wygenerowanym CSS (kolory, motyw jasny/ciemny, widoczność zdjęcia, eksport PDF) | `CONFIG { LANG: "PL" THEME: "Modern-Dark" ... }` |
| **`SectionNode`** | Pojedyncza sekcja CV — ma nazwę i listę zawartości (`pair`, `list_field`, `object_list`, `bullet_list`). Renderuje się jako `<section><h2>Nazwa</h2>...</section>` | `SECTION Experience { ... }` |
| **`FieldNode`** | Pojedyncze pole klucz–wartość. Renderuje się jako `<div class="field"><span class="key">KLUCZ</span>WARTOŚĆ</div>` | `NAME: "Filip Sobala"` |
| **`ValueNode`** *(klasa abstrakcyjna)* | Wspólny przodek dla wszystkich możliwych typów wartości pola — pozwala przechowywać w jednej liście (np. `ListNode`) wartości różnych typów (stringi, liczby, obiekty, booleany) | — |
| **`StringNode`** | Wartość tekstowa — string, multiline `"""..."""`, URL, e-mail, telefon, data, `PRESENT`/`NOW` (wszystkie reprezentowane jako tekst) | `"Senior Java Engineer"`, `https://github.com/...` |
| **`NumberNode`** | Wartość liczbowa | `PROFICIENCY: 5` |
| **`BooleanNode`** | Wartość logiczna `TRUE`/`FALSE` | `IS_REMOTE: TRUE` |
| **`ListNode`** | Lista wartości — renderuje się jako `<ul><li>...</li></ul>`. Może zawierać dowolny `ValueNode`, w tym `ObjectNode` | `TECH_STACK: ["Java 21", "ANTLR v4"]`, `RESPONSIBILITIES: - "..." - "..."` |
| **`ObjectNode`** | Złożony obiekt (np. jeden wpis doświadczenia zawodowego) — lista pól `FieldNode`, renderowana jako `<div class="object">...</div>` z wizualnym wyróżnieniem (kolorowy lewy border, tło karty) | Pojedynczy `{ COMPANY: "Google" POSITION: "..." ... }` w `WORK_HISTORY` |

### Dlaczego to dobre rozwiązanie?

* **Separacja odpowiedzialności** — gramatyka ANTLR (`CvDsl.g4`) odpowiada wyłącznie za rozpoznanie składni, `CvBuilder` za zbudowanie modelu domenowego, a klasy `Node` za prezentację (HTML). Każdą z tych warstw można zmieniać niezależnie — np. dodać generowanie do innego formatu (Markdown, JSON) pisząc tylko nowe implementacje `toHtml()` → `toMarkdown()`, bez dotykania gramatyki czy `CvBuilder`.
* **Rekurencyjna budowa HTML "za darmo"** — ponieważ `ObjectNode` i `ListNode` same implementują `Node` i mogą zawierać inne `Node`/`ValueNode`, dowolnie zagnieżdżone struktury (lista obiektów, w których każdy obiekt ma pole będące listą) renderują się poprawnie bez żadnego specjalnego kodu — wystarczy że każdy poziom zna tylko swoje dzieci i wywołuje na nich `toHtml()`.
* **Centralny punkt stylowania** — cały CSS (kolory, layout, motyw) generowany jest w jednym miejscu (`Cv.toHtml()`) na podstawie `ConfigNode`, a poszczególne elementy (`field`, `object`, `section`) używają tylko nazwanych klas CSS — dzięki temu zmiana `ACCENT_COLOR` czy `THEME` w jednym pliku `.cv` zmienia wygląd całego wygenerowanego dokumentu, bez ingerencji w strukturę HTML.
* **Zgodność z Visitor pattern z ANTLR** — `CvBuilder extends CvDslBaseVisitor<Node>` to standardowy, podręcznikowy wzorzec budowania AST z drzewa rozbioru ANTLR, co czyni kod łatwym do rozszerzenia (nowe typy węzłów gramatyki = nowa metoda `visitXxx` zwracająca nowy `Node`).

---

## 🛠️ Środowisko developerskie — co dodaliśmy do projektu

Oprócz samego kompilatora (Lexer/Parser/Visitor generowanych przez ANTLR), w ramach projektu stworzyliśmy **kompletne środowisko pracy w VS Code** dla języka CvDsl. Składa się ono z czterech elementów:

1. **Własne rozszerzenie VS Code (`cvdsl`)** — instalowane jednorazowo, daje edytorowi "świadomość" języka CvDsl (kolory, wcięcia, szablony).
2. **Automatyzacja budowy (Maven Task + skrót klawiszowy)** — wbudowana w projekt, pozwala jednym klawiszem skompilować i podglądnąć CV.
3. **Zestaw przykładowych plików `.cv`** — gotowe demo pokazujące pełne możliwości języka w trzech różnych stylach.
4. **Obsługa błędów składniowych i semantycznych z integracją w edytorze** — błędy w pliku `.cv` pojawiają się jako czerwone podkreślenia i wpisy w panelu Problems, klikalne i z numerem linii.

Razem dają efekt: piszesz `.cv` z pełnym kolorowaniem i podpowiedziami → jeden klawisz → gotowy HTML/PDF otwiera się sam w przeglądarce, a jeśli coś jest nie tak — widzisz to od razu w edytorze.

---

## 🎨 Część 1 — Rozszerzenie VS Code dla języka CvDsl

Stworzyliśmy od zera (generatorem `yo code`) dedykowane rozszerzenie VS Code o identyfikatorze `cvdsl`, rozpoznające pliki z rozszerzeniem **`.cv`**.

### Instalacja (jedna komenda)

1. Sklonuj lub pobierz repozytorium
2. W terminalu przejdź do folderu z projektem i wpisz:

```bash
code --install-extension cvdsl/cvdsl-0.0.1.vsix
```

3. Zrestartuj VS Code

Od tej chwili każdy plik `.cv` jest automatycznie rozpoznawany jako język "CvDsl" (widać to w prawym dolnym rogu edytora).

### 1.1 Podświetlanie składni (syntax highlighting)

Najważniejsza funkcjonalność — pełne kolorowanie tokenów, **zgodne 1:1 z gramatyką ANTLR** zdefiniowaną w `CvDsl.g4`. Każdy typ tokenu z lexera dostał odpowiadający mu kolor:

| Element języka | Przykład w kodzie | Token z gramatyki |
| :--- | :--- | :--- |
| Znaczniki dokumentu | `CV_START`, `CV_END` | `T_START`, `T_END` |
| Słowa kluczowe | `CONFIG`, `SECTION`, `IMPORT` | `T_CONFIG`, `T_SECTION`, `T_IMPORT` |
| Nazwy sekcji | `Personal_Info`, `Experience` | `T_LABEL` |
| Klucze pól | `NAME:`, `START_DATE:`, `TECH_STACK:` | `T_KEY` |
| Stringi | `"Filip Sobala"` | `T_STRING` |
| Bloki wieloliniowe | `"""..."""` | `T_MULTILINE` |
| Wartości logiczne | `TRUE`, `FALSE` | `T_BOOLEAN` |
| Wartości specjalne | `PRESENT`, `NOW` | `T_PRESENT` |
| Daty | `2024-07` | `T_DATE` |
| Liczby | `128`, `5` | `T_NUMBER` |
| Adresy URL | `https://github.com/...` | `T_URL` |
| Adresy e-mail | `fsobala@student.agh.edu.pl` | `T_EMAIL` |
| Numery telefonu | `+48 123 456 789` | `T_PHONE` |
| Komentarze liniowe | `# komentarz` | `T_COMMENT` |
| Komentarze blokowe | `/* blok */` | `T_BLOCK_COMM` |
| Nawiasy / separatory | `{ } [ ] , -` | `T_LBRACE`, `T_RBRACE`, `T_LSQUARE`, `T_RSQUARE`, `T_COMMA`, `T_DASH` |

**Efekt praktyczny:** plik `.cv` wygląda teraz jak kod w "prawdziwym" języku programowania — od razu widać strukturę dokumentu, łatwo zauważyć literówkę w słowie kluczowym (inny kolor = błąd), a stringi, daty i e-maile wyraźnie się wyróżniają.

### 1.2 Konfiguracja edytora (language-configuration.json)

Plik `language-configuration.json` definiuje, jak edytor zachowuje się podczas pisania w `.cv`:

* **Automatyczne zamykanie nawiasów** — wpisanie `{`, `[` lub `"` automatycznie dodaje domykający znak (`}`, `]`, `"`)
* **Automatyczne wcięcia** — naciśnięcie Enter po `{` lub `[` przesuwa kursor o tabulator, a po `}`/`]` zmniejsza wcięcie — dzięki temu struktura `SECTION { ... }` i listy `[ ... ]` formatują się same, tak jak w JSON
* **Zwijanie bloków (code folding)** — przy każdej linii zawierającej `{` pojawia się strzałka pozwalająca zwinąć cały blok (np. całą `SECTION Experience { ... }`), co bardzo pomaga przy długich CV z wieloma sekcjami
* **Komentowanie `Ctrl+/`** — zaznaczenie linii i `Ctrl+/` wstawia/usuwa `#` na początku — działa identycznie jak w Pythonie, zgodnie z `T_COMMENT` z gramatyki
* **Komentarze blokowe `/* */`** — wspierane jako alternatywa dla dłuższych notatek (`T_BLOCK_COMM`)

### 1.3 Snippety — szablony przyspieszające pisanie CV

Plik `snippets/cvdsl.json` zawiera gotowe szablony. Wpisujesz **prefix** i naciskasz `Tab`, a VS Code wstawia od razu całą strukturę z polami do wypełnienia (kursor przeskakuje między nimi po `Tab`):

| Prefix | Co generuje | Przykładowe zastosowanie |
| :--- | :--- | :--- |
| `cvdoc` | Szkielet całego dokumentu: `CV_START`, blok `CONFIG { ... }` z domyślnymi ustawieniami (motyw, kolor akcentu, eksport PDF) i `CV_END` | Start nowego pliku CV od zera |
| `section` | `SECTION ${Name} { }` | Dodanie nowej sekcji (np. `Education`, `Hobbies`) |
| `field` | `KEY: "value"` | Pojedyncze pole tekstowe, np. `ROLE: "Backend Developer"` |
| `bullets` | Lista wypunktowana z myślnikami (`- "..."`) | Lista obowiązków, osiągnięć |
| `objlist` | `KEY: [ { } ]` — lista obiektów w nawiasach kwadratowych | Tablica `WORK_HISTORY`, `DEGREES`, `LANGUAGES` |
| `multiline` | Blok `""" """` | Dłuższy opis (np. `SUMMARY`, `DESCRIPTION`, `RODO`) |
| `experience` | Cały gotowy obiekt doświadczenia zawodowego: `COMPANY`, `POSITION`, `START_DATE`, `END_DATE`, `IS_REMOTE`, `RESPONSIBILITIES` (lista myślnikowa) | Jednym ruchem dodajesz kompletny wpis do `WORK_HISTORY` |

**Efekt praktyczny:** napisanie kompletnego wpisu o pracy (firma, stanowisko, daty, lista obowiązków) to wpisanie `experience` + `Tab` + wypełnienie kilku pól przez `Tab`, zamiast ręcznego pisania kilkunastu linii ze znakami `{`, `}`, `"`, `-`.

---

## ⚙️ Część 2 — Automatyzacja: generowanie CV jednym klawiszem

Drugi element środowiska to **VS Code Task**, który łączy edycję pliku `.cv` z kompilatorem napisanym w Javie — bez ręcznego wpisywania komend w terminalu i bez edytowania ścieżek w kodzie.

### Jak to wygląda w użyciu?

1. Otwórz w VS Code dowolny plik `.cv` (np. `test.cv`) i ustaw go jako aktywną zakładkę
2. Naciśnij **`F6`** (na laptopach z klawiszami funkcyjnymi multimedialnymi: **`Fn+F6`**) — albo `Ctrl+Shift+P` → `Run Task` → **„Generuj CV z bieżącego pliku”**
3. Maven kompiluje projekt i odpala `Main.java` z argumentem będącym ścieżką do **aktualnie otwartego pliku** (`${file}`)
4. Kompilator:
   - parsuje plik (Lexer + Parser + Visitor z ANTLR)
   - generuje plik HTML o nazwie zależnej od pliku wejściowego, np. `output_test.html` dla `test.cv`
   - jeśli w `CONFIG` ustawiono `EXPORT_PDF: TRUE` — generuje też odpowiadający plik PDF (np. `output_test.pdf`)
5. **Wygenerowany plik HTML automatycznie otwiera się w domyślnej przeglądarce** — bez klikania w plik w eksploratorze

### Co zostało do tego zmienione w kodzie projektu?

| Plik | Zmiana | Dlaczego |
| :--- | :--- | :--- |
| `Main.java` | Ścieżka pliku wejściowego brana jest z argumentu programu: `args.length > 0 ? args[0] : "src/main/resources/test.cv"` | Pozwala kompilatorowi przetwarzać **dowolny** plik `.cv`, a nie tylko jeden zahardkodowany |
| `Main.java` | Nazwa wynikowego pliku jest wyliczana z nazwy pliku wejściowego: `output_<nazwa>.html` / `output_<nazwa>.pdf` | Każdy plik `.cv` ma swój własny, niezależny output — łatwo trzymać wiele CV w jednym projekcie bez nadpisywania się |
| `Main.java` | Po zapisie HTML dodano `Desktop.getDesktop().browse(outputPath.toUri())` | Automatyczne otwarcie wyniku w przeglądarce — bez tego trzeba było ręcznie szukać pliku w eksploratorze |
| `pom.xml` | Dodano plugin `org.codehaus.mojo:exec-maven-plugin` z ustawioną klasą główną `org.example.Main` | Pozwala odpalić `Main` jedną komendą Mavena (`mvn compile exec:java -Dexec.args=...`), bez ręcznego budowania classpath |
| `.vscode/tasks.json` | Nowy task **„Generuj CV z bieżącego pliku”**, typ `process`, wywołujący `mvn.cmd` z `-Dexec.args=${file}` | Spina wszystko w jedną akcję dostępną z palety komend (`Ctrl+Shift+P` → `Run Task`) |
| `keybindings.json` | Skrót **`F6`** (na niektórych laptopach `Fn+F6`) powiązany z taskiem „Generuj CV z bieżącego pliku”, aktywny tylko gdy edytowany jest plik `.cv` | Generowanie CV jednym naciśnięciem klawisza, bez przechodzenia przez palety komend |
| `.gitignore` | Wykluczono `target/`, `*.class`, `output*.html`, `output*.pdf`, `*.vsix`, `.vscode/launch.json` | Repozytorium nie zaśmieca się plikami wygenerowanymi i lokalnymi konfiguracjami — dobra praktyka przy pracy zespołowej |

### Wymagania / konfiguracja workspace

Aby task i skrót `F6` były widoczne w VS Code, jako **główny folder okna** (workspace root) musi być otwarty folder nadrzędny:

```
Kompilator-opisu-CV-do-formatu-HTML/
├── .vscode/              ← tu jest tasks.json
├── cvdsl/                ← rozszerzenie VS Code
└── Kompilator_CV_do_HTML/ ← projekt Java/Maven (pom.xml, src, Main.java)
```

(folder zawierający i `cvdsl`, i `Kompilator_CV_do_HTML`)

#### `.gitignore` (folder główny projektu)

```gitignore
# Java / Maven
target/
*.class

# Wygenerowane pliki CV
output*.html
output*.pdf

# VS Code
.vscode/launch.json
*.vsix
```

#### `.vscode/keybindings.json` (skrót F6)

```jsonc
[
    {
        "key": "f6",
        "command": "workbench.action.tasks.runTask",
        "args": "Generuj CV z bieżącego pliku",
        "when": "editorTextFocus && resourceExtname == '.cv'"
    }
]
```

---

## 🚨 Część 3 — Obsługa błędów: czerwone podkreślenia w edytorze

Trzeci element środowiska to integracja błędów kompilatora z edytorem VS Code. Zarówno **błędy składniowe** (wykrywane przez ANTLR), jak i **błędy semantyczne** (wykrywane przez nasz kod w `CvBuilder`) są wypisywane w jednym, ustandaryzowanym formacie i przechwytywane przez VS Code jako czerwone podkreślenia w edytorze oraz wpisy w panelu **Problems** (`Ctrl+Shift+M`), klikalne i prowadzące do konkretnej linii.

### 3.1 Błędy składniowe — `CvErrorListener`

Domyślnie ANTLR wypisuje błędy składniowe do konsoli w formacie nieczytelnym dla VS Code (np. `line 5:3 mismatched input...`). Stworzyliśmy własną klasę `CvErrorListener`, podpiętą zarówno do lexera, jak i parsera, która formatuje każdy błąd jako:

```
<ścieżka_do_pliku>:<linia>:<kolumna>: error: <komunikat>
```

```java
package org.example;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class CvErrorListener extends BaseErrorListener {

    private final String filePath;

    public CvErrorListener(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                             int line, int charPositionInLine, String msg,
                             RecognitionException e) {
        System.err.printf("%s:%d:%d: error: %s%n",
                filePath, line, charPositionInLine + 1, msg);
    }
}
```

Podpięcie w `Main.java`:

```java
CvDslLexer lexer = new CvDslLexer(input);
lexer.removeErrorListeners();
lexer.addErrorListener(new CvErrorListener(filePath));

CvDslParser parser = new CvDslParser(tokens);
parser.removeErrorListeners();
parser.addErrorListener(new CvErrorListener(filePath));
```

**Przykład:** brak `}` zamykającego sekcję → w edytorze pojawia się czerwone podkreślenie w odpowiedniej linii, a w panelu Problems wpis typu `mismatched input '<EOF>' expecting '}'`.

### 3.2 Błędy semantyczne — walidacja w `CvBuilder`

Sam parser sprawdza tylko, czy plik jest *poprawny gramatycznie* — nie wie nic o sensie dokumentu. Dlatego w `CvBuilder` dodaliśmy dodatkowe sprawdzenia, które używają **tego samego formatu błędów** (`plik:linia:kolumna: error: ...`), dzięki czemu trafiają do tego samego panelu Problems:

* **Zduplikowana sekcja** — jeśli w dokumencie dwa razy pojawia się `SECTION` o tej samej nazwie (np. dwa razy `Experience`), zgłaszany jest błąd `Zduplikowana sekcja: "Experience"`
* **Wymagane pola w `Personal_Info`** — sekcja `Personal_Info` musi zawierać pola `NAME` i `EMAIL`; jeśli któregoś brakuje, zgłaszany jest błąd `Sekcja "Personal_Info": brakuje wymaganego pola NAME` (analogicznie dla `EMAIL`)

```java
private void validatePersonalInfo(CvDslParser.SectionContext ctx) {
    boolean hasName = false;
    boolean hasEmail = false;

    for (CvDslParser.ContentContext c : ctx.content()) {
        if (c.pair() != null) {
            String key = stripKey(c.pair().T_KEY().getText());
            if (key.equals("NAME")) hasName = true;
            if (key.equals("EMAIL")) hasEmail = true;
        }
    }

    if (!hasName) {
        reportError(ctx, "Sekcja \"Personal_Info\": brakuje wymaganego pola NAME");
    }
    if (!hasEmail) {
        reportError(ctx, "Sekcja \"Personal_Info\": brakuje wymaganego pola EMAIL");
    }
}

private void reportError(org.antlr.v4.runtime.ParserRuleContext ctx, String message) {
    System.err.printf("%s:%d:%d: error: %s%n",
            filePath,
            ctx.getStart().getLine(),
            ctx.getStart().getCharPositionInLine() + 1,
            message);
}
```

Każdy `ParserRuleContext` z ANTLR niesie informację o tym, w której linii i kolumnie się zaczyna (`getStart().getLine()`, `getStart().getCharPositionInLine()`) — dzięki temu błąd semantyczny wskazuje **konkretne miejsce w pliku `.cv`**, mimo że plik jest składniowo w 100% poprawny.

### 3.3 Problem Matcher — most między konsolą Mavena a edytorem

Aby VS Code "zrozumiał" linie błędów wypisywane przez `CvErrorListener` i walidację semantyczną, w `.vscode/tasks.json` zdefiniowano `problemMatcher` z wyrażeniem regularnym dopasowującym nasz format:

```json
"problemMatcher": {
    "owner": "cvdsl",
    "fileLocation": ["absolute"],
    "pattern": {
        "regexp": "^(.*):(\\d+):(\\d+): error: (.*)$",
        "file": 1,
        "line": 2,
        "column": 3,
        "message": 4
    }
}
```

Dzięki temu każde naciśnięcie `F6` powoduje, że Maven wypisuje błędy w konsoli, a VS Code automatycznie:
- podkreśla odpowiednią linię w pliku `.cv` na czerwono,
- dodaje wpis do panelu **Problems** z treścią błędu,
- pozwala kliknąć wpis i przeskoczyć do dokładnego miejsca błędu.

### Co zostało do tego zmienione w kodzie projektu?

| Plik | Zmiana | Dlaczego |
| :--- | :--- | :--- |
| `CvErrorListener.java` *(nowy plik)* | Klasa formatująca błędy ANTLR jako `plik:linia:kolumna: error: komunikat` | Wspólny, ustandaryzowany format błędów rozumiany przez Problem Matcher |
| `Main.java` | Podpięcie `CvErrorListener` do lexera i parsera (`removeErrorListeners()` + `addErrorListener(...)`) | Zastąpienie domyślnych, nieczytelnych komunikatów ANTLR naszym formatem |
| `Main.java` | `CvBuilder` przyjmuje teraz `filePath` w konstruktorze (`new CvBuilder(filePath)`) | Umożliwia walidacji semantycznej zgłaszanie błędów z poprawną ścieżką do pliku |
| `CvBuilder.java` | Dodano `Set<String> sectionNames` do wykrywania duplikatów sekcji oraz metody `validatePersonalInfo(...)` i `reportError(...)` | Walidacja semantyczna (duplikaty sekcji, wymagane pola `NAME`/`EMAIL` w `Personal_Info`) w tym samym formacie co błędy składniowe |
| `.vscode/tasks.json` | Rozbudowano `problemMatcher` z `[]` na obiekt z regexem `^(.*):(\\d+):(\\d+): error: (.*)$` | Pozwala VS Code parsować output Mavena i pokazywać błędy jako podkreślenia/Problems |

> **Uwaga:** podkreślenia pojawiają się **po naciśnięciu `F6`** (czyli po kompilacji), nie w trakcie pisania na żywo. Pełne "live" podkreślanie wymagałoby napisania osobnego Language Servera (LSP) — to świadomie pominięty, znacznie większy temat.

---

## 📁 Część 4 — Przykładowe pliki .cv (gotowe demo)

W `src/main/resources/` przygotowaliśmy **trzy kompletne, realistyczne CV**, każde wykorzystujące inny wariant konfiguracji (`CONFIG`) — żeby na jednym i tym samym kompilatorze pokazać kilka różnych stylów wynikowego dokumentu. Wszystkie trzy pliki wykorzystują **pełny zestaw konstrukcji języka**: sekcje, listy obiektów (`object_list`), listy wypunktowane (`bullet_list`), listy w `[ ]`, bloki wieloliniowe `"""..."""`, daty, `PRESENT`/`NOW`, adresy URL, e-mail, telefon i liczby.

| Plik | Motyw / `CONFIG` | Co pokazuje |
| :--- | :--- | :--- |
| `example_light.cv` | `THEME: "Modern-Light"`, `ACCENT_COLOR: "#2196F3"`, `SHOW_PHOTO: TRUE`, `EXPORT_PDF: TRUE` | Pełne CV (Anna Kowalska, Frontend Developer) — jasny motyw, zdjęcie, eksport do PDF |
| `example_dark.cv` | `THEME: "Modern-Dark"`, `ACCENT_COLOR: "#4CAF50"`, `SHOW_PHOTO: TRUE`, `EXPORT_PDF: TRUE` | Pełne CV (Filip Sobala) — ciemny motyw, zdjęcie, eksport do PDF |
| `example_minimal.cv` | `THEME: "Minimal"`, `ACCENT_COLOR: "#9C27B0"`, `SHOW_PHOTO: FALSE`, `EXPORT_PDF: FALSE`, `LANG: "EN"` | Skrócone CV po angielsku (Marcus Webb, Data Analyst) — wersja minimalistyczna, bez zdjęcia, bez PDF |

**Efekt praktyczny na prezentacji:** otwierasz po kolei każdy plik i naciskasz `F6`/`Fn+F6` — przeglądarka pokazuje 3 różne, sensowne CV w 3 różnych stylach (każdy zapisany do osobnego pliku `output_<nazwa>.html`), mimo że **kompilator i gramatyka są identyczne**. To najlepszy dowód, że `CONFIG` realnie steruje wyglądem strony wynikowej, a logika tłumaczenia DSL→HTML jest od tego niezależna.

---

## 2. Analiza Leksykalna (Spis Tokenów)

Skaner (Lexer) przetwarza plik wejściowy na strumień tokenów. 

| Nazwa tokenu | Wzór (Regex / Opis) | Opis |
| :--- | :--- | :--- |
| **`T_START`** | `CV_START` | Znacznik rozpoczęcia dokumentu |
| **`T_END`** | `CV_END` | Znacznik zakończenia dokumentu |
| **`T_CONFIG`** | `CONFIG` | Słowo kluczowe definiujące globalne ustawienia (motyw, język) |
| **`T_SECTION`** | `SECTION` | Słowo kluczowe definiujące nową sekcję |
| **`T_IMPORT`** | `IMPORT` | Słowo kluczowe do wczytywania zewnętrznych plików |
| **`T_LABEL`** | `[a-zA-Z]([a-zA-Z0-9_]*[a-zA-Z0-9])?` | Nazwa sekcji (bez `_` na początku i końcu, np. `Experience`) |
| **`T_KEY`** | `[A-Z_]+:` | Klucz pola danych (np. `NAME:`, `EMAIL:`, `START_DATE:`) |
| **`T_STRING`** | `\"(.*?)\"` | Dowolna wartość tekstowa ujęta w cudzysłów (wsparcie UTF-8) |
| **`T_MULTILINE`** | `\"\"\"[\s\S]*?\"\"\"` | Wielowierszowy blok tekstu (do długich opisów stanowisk) |
| **`T_DATE`** | `[0-9]{4}-[0-9]{2}` | Data w formacie RRRR-MM |
| **`T_PRESENT`** | `PRESENT` \| `NOW` | Wartość oznaczająca trwające wydarzenie (np. obecna praca) |
| **`T_URL`** | `https?://[^\s"']+` | Adres URL (np. do GitHub, LinkedIn, portfolio) |
| **`T_EMAIL`** | `[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}` | Bezpośrednie rozpoznawanie adresu e-mail |
| **`T_PHONE`** | `\+?[0-9][0-9\s\-]{8,14}` | Rozpoznawanie numeru telefonu |
| **`T_NUMBER`** | `[0-9]+` | Liczby całkowite (np. do określania poziomu umiejętności 1-5) |
| **`T_BOOLEAN`** | `TRUE` \| `FALSE` | Wartości logiczne (np. do ukrywania/pokazywania sekcji) |
| **`T_LBRACE`** | `{` | Klamra otwierająca blok danych (obiekt) |
| **`T_RBRACE`** | `}` | Klamra zamykająca blok danych (obiekt) |
| **`T_LSQUARE`** | `[` | Otwarcie listy elementów |
| **`T_RSQUARE`** | `]` | Zamknięcie listy elementów |
| **`T_COMMA`** | `,` | Separator elementów na liście |
| **`T_DASH`** | `-` | Myślnik (np. do tworzenia wypunktowań wewnątrz sekcji `T_MULTILINE`) |
| **`T_COMMENT`** | `#.*` | Komentarz jednowierszowy (pomijany `-> skip`) |
| **`T_BLOCK_COMM`**| `/\*[\s\S]*?\*/` | Komentarz wielowierszowy (pomijany `-> skip`) |
| **`WS`** | `[ \t\r\n]+` | Znaki białe (pomijane automatycznie `-> skip`) |

---

## 3. Analiza Składniowa (Gramatyka)

Logiczna struktura języka zdefiniowana w formacie ANTLR:

```antlr
grammar CvDsl;

// Punkt wejścia dokumentu
cv_document  : T_START import_stmt* config_block? section+ T_END ;

// Importowanie zewnętrznych plików
import_stmt  : T_IMPORT T_STRING ;

// Globalna konfiguracja
config_block : T_CONFIG T_LBRACE pair* T_RBRACE ;

// Główna sekcja CV
section      : T_SECTION T_LABEL T_LBRACE content* T_RBRACE ;

// Elementy dozwolone wewnątrz sekcji i obiektów
content      : pair 
             | list_field 
             | object_list 
             | bullet_list ;

// Przypisanie klucz-wartość (np. NAME: "Filip")
pair         : T_KEY value ;

// Lista w nawiasach kwadratowych z opcjonalnym przecinkiem na końcu
list_field   : T_KEY T_LSQUARE (value (T_COMMA value)* T_COMMA?)? T_RSQUARE ;

// Lista wypunktowana oparta na myślnikach (np. TASKS: - "Zadanie 1" - "Zadanie 2")
bullet_list  : T_KEY (T_DASH value)+ ;

// Lista złożonych obiektów (np. doświadczenie, edukacja)
object_list  : T_KEY T_LSQUARE (object_block (T_COMMA object_block)* T_COMMA?)? T_RSQUARE ;

// Definicja pojedynczego obiektu wewnątrz listy
object_block : T_LBRACE content* T_RBRACE ;

// Wszystkie rozpoznawane i dozwolone typy wartości (zbiorczo)
value        : T_STRING 
             | T_MULTILINE 
             | T_DATE 
             | T_YEAR 
             | T_PRESENT 
             | T_URL 
             | T_EMAIL 
             | T_PHONE 
             | T_NUMBER 
             | T_BOOLEAN ;
```

---

## 4. Przykład poprawnej składni (Input Example)

Przykładowy plik tekstowy (.txt), który stanowi wejście dla kompilatora:

```text

CV_START

CONFIG {
    LANG: "PL"
    THEME: "Modern-Dark"
    SHOW_PHOTO: TRUE
    ACCENT_COLOR: "#4CAF50"
    EXPORT_PDF: TRUE
}

SECTION Personal_Info {
    NAME: "Filip Sobala"
    ROLE: "Senior Java & ANTLR Engineer"
    EMAIL: fsobala@student.agh.edu.pl
    PHONE: +48 123 456 789
}

SECTION Profile {
    SUMMARY: """
    Doświadczony inżynier oprogramowania z pasją do tworzenia języków 
    dziedzinowych (DSL) oraz narzędzi deweloperskich. Specjalizuję się 
    w architekturze JVM, gramatykach ANTLR v4 i optymalizacji parserów.
    Poszukuję wyzwań przy budowie systemów o wysokiej dostępności.
    """
}

SECTION Experience {
    WORK_HISTORY: [
        {
            COMPANY: "Google"
            POSITION: "Software Engineer"
            START_DATE: 2024-07
            END_DATE: PRESENT
            IS_REMOTE: TRUE
            
            # Zastosowanie listy wypunktowanej opartej na myślnikach
            RESPONSIBILITIES: 
                - "Projektowanie i rozwój wewnętrznych języków konfiguracyjnych."
                - "Optymalizacja silnika parsowania, redukcja czasu kompilacji o 35%."
                - "Współpraca z zespołami Cloud Infrastructure."
                
            # Zastosowanie klasycznej listy w nawiasach kwadratowych
            TECH_STACK: ["Java 21", "ANTLR v4", "Spring Boot", "Kubernetes"]
        },
        {
            COMPANY: "Tech Corp Sp. z o.o."
            POSITION: "Junior Java Developer"
            START_DATE: 2022-03
            END_DATE: 2024-06
            IS_REMOTE: FALSE
            RESPONSIBILITIES: 
                - "Utrzymanie systemów legacy opartych na Java EE."
                - "Migracja monolitu do architektury mikroserwisów."
                - "Pisanie testów (JUnit 5, Testcontainers)."
            TECH_STACK: ["Java 11", "PostgreSQL", "Docker"]
        }
    ]
}

SECTION Education {
    DEGREES: [
        {
            UNIVERSITY: "Akademia Górniczo-Hutnicza w Krakowie"
            FACULTY: "Informatyka, Wydział IET"
            DEGREE: "Magister Inżynier"
            START_YEAR: 2021
            END_YEAR: 2026
            ACHIEVEMENTS: 
                - "Stypendium Rektora dla najlepszych studentów"
                - "Przewodniczący Koła Naukowego Kompilatorów"
        }
    ]
}

SECTION Skills {
    PROGRAMMING: ["Java", "Kotlin", "Python", "SQL"]
    TOOLS: ["Git", "Maven", "Gradle", "Docker", "IntelliJ IDEA"]
    
    # Ocena umiejętności miękkich i językowych w skali liczbowej (1-5)
    LANGUAGES: [
        {
            LANGUAGE: "Angielski"
            LEVEL_NAME: "C1 (Advanced)"
            PROFICIENCY: 5
        },
        {
            LANGUAGE: "Niemiecki"
            LEVEL_NAME: "A2 (Basic)"
            PROFICIENCY: 2
        }
    ]
}

SECTION Projects {
    OPEN_SOURCE: [
        {
            NAME: "CvDslCompiler"
            URL: https://github.com/fsobala/CvDslCompiler
            IS_ACTIVE: TRUE
            STARS: 128
            DESCRIPTION: """
            Autorski kompilator transformujący pliki tekstowe na gotowe 
            do druku strony HTML/PDF. Zbudowany na architekturze Visitor.
            Wspiera walidację semantyczną drzewa AST oraz fail-fast parser.
            """
        }
    ]
}

SECTION Clauses {
    RODO: """
    Wyrażam zgodę na przetwarzanie moich danych osobowych dla potrzeb niezbędnych 
    do realizacji procesu rekrutacji (zgodnie z ustawą z dnia 10 maja 2018 roku 
    o ochronie danych osobowych ).
    """
}

CV_END
```