package stok;

import kadra.mapki.styl.StylKrawedzi;
import kadra.mapki.styl.StylLinii;
import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import zdarzenia.InterfaceKolejkiZdarzeń;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

public class Wyciąg implements KrawędźGrafu {
    private final int id;
    private final int początkowyCzas;
    private final Węzeł początkowaStacja;
    private final Węzeł końcowaStacja;
    private final int odstępCzasowy;
    private final int ileSiedzeń;
    private final int czasPrzejazdu;
    private final Queue<Sportowiec> kolejkaSportowców;
    private int ileOsóbJechało = 0;
    private int maksymalnaDługośćKolejki = 0;
    private int czasOstatniejZmiany;
    private long sumaDługościKolejek = 0;
    private int ileKursów = 0;

    public Wyciąg(int id,
                  Węzeł początkowaStacja,
                  Węzeł końcowaStacja,
                  int odstępCzasowy,
                  int ileSiedzeń,
                  int czasPrzejazdu,
                  int początkowyCzas) {

        this.id = id;
        this.początkowaStacja = początkowaStacja;
        this.końcowaStacja = końcowaStacja;
        this.odstępCzasowy = odstępCzasowy;
        this.ileSiedzeń = ileSiedzeń;
        this.czasPrzejazdu = czasPrzejazdu;
        this.początkowyCzas = początkowyCzas;
        this.czasOstatniejZmiany = początkowyCzas;
        this.kolejkaSportowców = new ArrayDeque<>();
    }

    @Override
    public void przyjmijSportowca(Sportowiec sportowiec,
                                  int aktualnyCzas,
                                  List<Trasa> wszystkieTrasy,
                                  InterfaceKolejkiZdarzeń kolejka) {

        KomunikatAktywności.odnotuj(
                aktualnyCzas,
                sportowiec,
                "ustawił się w kolejce do wyciągu nr " + this.getId() + "."
        );

        this.dodajDoKolejki(sportowiec, aktualnyCzas);
    }

    public void dodajDoKolejki(Sportowiec sportowiec, int aktualnyCzas) {
        aktualizujStatystykiWyciągu(aktualnyCzas);

        kolejkaSportowców.add(sportowiec);

        /* Sprawdzenie czy maksymalna dotychczasowa długość kolejki została przekroczona. */
        if (kolejkaSportowców.size() > maksymalnaDługośćKolejki) {
            maksymalnaDługośćKolejki = kolejkaSportowców.size();
        }
    }

    @Override
    /* Licznik osób, które skorzystały z wyciągu podczas trwania symulacji. */
    public void odnotujPrzejazd() {
        ileOsóbJechało++;
    }

    /* Wyznaczenie grupy osób, które wjadą wyciągiem jako następne. */
    public Sportowiec[] pobierzOsobyNaWyciąg(int aktualnyCzas) {
        aktualizujStatystykiWyciągu(aktualnyCzas);

        ileKursów++;

        int ileOsób = kolejkaSportowców.size();
        ileOsób = Math.min(ileOsób, ileSiedzeń);

        Sportowiec[] osobyDoPobrania = new Sportowiec[ileOsób];
        for (int i = 0; i < ileOsób; i++) {
            osobyDoPobrania[i] = kolejkaSportowców.poll();
            odnotujPrzejazd();
        }
        return osobyDoPobrania;
    }

    private void aktualizujStatystykiWyciągu(int aktualnyCzas) {
        int upłyniętyCzas = aktualnyCzas - czasOstatniejZmiany;
        if (upłyniętyCzas > 0) {
            this.sumaDługościKolejek += (long) upłyniętyCzas * kolejkaSportowców.size();
            this.czasOstatniejZmiany = aktualnyCzas;
        }
    }
    public double getŚredniaDługośćKolejki(int czasZakończenia) {
        aktualizujStatystykiWyciągu(czasZakończenia);

        int czasTrwaniaSymulacji = czasZakończenia - początkowyCzas;

        if (czasTrwaniaSymulacji == 0) {
            return 0;
        }

        return (double) sumaDługościKolejek / czasTrwaniaSymulacji;
    }

    public int podajProcentZajętości() {
        int maksymalnaMozliwośćZajętości = ileKursów * ileSiedzeń;
        if (maksymalnaMozliwośćZajętości == 0) {
            return 0;
        }
        return (int) Math.round(((double) ileOsóbJechało / maksymalnaMozliwośćZajętości) * 100);
    }

    @Override
    public int getId() {
        return id;
    }

    public int getIleSiedzeń() {
        return ileSiedzeń;
    }

    @Override
    public Węzeł getKońcowaStacja() {
        return końcowaStacja;
    }

    @Override
    public Węzeł getPoczątkowaStacja() { return początkowaStacja; }

    public int getOdstępCzasowy() {
        return odstępCzasowy;
    }

    @Override
    public int getCzasPrzejazdu() {
        return czasPrzejazdu;
    }

    @Override
    public StylKrawedzi getStylKrawędzi() {
        return new StylKrawedzi(StylLinii.PRZERYWANA);
    }

    @Override
    public String getOznaczenieTypu() {
        return "w";
    }

    public int getIleOsóbJechało() {
        return ileOsóbJechało;
    }

    public int getMaksymalnaDługośćKolejki() {
        return maksymalnaDługośćKolejki;
    }

    public int getIleKursów() {
        return ileKursów;
    }
}
