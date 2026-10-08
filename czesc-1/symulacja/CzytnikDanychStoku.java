package symulacja;

import sportowcy.Sportowiec;
import stok.Trasa;
import stok.Wyciąg;
import stok.Węzeł;
import zdarzenia.InterfaceKolejkiZdarzeń;
import zdarzenia.KursWyciągu;
import zdarzenia.PrzybycieDoWęzła;

import java.util.Locale;
import java.util.Scanner;

public class CzytnikDanychStoku {
    private final InterfaceKolejkiZdarzeń kolejkaZdarzeń;
    private Wyciąg[] tablicaWyciągów;
    private Trasa[] tablicaTras;
    private Węzeł[] tablicaWęzłów;

    public CzytnikDanychStoku(InterfaceKolejkiZdarzeń kolejkaZdarzeń) {
        this.kolejkaZdarzeń = kolejkaZdarzeń;
    }

    /* Wczytywanie danych symulacji w kolejności: węzły, wyciągi, trasy, sportowcy. */
    public void wczytajDane(Scanner skaner, int aktualnyCzas) {
        wczytajWęzły(skaner);

        skaner.nextLine();
        wczytajWyciągi(skaner, aktualnyCzas);

        skaner.nextLine();
        wczytajTrasy(skaner);

        skaner.nextLine();
        wczytajSportowców(skaner);
    }

    private void wczytajWęzły(Scanner skaner) {
        int liczbaWęzłów = Integer.parseInt(skaner.nextLine().trim());
        Węzeł[] tablicaWęzłów = new Węzeł[liczbaWęzłów];

        for (int i = 0; i < liczbaWęzłów; i++) {
            Scanner daneWęzła = new Scanner(skaner.nextLine());

            int wysokość = daneWęzła.nextInt();
            int x = daneWęzła.nextInt();
            int y = daneWęzła.nextInt();

            boolean czySkomunikowany = daneWęzła.hasNext() && daneWęzła.next().equals("s");

            tablicaWęzłów[i] = new Węzeł(wysokość, x, y, czySkomunikowany);
        }
        this.tablicaWęzłów = tablicaWęzłów;
    }

    private void wczytajWyciągi(Scanner skaner, int aktualnyCzas) {
        int liczbaWyciągów = Integer.parseInt(skaner.nextLine().trim());
        Wyciąg[] tablicaWyciągów = new Wyciąg[liczbaWyciągów];

        for (int i = 0; i < liczbaWyciągów; i++) {
            Scanner daneWyciągu = new Scanner(skaner.nextLine());

            int idPoczątkowego = daneWyciągu.nextInt();
            int idKońcowego = daneWyciągu.nextInt();
            int odstępCzasowy = daneWyciągu.nextInt();
            int maksymalnaGrupa = daneWyciągu.nextInt();
            int czasPrzejazdu = daneWyciągu.nextInt();

            Węzeł stacjaPoczątkowa = tablicaWęzłów[idPoczątkowego];
            Węzeł stacjaKońcowa = tablicaWęzłów[idKońcowego];

            tablicaWyciągów[i] = new Wyciąg(
                    i,
                    stacjaPoczątkowa,
                    stacjaKońcowa,
                    odstępCzasowy,
                    maksymalnaGrupa,
                    czasPrzejazdu
            );

            stacjaPoczątkowa.dodajWyciąg(tablicaWyciągów[i]);

            /* Rozpoczęcie działania wyciągu. */
            kolejkaZdarzeń.wstaw(new KursWyciągu(
                    aktualnyCzas,
                    kolejkaZdarzeń,
                    tablicaWyciągów[i]
            ));
        }
        this.tablicaWyciągów = tablicaWyciągów;
    }

