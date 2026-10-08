package stok;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentująca wynik działania algorytmu
 * obliczającego odległości węzłów od węzła początkowego.
 */
public class WynikPrzeszukiwania {
    /* Mapa przechowująca informacje dotyczące odległości
     wierzchołków od punktu startowego. */
    private final Map<Węzeł, Integer> odległości;
    /* Mapa przechowująca informacje z której
    krawędzi dostalismy się do aktualnego wierzchołka. */
    private final Map<Węzeł, KrawędźGrafu> krawędzieWejściowe;

    public WynikPrzeszukiwania(Map<Węzeł, Integer> odległości,
                               Map<Węzeł, KrawędźGrafu> krawędzieWejściowe) {
        this.odległości = odległości;
        this.krawędzieWejściowe = krawędzieWejściowe;
    }

    public Map<Węzeł, Integer> getOdległości() {
        return odległości;
    }

    public Map<Węzeł, KrawędźGrafu> getKrawędzieWejściowe() {
        return krawędzieWejściowe;
    }
}
