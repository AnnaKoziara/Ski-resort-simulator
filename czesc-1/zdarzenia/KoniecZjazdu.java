package zdarzenia;

import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import stok.Trasa;

/* Zdarzenie wywoływane, gdy upłynie czas zjazdu sportowca z trasy. */
public class KoniecZjazdu extends Zdarzenie {
    private final Trasa trasa;
    private final Sportowiec sportowiec;

    public KoniecZjazdu(int czas,
                        Sportowiec sportowiec,
                        Trasa trasa,
                        InterfaceKolejkiZdarzeń kolejkaZdarzeń) {

        super(czas, kolejkaZdarzeń);
        this.trasa = trasa;
        this.sportowiec = sportowiec;
    }

    @Override
    public void wykonaj() {
        KomunikatAktywności.odnotuj(
                getCzas(),
                sportowiec,
                "zakończył zjazd trasą nr " + trasa.getId() + "."
        );

        getKolejkaZdarzeń().wstaw(new PrzybycieDoWęzła(
                getCzas(),
                sportowiec,
                trasa.getKońcowaStacja(),
                getKolejkaZdarzeń()
        ));
    }
}
