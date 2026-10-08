package zdarzenia;

import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import stok.Wyciąg;

/* Zdarzenie wywoływane, gdy upłynie czas wjazdu wyciągiem przez sportowca. */
public class KoniecWjazdu extends Zdarzenie {
    private final Wyciąg wyciąg;
    private final Sportowiec sportowiec;

    public KoniecWjazdu(int czas,
                        Sportowiec sportowiec,
                        Wyciąg wyciąg,
                        InterfaceKolejkiZdarzeń kolejkaZdarzeń) {

        super(czas, kolejkaZdarzeń);
        this.wyciąg = wyciąg;
        this.sportowiec = sportowiec;
    }

    @Override
    public void wykonaj() {
        KomunikatAktywności.odnotuj(
                getCzas(),
                sportowiec,
                "zakończył wjazd wyciągiem nr " + wyciąg.getId() + "."
        );

        getKolejkaZdarzeń().wstaw(new PrzybycieDoWęzła(
                getCzas(),
                sportowiec,
                wyciąg.getKońcowaStacja(),
                getKolejkaZdarzeń()
        ));
    }
}
