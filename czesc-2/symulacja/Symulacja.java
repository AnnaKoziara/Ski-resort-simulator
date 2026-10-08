package symulacja;

import stok.Trasa;
import stok.Wyciąg;
import stok.Węzeł;
import zdarzenia.InterfaceKolejkiZdarzeń;
import zdarzenia.TablicaZdarzeń;
import zdarzenia.Zdarzenie;

import java.util.Scanner;

public class Symulacja {
    /* Czas symulacji jest mierzony w sekundach, a 9:00:00 to 32400 sekund. */
    private static final int CZAS_ROZPOCZĘCIA_SYMULACJI = 9 * 3600;

    private int aktualnyCzas;
    private final InterfaceKolejkiZdarzeń kolejkaZdarzeń;
    private Wyciąg[] tablicaWyciągów;
    private Trasa[] tablicaTras;

    public Symulacja() {
        this.kolejkaZdarzeń = new TablicaZdarzeń();
        this.aktualnyCzas = CZAS_ROZPOCZĘCIA_SYMULACJI;
    }

    public void wczytajDane(Scanner skaner) {
        CzytnikDanychStoku czytnik = new CzytnikDanychStoku(kolejkaZdarzeń);
        czytnik.wczytajDane(skaner, aktualnyCzas);

        this.tablicaTras = czytnik.getTablicaTras();
        this.tablicaWyciągów = czytnik.getTablicaWyciągów();
    }

    public void wykonajSymulację() {
        while (!kolejkaZdarzeń.czyPusta()) {
            Zdarzenie aktualneZdarzenie = kolejkaZdarzeń.pobierzZdarzenie();
            this.aktualnyCzas = aktualneZdarzenie.getCzas();
            aktualneZdarzenie.wykonaj();
        }

        /* Wypisanie zliczonych statystyk dla wyciągów i tras. */
        wypiszStatystykiTras();
        wypiszStatystykiWyciągów();
    }

    private void wypiszStatystykiTras() {
        for (int i = 0; i < tablicaTras.length; i++) {
            System.out.println("Łączna liczba przejazdów trasą nr " +
                    i +
                    " wynosi " +
                    tablicaTras[i].getIlePrzejazdów() + "."
            );
        }
    }

    private void wypiszStatystykiWyciągów() {
        for (int i = 0; i < tablicaWyciągów.length; i++) {
            System.out.println("Łączna liczba przejazdów wyciągiem nr " +
                    i +
                    " wynosi " +
                    tablicaWyciągów[i].getIlePrzejazdów() + "."
            );
        }
    }
}
