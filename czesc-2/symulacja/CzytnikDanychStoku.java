package symulacja;

import sportowcy.Sportowiec;
import sportowcy.SportowiecKolekcjoner;
import sportowcy.SportowiecLokalny;
import sportowcy.SportowiecZachłanny;
import stok.Trasa;
import stok.Wyciąg;
import stok.Węzeł;
import zdarzenia.InterfaceKolejkiZdarzeń;
import zdarzenia.KursWyciągu;
import zdarzenia.PrzybycieDoWęzła;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CzytnikDanychStoku {
    private final InterfaceKolejkiZdarzeń kolejkaZdarzeń;
    private final List<Trasa> listaTras;
    private List<Wyciąg> listaWyciągów;
    private List<Węzeł> listaWęzłów;
    private List<Sportowiec> listaSportowców;

    public CzytnikDanychStoku(InterfaceKolejkiZdarzeń kolejkaZdarzeń) {
        this.kolejkaZdarzeń = kolejkaZdarzeń;
        this.listaTras = new ArrayList<>();
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
        listaWęzłów= new ArrayList<>(liczbaWęzłów);

        for (int i = 0; i < liczbaWęzłów; i++) {
            Scanner daneWęzła = new Scanner(skaner.nextLine());

            int wysokość = daneWęzła.nextInt();
            int x = daneWęzła.nextInt();
            int y = daneWęzła.nextInt();

            boolean czySkomunikowany = daneWęzła.hasNext() && daneWęzła.next().equals("s");

            listaWęzłów.add(new Węzeł(wysokość, x, y, czySkomunikowany));
        }
    }

    private void wczytajWyciągi(Scanner skaner, int aktualnyCzas) {
        int liczbaWyciągów = Integer.parseInt(skaner.nextLine().trim());
        this.listaWyciągów = new ArrayList<>(liczbaWyciągów);

        for (int i = 0; i < liczbaWyciągów; i++) {
            Scanner daneWyciągu = new Scanner(skaner.nextLine());

            int idPoczątkowego = daneWyciągu.nextInt();
            int idKońcowego = daneWyciągu.nextInt();
            int odstępCzasowy = daneWyciągu.nextInt();
            int maksymalnaGrupa = daneWyciągu.nextInt();
            int czasPrzejazdu = daneWyciągu.nextInt();

            Węzeł stacjaPoczątkowa = listaWęzłów.get(idPoczątkowego);
            Węzeł stacjaKońcowa = listaWęzłów.get(idKońcowego);

            Wyciąg nowyWyciąg = new Wyciąg(
                    i,
                    stacjaPoczątkowa,
                    stacjaKońcowa,
                    odstępCzasowy,
                    maksymalnaGrupa,
                    czasPrzejazdu,
                    aktualnyCzas
            );

            listaWyciągów.add(nowyWyciąg);
            stacjaPoczątkowa.dodajWyciąg(nowyWyciąg);

            /* Rozpoczęcie działania wyciągu. */
            kolejkaZdarzeń.wstaw(new KursWyciągu(
                    aktualnyCzas,
                    kolejkaZdarzeń,
                    nowyWyciąg,
                    listaTras
            ));
        }
    }

    private void wczytajTrasy(Scanner skaner) {
        int liczbaTras = Integer.parseInt(skaner.nextLine().trim());

        for (int i = 0; i < liczbaTras; i++) {
            Scanner daneTrasy = new Scanner(skaner.nextLine());
            daneTrasy.useLocale(Locale.ENGLISH);

            int idPoczątkowego = daneTrasy.nextInt();
            int idKońcowego = daneTrasy.nextInt();
            int poziomTrudnosci = daneTrasy.nextInt();
            int czasPrzejazdu = daneTrasy.nextInt();
            double bazowaAtrakcyjność = daneTrasy.nextDouble();
            double odporność = daneTrasy.nextDouble();

            Węzeł stacjaPoczątkowa = listaWęzłów.get(idPoczątkowego);
            Węzeł stacjaKońcowa = listaWęzłów.get(idKońcowego);

            Trasa nowaTrasa = new Trasa(
                    i,
                    stacjaPoczątkowa,
                    stacjaKońcowa,
                    poziomTrudnosci,
                    czasPrzejazdu,
                    bazowaAtrakcyjność,
                    odporność
            );

            stacjaPoczątkowa.dodajTrasę(nowaTrasa);
            this.listaTras.add(nowaTrasa);
        }

    }

    /* Wczytywanie grupy sportowców i dodanie do kolejki zdarzeń ich pojawienia
    się na stoku, czyli dodanie zdarzenia PrzybycieDoWęzła. */
    private void wczytajSportowców(Scanner skaner) {
        this.listaSportowców = new ArrayList<>();
        int liczbaGrup = Integer.parseInt(skaner.nextLine().trim());
        int idAktualnegoSportowca = 0;

        for (int i = 0; i < liczbaGrup; i++) {
            Scanner daneGrupy = new Scanner(skaner.nextLine());
            daneGrupy.useLocale(Locale.ENGLISH);

            int liczbaWGrupie = daneGrupy.nextInt();
            int poziomZaawansowania = daneGrupy.nextInt();
            double spontaniczność = daneGrupy.nextDouble();
            double współczynnikZnudzenia = daneGrupy.nextDouble();
            String rodzajSportowca = daneGrupy.next();
            boolean czyŚledzony = daneGrupy.hasNext() && daneGrupy.next().equals("s");

            Scanner parametryAtrakcyjności = new Scanner(skaner.nextLine());
            parametryAtrakcyjności.useLocale(Locale.ENGLISH);

            double wagaTrudności = parametryAtrakcyjności.nextDouble();
            double wagaWyrównania = parametryAtrakcyjności.nextDouble();
            double wagaZnudzenia = parametryAtrakcyjności.nextDouble();

            Scanner startoweDane = new Scanner(skaner.nextLine());

            int idWęzłaStartowego = startoweDane.nextInt();
            String czasStartu = startoweDane.next();

            int odstępCzasowy = 0;
            if (startoweDane.hasNextInt()) {
                odstępCzasowy = startoweDane.nextInt();
            }

            Węzeł węzełStartowy = listaWęzłów.get(idWęzłaStartowego);
            int czas = czasWSekundach(czasStartu);

            for (int j = 0; j < liczbaWGrupie; j++) {
                int czasPrzybycia = czas + (j * odstępCzasowy);

                Sportowiec nowySportowiec = null;

                switch (rodzajSportowca) {
                    case "L":
                        nowySportowiec = new SportowiecLokalny(
                                idAktualnegoSportowca,
                                poziomZaawansowania,
                                spontaniczność,
                                współczynnikZnudzenia,
                                wagaTrudności,
                                wagaWyrównania,
                                wagaZnudzenia,
                                czyŚledzony
                        );
                        break;
                    case "Z":
                        nowySportowiec = new SportowiecZachłanny(
                                idAktualnegoSportowca,
                                poziomZaawansowania,
                                spontaniczność,
                                współczynnikZnudzenia,
                                wagaTrudności,
                                wagaWyrównania,
                                wagaZnudzenia,
                                czyŚledzony
                        );
                        break;
                    case "K":
                        nowySportowiec = new SportowiecKolekcjoner(
                                idAktualnegoSportowca,
                                poziomZaawansowania,
                                spontaniczność,
                                współczynnikZnudzenia,
                                wagaTrudności,
                                wagaWyrównania,
                                wagaZnudzenia,
                                czyŚledzony
                        );
                        break;
                }

                listaSportowców.add(nowySportowiec);

                kolejkaZdarzeń.wstaw(new PrzybycieDoWęzła(
                        czasPrzybycia,
                        nowySportowiec,
                        węzełStartowy,
                        kolejkaZdarzeń,
                        listaTras

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

    public List<Wyciąg> getListaWyciągów() { return new ArrayList<>(listaWyciągów); }

    public List<Trasa> getListaTras() { return new ArrayList<>(listaTras); }

    public List<Węzeł> getListaWęzłów () { return new ArrayList<>(listaWęzłów); }

    public List<Sportowiec> getListaSportowców() {
        return new ArrayList<>(listaSportowców);
    }
}
