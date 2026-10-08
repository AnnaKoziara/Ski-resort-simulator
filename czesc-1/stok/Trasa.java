package stok;

import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import zdarzenia.InterfaceKolejkiZdarzeń;
import zdarzenia.KoniecZjazdu;

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

        return (sportowiec.getWagaTrudności() * dopasowanie) +
                (sportowiec.getWagaWyrównania() * wyrównanie);
    }

    @Override
    public void przyjmijSportowca(Sportowiec sportowiec,
                                  int aktualnyCzas,
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
                kolejka
        ));
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
    public int getCzasPrzejazdu() {
        return czasPrzejazdu;
    }

    public int getIlePrzejazdów() {
        return ilePrzejazdów;
    }
}