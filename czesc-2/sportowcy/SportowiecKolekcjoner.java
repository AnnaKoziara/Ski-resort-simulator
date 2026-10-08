package sportowcy;

import stok.Trasa;
import stok.WynikPrzeszukiwania;

import java.util.List;

/**
 * Implementacja sportowca kolekcjonerskiego, który
 * stara się odwiedzić jak najwięcej tras.
 */
public class SportowiecKolekcjoner extends SportowiecZeStrategią {
    private static final int POCZĄTKOWA_MINIMALNA_ODLEGŁOŚĆ = Integer.MAX_VALUE;

    public SportowiecKolekcjoner(int id,
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
    protected Trasa wybierzCel(List<Trasa> wszystkieTrasy, WynikPrzeszukiwania wynik) {
        Trasa najlepszaTrasa = null;
        int najmniejZjazdów = POCZĄTKOWE_MINIMUM_ZJAZDÓW;
        int najmniejszaOdległość = POCZĄTKOWA_MINIMALNA_ODLEGŁOŚĆ;
        double największaAtrakcyjność = POCZĄTKOWA_ATRAKCYJNOŚĆ;

        for (Trasa pobrana: wszystkieTrasy) {
            int ilośćZjazdów = this.podajLiczbęZjazdów(pobrana);

            /* Odległość do stacji początkowej danej trasy. */
            int odległość = wynik.getOdległości().get(pobrana.getPoczątkowaStacja());

            double atrakcyjność = pobrana.podajAtrakcyjność(this);

            if (czyTrasaJestLepsza(ilośćZjazdów, najmniejZjazdów, odległość,
                    najmniejszaOdległość, atrakcyjność, największaAtrakcyjność)) {
                najmniejZjazdów = ilośćZjazdów;
                najmniejszaOdległość = odległość;
                najlepszaTrasa = pobrana;
                największaAtrakcyjność = atrakcyjność;
                }
        }
        return najlepszaTrasa;
    }

    private boolean czyTrasaJestLepsza(int ilośćZjazdów, int najmniejZjazdów,
                                       int odległość, int najmniejszaOdległość,
                                       double atrakcyjność, double największaAtrakcyjność) {
        if (ilośćZjazdów > najmniejZjazdów) {
            return false;
        }
        if (ilośćZjazdów < najmniejZjazdów) {
            return true;
        }

        /* W przypadku jednakowej ilość zjazdów decyzuje odległość. */
        if (odległość > najmniejszaOdległość) {
            return false;
        }
        if (odległość < najmniejszaOdległość) {
            return true;
        }

        /* W przypadku jednakowej ilość zjazdów i równych odległości decyduje atrakcyjność. */
        return (atrakcyjność > największaAtrakcyjność);
    }
}

