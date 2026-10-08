package zdarzenia;

import sportowcy.Sportowiec;
import stok.KrawędźGrafu;
import stok.Trasa;
import stok.Węzeł;

import java.util.List;

/**
 *  Zdarzenie wywoływane, gdy sportowiec rozpoczyna dzień lub kończy przejazd.
 */
public class PrzybycieDoWęzła extends Zdarzenie{
    private final Węzeł węzeł;
    private final Sportowiec sportowiec;
    private final List<Trasa> listaTras;

    public PrzybycieDoWęzła(
            int czas,
            Sportowiec sportowiec,
            Węzeł węzeł,
            InterfaceKolejkiZdarzeń kolejkaZdarzeń,
            List<Trasa> listaTras) {

        super(czas, kolejkaZdarzeń);
        this.węzeł = węzeł;
        this.sportowiec = sportowiec;
        this.listaTras = listaTras;
    }

    @Override
    public void wykonaj() {
        if (getCzas() >= KONIEC_CZASU_DECYZJI) {
            return;
        }
        KrawędźGrafu wybór = sportowiec.wybierzDrogę(węzeł, listaTras);
        wybór.przyjmijSportowca(sportowiec, getCzas(), listaTras, getKolejkaZdarzeń());
    }
}
