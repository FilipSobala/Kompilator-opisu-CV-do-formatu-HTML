# Projekt TKiK: Kompilator opisu CV do formatu HTML

## Autorzy
* **Filip Sobala** fsobala@student.agh.edu.pl
* **Bartłomiej Przytuła** bartprzyt@student.agh.edu.pl

## 1. Założenia programu

* **Cel programu:** Tłumaczenie autorskiego, prostego formatu tekstowego na w pełni sformatowaną, nowoczesną stronę CV w języku HTML.
* **Rodzaj translatora:** Kompilator.
* **Planowany wynik działania:** Program pobiera plik tekstowy (np. `moje_cv.txt`) i generuje na jego podstawie plik `.html` (wraz z wbudowanym ostylowaniem CSS), gotowy do wyświetlenia w przeglądarce i łatwego eksportu do formatu PDF.
* **Język implementacji:** Java.
* **Sposób realizacji skanera i parsera:** Użycie zewnętrznego generatora skanerów i parserów **ANTLR v4** (narzędzie wygeneruje kod w języku Java na podstawie napisanej przez nas gramatyki).

## 2. Analiza Leksykalna (Spis Tokenów)

Skaner (Lexer) przetwarza plik wejściowy na strumień tokenów według poniższej specyfikacji.

| Nazwa tokenu | Wzór (Regex / Opis) | Opis |
| :--- | :--- | :--- |
| **`T_START`** | `CV_START` | Znacznik rozpoczęcia dokumentu |
| **`T_END`** | `CV_END` | Znacznik zakończenia dokumentu |
| **`T_SECTION`** | `SECTION` | Słowo kluczowe definiujące nową sekcję |
| **`T_LABEL`** | `[a-zA-Z]([a-zA-Z0-9_]*[a-zA-Z0-9])?` | Nazwa sekcji (nie może zaczynać/kończyć się podkreślnikiem) |
| **`T_KEY`** | `[A-Z_]+:` | Klucz pola danych (np. `NAME:`, `EMAIL:`) |
| **`T_STRING`** | `\"(.*?)\"` | Dowolna wartość tekstowa ujęta w cudzysłów |
| **`T_LBRACE`** | `{` | Klamra otwierająca blok danych |
| **`T_RBRACE`** | `}` | Klamra zamykająca blok danych |
| **`T_LSQUARE`** | `[` | Otwarcie listy elementów |
| **`T_RSQUARE`** | `]` | Zamknięcie listy elementów |
| **`T_COMMA`** | `,` | Separator elementów na liście |
| **`T_COMMENT`** | `#.*` | Komentarz jednowierszowy (pomijany) |
| **`WS`** | `[ \t\r\n]+` | Znaki białe (pomijane automatycznie) |