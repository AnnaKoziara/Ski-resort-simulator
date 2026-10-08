package zdarzenia;

import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import stok.Trasa;

import java.util.List;

/* Zdarzenie wywoływane, gdy upłynie czas zjazdu sportowca z trasy. */
public class KoniecZjazdu extends Zdarzenie {
    private final Trasa trasa;
    private final Sportowiec sportowiec;
    private final List<Trasa> listaTras;

    public KoniecZjazdu(int czas,
                        Sportowiec sportowiec,
                        Trasa trasa,
                        List<Trasa> listaTras,
                        InterfaceKolejkiZdarzeń kolejkaZdarzeń) {

        super(czas, kolejkaZdarzeń);
        this.trasa = trasa;
        this.sportowiec = sportowiec;
        this.listaTras = listaTras;
    }

    @Override
    public void wykonaj() {
        KomunikatAktywności.odnotuj(
                getCzas(),
                sportowiec,
                "zakończył zjazd trasą nr " + trasa.getId() + "."
        );

        sportowiec.zarejestrujZjazdTrasą(trasa);

        getKolejkaZdarzeń().wstaw(new PrzybycieDoWęzła(
                getCzas(),
                sportowiec,
                trasa.getKońcowaStacja(),
                getKolejkaZdarzeń(),
                listaTras
        ));
    }
}
