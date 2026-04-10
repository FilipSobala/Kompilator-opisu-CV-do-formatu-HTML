# Projekt TKiK: Kompilator opisu CV do formatu HTML

## Autorzy
* **Filip Sobala** fsobala@student.agh.edu.pl
* **Bartłomiej Przytuła** bartprzyt@student.agh.edu.pl

## 1. Założenia Projektu

* **Cel:** Tłumaczenie autorskiego formatu opisu CV (DSL) na nowoczesną stronę HTML z wbudowanymi stylami CSS.
* **Rodzaj translatora:** Kompilator.
* **Język implementacji:** Java.
* **Narzędzia:** Generator parserów **ANTLR v4**.
* **Główny atut:** Generowanie pojedynczego, gotowego do druku (PDF) pliku .html, który jest całkowicie niezależny od zewnętrznych plików stylów.

---

## 2. Analiza Leksykalna (Spis Tokenów)

Skaner (Lexer) przetwarza plik wejściowy na strumień tokenów według poniższej specyfikacji:

| Nazwa tokenu | Wzór (Regex / Opis) | Opis |
| :--- | :--- | :--- |
| **`T_START`** | `CV_START` | Znacznik rozpoczęcia dokumentu |
| **`T_END`** | `CV_END` | Znacznik zakończenia dokumentu |
| **`T_SECTION`** | `SECTION` | Słowo kluczowe definiujące nową sekcję |
| **`T_LABEL`** | `[a-zA-Z]([a-zA-Z0-9_]*[a-zA-Z0-9])?` | Nazwa sekcji (bez `_` na początku i końcu) |
| **`T_KEY`** | `[A-Z_]+:` | Klucz pola danych (np. `NAME:`, `EMAIL:`) |
| **`T_STRING`** | `\"(.*?)\"` | Dowolna wartość tekstowa ujęta w cudzysłów |
| **`T_LBRACE`** | `{` | Klamra otwierająca blok danych |
| **`T_RBRACE`** | `}` | Klamra zamykająca blok danych |
| **`T_LSQUARE`** | `[` | Otwarcie listy elementów |
| **`T_RSQUARE`** | `]` | Zamknięcie listy elementów |
| **`T_COMMA`** | `,` | Separator elementów na liście |
| **`T_COMMENT`** | `#.*` | Komentarz jednowierszowy (pomijany) |
| **`WS`** | `[ \t\r\n]+ -> skip` | Znaki białe (pomijane automatycznie) |

---

## 3. Analiza Składniowa (Gramatyka)

Logiczna struktura języka zdefiniowana w formacie ANTLR:

```antlr
cv_document : T_START section+ T_END ;
section     : T_SECTION T_LABEL T_LBRACE content* T_RBRACE ;
content     : pair | list_field ;
pair        : T_KEY T_STRING ;
list_field  : T_KEY T_LSQUARE T_STRING (T_COMMA T_STRING)* T_RSQUARE ;
```

---

## 4. Przykład poprawnej składni (Input Example)

Przykładowy plik tekstowy (.txt), który stanowi wejście dla kompilatora:

```text
# To jest przykładowe CV
CV_START

SECTION Personal_Data {
    NAME: "Filip Sobala"
    EMAIL: "fsobala@student.agh.edu.pl"
}

SECTION Experience {
    JOB: "Java Developer"
    COMPANY: "Google"
}

SECTION Skills {
    TECH_STACK: ["Java", "ANTLR", "Spring_Boot"]
}

CV_END
```