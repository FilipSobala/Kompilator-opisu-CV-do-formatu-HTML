# Projekt TKiK: Kompilator opisu CV do formatu HTML

## Autorzy
* **Filip Sobala** fsobala@student.agh.edu.pl
* **Bartłomiej Przytuła** bartprzyt@student.agh.edu.pl

## Założenia programu

* **Cel programu:** Tłumaczenie autorskiego, prostego formatu tekstowego na w pełni sformatowaną, nowoczesną stronę CV w języku HTML.
* **Rodzaj translatora:** Kompilator.
* **Planowany wynik działania:** Program pobiera plik tekstowy (np. `moje_cv.txt`) i generuje na jego podstawie plik `.html` (wraz z wbudowanym ostylowaniem CSS), gotowy do wyświetlenia w przeglądarce i łatwego eksportu do formatu PDF.
* **Język implementacji:** Java.
* **Sposób realizacji skanera i parsera:** Użycie zewnętrznego generatora skanerów i parserów **ANTLR v4** (narzędzie wygeneruje kod w języku Java na podstawie napisanej przez nas gramatyki).