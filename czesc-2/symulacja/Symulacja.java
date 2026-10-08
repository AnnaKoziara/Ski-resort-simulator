package symulacja;

import kadra.mapki.pliki.WyjatekSystemuPlikow;
import sportowcy.Sportowiec;
import stok.Trasa;
import stok.Wyciąg;
import stok.Węzeł;
import zdarzenia.InterfaceKolejkiZdarzeń;
import zdarzenia.KolejkaZdarzeń;
import zdarzenia.Zdarzenie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Symulacja {
    /* Czas symulacji jest mierzony w sekundach, a 9:00:00 to 32400 sekund. */
    private static final int CZAS_ROZPOCZĘCIA_SYMULACJI = 9 * 3600;
    private int aktualnyCzas;
    private final InterfaceKolejkiZdarzeń kolejkaZdarzeń;
    private List<Wyciąg> listaWyciągów;
    private List<Trasa> listaTras;
    private List<Węzeł> listaWęzłów;
    private List<Sportowiec> listaSportowców;

    public Symulacja() {
        this.kolejkaZdarzeń = new KolejkaZdarzeń();
        this.aktualnyCzas = CZAS_ROZPOCZĘCIA_SYMULACJI;
    }

    public void wczytajDane(Scanner skaner) {
        CzytnikDanychStoku czytnik = new CzytnikDanychStoku(kolejkaZdarzeń);
        czytnik.wczytajDane(skaner, aktualnyCzas);

        this.listaTras = czytnik.getListaTras();
        this.listaWyciągów = czytnik.getListaWyciągów();
        this.listaWęzłów = czytnik.getListaWęzłów();
        this.listaSportowców = czytnik.getListaSportowców();
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

    public void wygenerujMapy(String ścieżkaPliku) throws WyjatekSystemuPlikow {
        ProjektantStoku projektant = new ProjektantStoku(this.getListaWęzłów(), ścieżkaPliku);
        projektant.generujMapy(this.getListaSportowców());
    }

    private void wypiszStatystykiTras() {
        System.out.println("Statystyki dla tras:");
        for (Trasa pobranaTrasa : listaTras) {
            System.out.printf("Trasa nr %d:%n", pobranaTrasa.getId());
            System.out.printf("Łączna liczba przejazdów: %d%n", pobranaTrasa.getIlePrzejazdów());
            System.out.printf("Wyrównanie na koniec dnia: %.2f%n", pobranaTrasa.podajWyrównanie());
        }
    }

    private void wypiszStatystykiWyciągów() {
        System.out.println("Statystyki dla wyciągów:");
        for (Wyciąg pobranyWyciąg: listaWyciągów) {
            System.out.printf("Wyciąg nr %d:%n", pobranyWyciąg.getId());
            System.out.printf("Maksymalna długość kolejki: %d%n", pobranyWyciąg.getMaksymalnaDługośćKolejki());
            System.out.printf("Średnia długość kolejki: %.2f%n", pobranyWyciąg.getŚredniaDługośćKolejki(aktualnyCzas));
            System.out.printf("Łączna liczba przewiezionych pasażerów: %d%n", pobranyWyciąg.getIleOsóbJechało());
            System.out.printf("Procent zajętych miejsc: %d%%%n", pobranyWyciąg.podajProcentZajętości());
        }
    }

    public List<Sportowiec> getListaSportowców() {
        return new ArrayList<>(this.listaSportowców);
    }

    public List<Węzeł> getListaWęzłów() {
        return new ArrayList<>(this.listaWęzłów);
    }
}