    private void wczytajTrasy(Scanner skaner) {
        int liczbaTras = Integer.parseInt(skaner.nextLine().trim());
        Trasa[] tablicaTras = new Trasa[liczbaTras];

        for (int i = 0; i < liczbaTras; i++) {
            Scanner daneTrasy = new Scanner(skaner.nextLine());
            daneTrasy.useLocale(Locale.ENGLISH);

            int idPoczątkowego = daneTrasy.nextInt();
            int idKońcowego = daneTrasy.nextInt();
            int poziomTrudnosci = daneTrasy.nextInt();
            int czasPrzejazdu = daneTrasy.nextInt();
            double bazowaAtrakcyjność = daneTrasy.nextDouble();
            double odporność = daneTrasy.nextDouble();

            Węzeł stacjaPoczątkowa = tablicaWęzłów[idPoczątkowego];
            Węzeł stacjaKońcowa = tablicaWęzłów[idKońcowego];

            tablicaTras[i] = new Trasa(
                    i,
                    stacjaPoczątkowa,
                    stacjaKońcowa,
                    poziomTrudnosci,
                    czasPrzejazdu,
                    bazowaAtrakcyjność,
                    odporność
            );

            stacjaPoczątkowa.dodajTrasę(tablicaTras[i]);
        }
        this.tablicaTras = tablicaTras;
    }

    /* Wczytywanie grupy sportowców i dodanie do kolejki zdarzeń ich pojawienia
    się na stoku, czyli dodanie zdarzenia PrzybycieDoWęzła. */
    private void wczytajSportowców(Scanner skaner) {
        int liczbaGrup = Integer.parseInt(skaner.nextLine().trim());
        int idAktualnegoSportowca = 0;

        for (int i = 0; i < liczbaGrup; i++) {
            Scanner daneGrupy = new Scanner(skaner.nextLine());
            daneGrupy.useLocale(Locale.ENGLISH);

            int liczbaWGrupie = daneGrupy.nextInt();
            int poziomZaawansowania = daneGrupy.nextInt();
            double spontaniczność = daneGrupy.nextDouble();
            boolean czyŚledzony = daneGrupy.hasNext() && daneGrupy.next().equals("s");

            Scanner parametryAtrakcyjności = new Scanner(skaner.nextLine());
            parametryAtrakcyjności.useLocale(Locale.ENGLISH);

            double wagaTrudności = parametryAtrakcyjności.nextDouble();
            double wagaWyrównania = parametryAtrakcyjności.nextDouble();

            Scanner startoweDane = new Scanner(skaner.nextLine());

            int idWęzłaStartowego = startoweDane.nextInt();
            String czasStartu = startoweDane.next();

            int odstępCzasowy = 0;
            if (startoweDane.hasNextInt()) {
                odstępCzasowy = startoweDane.nextInt();
            }

            Węzeł węzełStartowy = tablicaWęzłów[idWęzłaStartowego];
            int czas = czasWSekundach(czasStartu);

            for (int j = 0; j < liczbaWGrupie; j++) {
                int czasPrzybycia = czas + (j * odstępCzasowy);

                Sportowiec nowySportowiec = new Sportowiec(
                        idAktualnegoSportowca,
                        poziomZaawansowania,
                        spontaniczność,
                        wagaTrudności,
                        wagaWyrównania,
                        czyŚledzony
                );

                kolejkaZdarzeń.wstaw(new PrzybycieDoWęzła(
                        czasPrzybycia,
                        nowySportowiec,
                        węzełStartowy,
                        kolejkaZdarzeń
                ));

                idAktualnegoSportowca++;
            }
        }
    }

    /* Zamiana tekstowego zapisu godziny formatu GG:MM:SS na ilość sekund,
    która upłynęła od północy. */
    private int czasWSekundach(String czas) {
        String[] podziałCzasu = czas.split(":");
        int godziny = Integer.parseInt(podziałCzasu[0]);
        int minuty = Integer.parseInt(podziałCzasu[1]);
        int sekundy = Integer.parseInt(podziałCzasu[2]);

        return godziny * 3600 + minuty * 60 + sekundy;
    }

    public Wyciąg[] getTablicaWyciągów() { return tablicaWyciągów; }

    public Trasa[] getTablicaTras() { return tablicaTras; }
}
