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

# Projekt TKiK: Kompilator opisu CV do formatu HTML

## Autorzy
* **Filip Sobala** (twoj@email.com)
* **Bartłomiej Przytuła** (bartek@email.com)

---

## 1. Założenia Projektu

* **Cel:** Tłumaczenie autorskiego tekstu (DSL - Domain Specific Language) na w pełni sformatowaną stronę CV w formacie HTML z wbudowanym stylem CSS.
* **Rodzaj translatora:** Kompilator.
* **Język implementacji:** Java.
* **Narzędzia:** Generator parserów **ANTLR v4**.
* **Założenie użytkowe:** Program ma maksymalnie upraszczać proces tworzenia CV, pozwalając użytkownikowi skupić się na treści, podczas gdy kompilator dba o strukturę i wygląd dokumentu.

---

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

---

## 3. Analiza Składniowa (Gramatyka)

Poniżej przedstawiono logiczną strukturę języka. Parser weryfikuje poprawność ułożenia tokenów w hierarchię.

```antlr
/** Gramatyka w formacie ANTLR **/

// Plik musi być zamknięty w znacznikach START i END
cv_document : T_START section+ T_END ;

// Każda sekcja posiada etykietę (Label) i blok danych w klamrach
section     : T_SECTION T_LABEL T_LBRACE content* T_RBRACE ;

// Zawartość sekcji to pojedyncze pola tekstowe lub listy (tablice)
content     : pair | list_field ;

// Standardowe pole: KLUCZ: "Wartość"
pair        : T_KEY T_STRING ;

// Pole listowe: KLUCZ: ["Wartość1", "Wartość2"]
list_field  : T_KEY T_LSQUARE T_STRING (T_COMMA T_STRING)* T_RSQUARE ;


# To jest przykładowe CV
CV_START

SECTION Personal_Data {
    NAME: "Filip Sobala"
    EMAIL: "filip@email.com"
    CITY: "Kraków"
}

SECTION Experience {
    JOB: "Java Developer"
    COMPANY: "Google"
    YEARS: "2023-2024"
}

SECTION Skills {
    TECH_STACK: ["Java", "ANTLR", "Spring_Boot", "SQL"]
    LANGUAGES: ["Polish", "English"]
}

CV_END