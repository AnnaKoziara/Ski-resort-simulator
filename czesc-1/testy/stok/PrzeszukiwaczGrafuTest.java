package stok;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Implementacja testów sprawdzających poprawność działania
 *  metody wyszukującej najkrótszej ścieżki w grafie.
 */
public class PrzeszukiwaczGrafuTest {
    private PrzeszukiwaczGrafu przeszukiwacz;
    private Węzeł[] węzły;

    private void dodajTrasę(int indeksPoczątkowy, int indeksKońcowy) {
        Trasa nowa = new Trasa(0, węzły[indeksPoczątkowy], węzły[indeksKońcowy], 0, 0, 0, 0);
        węzły[indeksPoczątkowy].dodajTrasę(nowa);
    }

    @BeforeEach
    void utwórzGraf() {
        this.przeszukiwacz = new PrzeszukiwaczGrafu();
        this.węzły = new Węzeł[6];
        for(int i = 0; i < 6; i++) {
            węzły[i] = new Węzeł(0, i, 0, false);
        }

        /* Utworzenie zadanego grafu testowego. */
        dodajTrasę(0, 1);
        dodajTrasę(1, 0);
        dodajTrasę(1, 2);
        dodajTrasę(2, 0);
        dodajTrasę(2, 3);
        dodajTrasę(2, 4);
        dodajTrasę(3, 1);
        dodajTrasę(3, 4);
        dodajTrasę(3, 5);
        dodajTrasę(4, 5);
        dodajTrasę(5, 3);
    }

    @Test
    void ścieżkaZ0do4Test() {
        WynikPrzeszukiwania wynik = przeszukiwacz.wyznaczNajkrótszeOdległości(węzły[0]);

        int odległość = wynik.getOdległości().get(węzły[4]);

        assertEquals(3, odległość, "Odległość od 0 do 4 powinna wynosić 3.");

        KrawędźGrafu krawędźDo4 = wynik.getKrawędzieWejściowe().get(węzły[4]);
        assertNotNull(krawędźDo4);
        assertEquals(węzły[2], krawędźDo4.getPoczątkowaStacja(), "Do węzła 4 powinniśmy się dostać poprzez węzeł 2.");

        KrawędźGrafu krawędźDo2 = wynik.getKrawędzieWejściowe().get(węzły[2]);
        assertNotNull(krawędźDo2);
        assertEquals(węzły[1], krawędźDo2.getPoczątkowaStacja(), "Do węzła 4 powinniśmy się dostać poprzez węzeł 1.");

        KrawędźGrafu krawędźDo1 = wynik.getKrawędzieWejściowe().get(węzły[1]);
        assertNotNull(krawędźDo1);
        assertEquals(węzły[0], krawędźDo1.getPoczątkowaStacja(), "Do węzła 1 powinniśmy się dostać poprzez węzeł 0.");

        KrawędźGrafu krawędźDo0 = wynik.getKrawędzieWejściowe().get(węzły[0]);
        assertNull(krawędźDo0, "Węzeł startowy nie ma krawędzi wejściowej");
    }

    @Test
    void bezpośredniaŚcieżkaZ3do1Test() {
        WynikPrzeszukiwania wynik = przeszukiwacz.wyznaczNajkrótszeOdległości(węzły[3]);

        int odległość = wynik.getOdległości().get(węzły[1]);

        assertEquals(1, odległość, "Odległość między bezpośrednimi węzłami powinna wynosić 1.");
    }

    @Test
    void pustaŚcieżkaZ2do2Test() {
        WynikPrzeszukiwania wynik = przeszukiwacz.wyznaczNajkrótszeOdległości(węzły[2]);

        int odległość = wynik.getOdległości().get(węzły[2]);

        assertEquals(0, odległość, "Odległość do tego samego węzła powinna wynosić 0.");
        assertNull(wynik.getKrawędzieWejściowe().get(węzły[2]));
    }

    @Test
    void ścieżkaZ4do3Test() {
        WynikPrzeszukiwania wynik = przeszukiwacz.wyznaczNajkrótszeOdległości(węzły[4]);

        int odległość = wynik.getOdległości().get(węzły[3]);

        assertEquals(2, odległość, "Odległość z 4 do 3 powinna wynosić 2.");
    }



}
