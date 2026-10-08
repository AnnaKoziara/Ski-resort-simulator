package stok;

import kadra.mapki.styl.StylKrawedzi;
import kadra.mapki.styl.StylLinii;
import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import zdarzenia.InterfaceKolejkiZdarzeń;
import zdarzenia.KoniecZjazdu;

import java.util.List;

public class Trasa implements KrawędźGrafu {
    private final int id;
    private final Węzeł początkowaStacja;
    private final Węzeł końcowaStacja;
    private final int poziomTrudności;
    private final int czasPrzejazdu;
    private final double bazowaAtrakcyjność;
    private final double odporność;
    private int ilePrzejazdów = 0;

    public Trasa(int id,
                 Węzeł początkowaStacja,
                 Węzeł końcowaStacja,
                 int poziomTrudności,
                 int czasPrzejazdu,
                 double bazowaAtrakcyjność,
                 double odporność) {

        this.id = id;
        this.początkowaStacja = początkowaStacja;
        this.końcowaStacja = końcowaStacja;
        this.poziomTrudności = poziomTrudności;
        this.czasPrzejazdu = czasPrzejazdu;
        this.bazowaAtrakcyjność = bazowaAtrakcyjność;
        this.odporność = odporność;
    }

    /* Wyznaczenie atrakcyjności trasy. */
    public double podajAtrakcyjność(Sportowiec sportowiec) {
        double dopasowanie = KalkulatorAtrakcyjności.obliczDopasowanie(
                this.poziomTrudności,
                sportowiec.getPoziomZaawansowania()
        );
        double wyrównanie = KalkulatorAtrakcyjności.obliczWyrównanie(
                this.odporność,
                this.bazowaAtrakcyjność,
                this.ilePrzejazdów
        );

        double znudzenie = sportowiec.obliczAktualneZnudzenie(this);

        return (sportowiec.getWagaTrudności() * dopasowanie) +
                (sportowiec.getWagaWyrównania() * wyrównanie) +
                (sportowiec.getWagaZnudzenia() * (1.0 - znudzenie));
    }

    @Override
    public void przyjmijSportowca(Sportowiec sportowiec,
                                  int aktualnyCzas,
                                  List<Trasa> wszystkieTrasy,
                                  InterfaceKolejkiZdarzeń kolejka) {

        this.odnotujPrzejazd();
        KomunikatAktywności.odnotuj(
                aktualnyCzas,
                sportowiec,
                "rozpoczął zjazd trasą nr " + this.id + "."
        );

        kolejka.wstaw(new KoniecZjazdu(
                aktualnyCzas + this.getCzasPrzejazdu(),
                sportowiec,
                this,
                wszystkieTrasy,
                kolejka
        ));
    }

    public double podajWyrównanie() {
        return KalkulatorAtrakcyjności.obliczWyrównanie(
                odporność,
                bazowaAtrakcyjność,
                ilePrzejazdów
        );
    }

    @Override
    /* Licznik osób, które zjechały daną trasą podczas trwania symulacji. */
    public void odnotujPrzejazd() {
        this.ilePrzejazdów++;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Węzeł getKońcowaStacja() {
        return końcowaStacja;
    }

    @Override
    public Węzeł getPoczątkowaStacja() {
        return początkowaStacja;
    }

    @Override
    public int getCzasPrzejazdu() {
        return czasPrzejazdu;
    }

    @Override
    public StylKrawedzi getStylKrawędzi() {
        return new StylKrawedzi(StylLinii.CIAGLA);
    }

    @Override
    public String getOznaczenieTypu() {
        return "t";
    }

    public int getIlePrzejazdów() {
        return ilePrzejazdów;
    }

    public double getOdporność() { return odporność; }

     public int getPoziomTrudności() {
        return poziomTrudności;
     }

    public double getBazowaAtrakcyjność() {
        return bazowaAtrakcyjność;
    }
}