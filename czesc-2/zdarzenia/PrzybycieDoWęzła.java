package zdarzenia;

import sportowcy.Sportowiec;
import stok.KrawędźGrafu;
import stok.Węzeł;

/* Zdarzenie wywoływane, gdy sportowiec rozpoczyna dzień lub kończy przejazd. */
public class PrzybycieDoWęzła extends Zdarzenie{
    private final Węzeł węzeł;
    private final Sportowiec sportowiec;

    public PrzybycieDoWęzła(
            int czas,
            Sportowiec sportowiec,
            Węzeł węzeł,
            InterfaceKolejkiZdarzeń kolejkaZdarzeń) {

        super(czas, kolejkaZdarzeń);
        this.węzeł = węzeł;
        this.sportowiec = sportowiec;
    }

    @Override
    public void wykonaj() {
        if (getCzas() >= KONIEC_CZASU_DECYZJI) {
            return;
        }
        KrawędźGrafu wybór = sportowiec.wybierzDrogę(węzeł);
        wybór.przyjmijSportowca(sportowiec, getCzas(), getKolejkaZdarzeń());
    }
}
