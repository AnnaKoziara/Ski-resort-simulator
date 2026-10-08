package stok;

import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import zdarzenia.InterfaceKolejkiZdarzeń;

public class Wyciąg implements KrawędźGrafu{
    private final int id;
    private final Węzeł początkowaStacja;
    private final Węzeł końcowaStacja;
    private final int odstępCzasowy;
    private final int ileSiedzeń;
    private final int czasPrzejazdu;
    private final KolejkaSportowców kolejkaSportowców = new KolejkaSportowców();
    private int ilePrzejazdów = 0;

    public Wyciąg(int id,
                  Węzeł początkowaStacja,
                  Węzeł końcowaStacja,
                  int odstępCzasowy,
                  int ileSiedzeń,
                  int czasPrzejazdu) {

        this.id = id;
        this.początkowaStacja = początkowaStacja;
        this.końcowaStacja = końcowaStacja;
        this.odstępCzasowy = odstępCzasowy;
        this.ileSiedzeń = ileSiedzeń;
        this.czasPrzejazdu = czasPrzejazdu;
    }

    @Override
    public void przyjmijSportowca(Sportowiec sportowiec,
                                  int aktualnyCzas,
                                  InterfaceKolejkiZdarzeń kolejka) {

        KomunikatAktywności.odnotuj(
                aktualnyCzas,
                sportowiec,
                "ustawił się w kolejce do wyciągu nr " + this.getId() + "."
        );

        this.dodajDoKolejki(sportowiec);
    }

    public void dodajDoKolejki(Sportowiec sportowiec) {
        kolejkaSportowców.ustawNaKońcu(sportowiec);
    }

    @Override
    /* Licznik osób, które skorzystały z wyciągu podczas trwania symulacji. */
    public void odnotujPrzejazd() {
        ilePrzejazdów++;
    }

    /* Wyznaczenie grupy osób, które wjadą wyciągiem jako następne. */
    public Sportowiec[] pobierzOsobyNaWyciąg() {
        int ileOsób = kolejkaSportowców.getIleOsóbCzeka();
        ileOsób = Math.min(ileOsób, ileSiedzeń);

        Sportowiec[] kolejkaOsób = new Sportowiec[ileOsób];
        for (int i = 0; i < ileOsób; i++) {
            kolejkaOsób[i] = kolejkaSportowców.pobierzZPrzodu();
        }
        return kolejkaOsób;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Węzeł getKońcowaStacja() {
        return końcowaStacja;
    }

    public int getOdstępCzasowy() {
        return odstępCzasowy;
    }

    @Override
    public int getCzasPrzejazdu() {
        return czasPrzejazdu;
    }

    public int getIlePrzejazdów() {
        return ilePrzejazdów;
    }
}
