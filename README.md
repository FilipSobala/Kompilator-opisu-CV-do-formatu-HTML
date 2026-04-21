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
| **`T_YEAR`** | `[0-9]{4}` | Data w formacie samego roku RRRR |
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
/* Główny plik CV - Filip Sobala
   Demonstracja pełnych możliwości kompilatora DSL
*/
CV_START

# Importowanie zewnętrznych zależności (np. czcionek lub dodatkowych stylów)
IMPORT "assets/fonts.css"
IMPORT "assets/custom-print-rules.css"

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
    PORTFOLIO: [https://fsobala.dev](https://fsobala.dev)
    GITHUB: [https://github.com/fsobala](https://github.com/fsobala)
    LINKEDIN: [https://linkedin.com/in/fsobala](https://linkedin.com/in/fsobala)
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
            URL: [https://github.com/fsobala/CvDslCompiler](https://github.com/fsobala/CvDslCompiler)
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
    o ochronie danych osobowych (Dz. Ustaw z 2018, poz. 1000)).
    """
}

CV_END
```