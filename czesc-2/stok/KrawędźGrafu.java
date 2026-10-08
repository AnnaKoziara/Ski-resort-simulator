package stok;

import sportowcy.Sportowiec;
import zdarzenia.InterfaceKolejkiZdarzeń;

/* Interface reprezentujący krawędź grafu stoku (trasę lub wyciąg). */
public interface KrawędźGrafu {

    /* Odnotowanie wejścia sportowca na krawędź i wygenerowanie nowego zdarzenia. */
    void przyjmijSportowca(Sportowiec sportowiec, int czas, InterfaceKolejkiZdarzeń kolejka);

    /* Zwiększa licznik osób, które skorzystały z tej krawędzi. */
    void odnotujPrzejazd();

    int getId();

    Węzeł getKońcowaStacja();

    int getCzasPrzejazdu();




}
