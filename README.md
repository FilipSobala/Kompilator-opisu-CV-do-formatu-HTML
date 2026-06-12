# Projekt TKiK: Kompilator opisu CV do formatu HTML

## Autorzy
* **Filip Sobala** fsobala@student.agh.edu.pl
* **Bartłomiej Przytuła** bartprzyt@student.agh.edu.pl

# CvDsl — Autorski język opisu CV

## 1. Założenia Projektu

* **Cel:** Tłumaczenie autorskiego formatu opisu CV (DSL) na nowoczesną stronę HTML z wbudowanymi stylami CSS.
* **Rodzaj translatora:** Kompilator.
* **Język implementacji:** Java.
* **Narzędzia:** Generator parserów **ANTLR v4**.
* **Główny atut:** Generowanie pojedynczego, gotowego do druku (PDF) pliku .html, który jest całkowicie niezależny od zewnętrznych plików stylów.

---

## 🛠️ Środowisko developerskie — co dodaliśmy do projektu

Oprócz samego kompilatora (Lexer/Parser/Visitor generowanych przez ANTLR), w ramach projektu stworzyliśmy **kompletne środowisko pracy w VS Code** dla języka CvDsl. Składa się ono z dwóch niezależnych elementów:

1. **Własne rozszerzenie VS Code (`cvdsl`)** — instalowane jednorazowo, daje edytorowi "świadomość" języka CvDsl (kolory, wcięcia, szablony).
2. **Automatyzacja budowy (Maven Task)** — wbudowana w projekt, pozwala jednym poleceniem skompilować i podglądnąć CV.

Razem dają efekt: piszesz `.cv` z pełnym kolorowaniem i podpowiedziami → jedno polecenie → gotowy HTML/PDF otwiera się sam w przeglądarce.

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
2. Naciśnij `Ctrl+Shift+P` → wpisz **`Run Task`** → wybierz **„Generuj CV z bieżącego pliku”**
3. Maven kompiluje projekt i odpala `Main.java` z argumentem będącym ścieżką do **aktualnie otwartego pliku** (`${file}`)
4. Kompilator:
   - parsuje plik (Lexer + Parser + Visitor z ANTLR)
   - generuje `output.html`
   - jeśli w `CONFIG` ustawiono `EXPORT_PDF: TRUE` — generuje też `output.pdf`
5. **`output.html` automatycznie otwiera się w domyślnej przeglądarce** — bez klikania w plik w eksploratorze

### Co zostało do tego zmienione w kodzie projektu?

| Plik | Zmiana | Dlaczego |
| :--- | :--- | :--- |
| `Main.java` | Ścieżka pliku wejściowego brana jest z argumentu programu: `args.length > 0 ? args[0] : "src/main/resources/test.cv"` | Pozwala kompilatorowi przetwarzać **dowolny** plik `.cv`, a nie tylko jeden zahardkodowany |
| `Main.java` | Po zapisie `output.html` dodano `Desktop.getDesktop().browse(outputPath.toUri())` | Automatyczne otwarcie wyniku w przeglądarce — bez tego trzeba było ręcznie szukać pliku w eksploratorze |
| `pom.xml` | Dodano plugin `org.codehaus.mojo:exec-maven-plugin` z ustawioną klasą główną `org.example.Main` | Pozwala odpalić `Main` jedną komendą Mavena (`mvn compile exec:java -Dexec.args=...`), bez ręcznego budowania classpath |
| `.vscode/tasks.json` | Nowy task **„Generuj CV z bieżącego pliku”**, typ `process`, wywołujący `mvn.cmd` z `-Dexec.args=${file}` | Spina wszystko w jedną akcję dostępną z palety komend (`Ctrl+Shift+P` → `Run Task`) |

### Wymagania / konfiguracja workspace

Aby task był widoczny w VS Code, jako **główny folder okna** (workspace root) musi być otwarty folder nadrzędny:

```
Kompilator-opisu-CV-do-formatu-HTML/
├── .vscode/              ← tu jest tasks.json
├── cvdsl/                ← rozszerzenie VS Code
└── Kompilator_CV_do_HTML/ ← projekt Java/Maven (pom.xml, src, Main.java)
```

(folder zawierający i `cvdsl`, i `Kompilator_CV_do_HTML`)

---

## 📋 Ściągawka na prezentację — co warto pokazać

1. **Otworzyć plik `.cv`** → pokazać kolorowanie (porównać np. z plikiem `.txt` — bez kolorów)
2. **Napisać nową sekcję snippetem** — wpisać `section` + `Tab`, albo `experience` + `Tab` i pokazać jak szybko wypełnia się strukturę
3. **Zwinąć/rozwinąć sekcję** — kliknąć strzałkę foldingu przy `SECTION ... {`
4. **Zakomentować linię** `Ctrl+/` — pokazać że wstawia `#` (zgodnie z `T_COMMENT` z gramatyki)
5. **Odpalić task „Generuj CV z bieżącego pliku”** → poczekać aż Maven skompiluje → przeglądarka sama otworzy `output.html`
6. (Opcjonalnie) zmienić coś w `.cv` (np. `ACCENT_COLOR`), ponownie odpalić task i pokazać że HTML się zaktualizował

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
