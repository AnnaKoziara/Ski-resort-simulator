package sportowcy;

import narzedzia.Losowanie;
import stok.*;

import java.util.List;

public abstract class SportowiecZeStrategią extends Sportowiec {
    protected static final int POCZĄTKOWE_MINIMUM_ZJAZDÓW = Integer.MAX_VALUE;
    private Trasa docelowaTrasa = null;

    public SportowiecZeStrategią(int id,
                             int poziomZaawansowania,
                             double spontaniczność,
                             double współczynnikZnudzenia,
                             double wagaTrudności,
                             double wagaWyrównania,
                             double wagaZnudzenia,
                             boolean czyŚledzony) {
        super(id, poziomZaawansowania, spontaniczność, współczynnikZnudzenia,
                wagaTrudności, wagaWyrównania, wagaZnudzenia, czyŚledzony);
    }

    @Override
    public void zarejestrujZjazdTrasą(Trasa trasa) {
        super.zarejestrujZjazdTrasą(trasa);

        /* Sprawdzenie czy cel ze strategii został osiągnięty. */
        if (trasa.equals(this.docelowaTrasa)) {
            this.docelowaTrasa = null;
        }
    }

    @Override
    protected KrawędźGrafu podejmijDecyzję(Węzeł węzeł, List<Trasa> wszystkieTrasy) {
        PrzeszukiwaczGrafu przeszukiwacz = new PrzeszukiwaczGrafu();
        WynikPrzeszukiwania wynik = przeszukiwacz.wyznaczNajkrótszeOdległości(węzeł);

        if (docelowaTrasa == null) {
            if (Losowanie.czySpontaniczny(this.spontaniczność)) {
                return losowyWybór(węzeł);
            }
            else {
                this.docelowaTrasa = wybierzCel(wszystkieTrasy, wynik);
            }
        }

        return znajdźPoczątekDrogi(węzeł, this.docelowaTrasa, wynik);
    }

    protected abstract Trasa wybierzCel(List<Trasa> wszystkieTrasy, WynikPrzeszukiwania wynik);

    /* Metoda, która ustala jaką trasę sportowiec powinien wybrać, aby dostać się
    do swojej docelowej trasy. */
    private KrawędźGrafu znajdźPoczątekDrogi(Węzeł aktualnyWęzeł,
                                             Trasa wybranaTrasa,
                                             WynikPrzeszukiwania wynikBfs) {
        Węzeł przeszukiwanyWęzeł = wybranaTrasa.getPoczątkowaStacja();

        /* Jeśli sportowiec już jest w stacji początkowej docelowej trasy to
         jest to trasa, którą powinien zjechać. */
        if (przeszukiwanyWęzeł.equals(aktualnyWęzeł)) {
            return wybranaTrasa;
        }

        KrawędźGrafu krawędźSzukana = null;
        while (!przeszukiwanyWęzeł.equals(aktualnyWęzeł)) {
            /* Sprawdzenie, którą krawędzią nastąpiło dojście do tego węzła. */
            krawędźSzukana = wynikBfs.getKrawędzieWejściowe().get(przeszukiwanyWęzeł);
            przeszukiwanyWęzeł = krawędźSzukana.getPoczątkowaStacja();
        }

        return krawędźSzukana;
    }
}
