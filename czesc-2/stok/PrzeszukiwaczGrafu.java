package stok;

import java.util.*;

/** Klasa służąca do wyznaczania najkrótszej ścieżki w grafie
 * oraz odległości od podanego węzła początkowego.
 * Wykorzystuje algorytm przeszukiwania wszerz grafu.
 */
public class PrzeszukiwaczGrafu {

    public PrzeszukiwaczGrafu() {}

    public WynikPrzeszukiwania wyznaczNajkrótszeOdległości(Węzeł węzełPoczątkowy) {
        Map<Węzeł, Integer> odległości = new HashMap<>();
        Map<Węzeł, KrawędźGrafu> krawędzieWejściowe = new HashMap<>();
        Queue<Węzeł> węzłyDoOdwiedzenia = new ArrayDeque<>();

        /* Inicjalizacja dla węzła początkowego */
        odległości.put(węzełPoczątkowy, 0);
        krawędzieWejściowe.put(węzełPoczątkowy, null);
        węzłyDoOdwiedzenia.add(węzełPoczątkowy);

        while (!węzłyDoOdwiedzenia.isEmpty()) {
            Węzeł pobranyWęzeł = węzłyDoOdwiedzenia.poll();
            int odległość = odległości.get(pobranyWęzeł);

            List<Trasa> listaTras = pobranyWęzeł.getListaTras();
            for (Trasa trasa : listaTras) {
                przetwórzKrawędź(trasa, odległość, odległości,
                        krawędzieWejściowe, węzłyDoOdwiedzenia);
            }

            List<Wyciąg> listaWyciągów = pobranyWęzeł.getListaWyciągów();
            for (Wyciąg wyciąg : listaWyciągów) {
                przetwórzKrawędź(wyciąg, odległość, odległości,
                        krawędzieWejściowe, węzłyDoOdwiedzenia);
            }

        }
        return new WynikPrzeszukiwania(odległości, krawędzieWejściowe);
    }

    private void przetwórzKrawędź(KrawędźGrafu krawędź,
                                         int odległość,
                                         Map<Węzeł, Integer> odległości,
                                         Map<Węzeł, KrawędźGrafu> krawędzieWejściowe,
                                         Queue<Węzeł> węzłyDoOdwiedzenia) {
        Węzeł końcowaStacja = krawędź.getKońcowaStacja();

        /* Uniknięcie wejścia w cykl w grafie. */
        if (!krawędzieWejściowe.containsKey(końcowaStacja)) {
            krawędzieWejściowe.put(końcowaStacja, krawędź);
            odległości.put(końcowaStacja, odległość + 1);
            węzłyDoOdwiedzenia.add(końcowaStacja);
        }
    }
}
