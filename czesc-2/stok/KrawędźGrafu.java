package stok;

import kadra.mapki.styl.StylKrawedzi;
import sportowcy.Sportowiec;
import zdarzenia.InterfaceKolejkiZdarzeń;

import java.util.List;

/**
 *  Interface reprezentujący krawędź grafu stoku (trasę lub wyciąg).
 */
public interface KrawędźGrafu {

    /* Odnotowanie wejścia sportowca na krawędź i wygenerowanie nowego zdarzenia. */
    void przyjmijSportowca(Sportowiec sportowiec,
                           int czas,
                           List<Trasa> wszystkieTrasy,
                           InterfaceKolejkiZdarzeń kolejka);

    /* Zwiększa licznik osób, które skorzystały z tej krawędzi. */
    void odnotujPrzejazd();

    int getId();

    Węzeł getKońcowaStacja();

    Węzeł getPoczątkowaStacja();

    int getCzasPrzejazdu();

    StylKrawedzi getStylKrawędzi();

    String getOznaczenieTypu();
}
