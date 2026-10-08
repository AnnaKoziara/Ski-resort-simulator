package sportowcy;

import narzedzia.Losowanie;
import stok.KrawędźGrafu;
import stok.Trasa;
import stok.Wyciąg;
import stok.Węzeł;

import java.util.List;

/**
 * Implementacja sportowca lokalnego, który nie ma długoterminowej strategii.
 */
public class SportowiecLokalny extends Sportowiec {

    public SportowiecLokalny(int id,
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
    protected KrawędźGrafu podejmijDecyzję(Węzeł węzeł, List<Trasa> wszystkieTrasy) {
        if (Losowanie.czySpontaniczny(this.spontaniczność)) {
            return losowyWybór(węzeł);
        }

        double maksAtrakcyjność = POCZĄTKOWA_ATRAKCYJNOŚĆ;
        KrawędźGrafu ostatecznyWybór = null;

        /* Analiza atrakcyjności tras wychodzących z bieżącego węzła. */
        Trasa najlepszaBezpośrednia = znajdźNajlepsząTrasę(węzeł);
        if (najlepszaBezpośrednia != null) {
            maksAtrakcyjność = najlepszaBezpośrednia.podajAtrakcyjność(this);
            ostatecznyWybór = najlepszaBezpośrednia;
        }

        /* Analiza atrakcyjności tras dostępnych bezpośrednio po wjechaniu wyciągiem. */
        int ileWyciągów = węzeł.getIleWyciągów();
        for (int i = 0; i < ileWyciągów; i++) {
            Wyciąg aktualnyWyciąg = węzeł.getWyciąg(i);
            Trasa najlepszaPośrednia = znajdźNajlepsząTrasę(aktualnyWyciąg.getKońcowaStacja());

            if (najlepszaPośrednia != null) {
                double atrakcyjność = najlepszaPośrednia.podajAtrakcyjność(this);
                if (atrakcyjność > maksAtrakcyjność) {
                    maksAtrakcyjność = atrakcyjność;
                    ostatecznyWybór = aktualnyWyciąg;
                }
            }
        }

        /* Jeśli z aktualnego węzła i węzła będącego końcem wyciagu bezpośredniego
        nie ma tras, to należy wybrać dowolny wyciąg. */
        if (ostatecznyWybór == null) {
            ostatecznyWybór = losowyWybór(węzeł);
        }

        return ostatecznyWybór;
    }

    private Trasa znajdźNajlepsząTrasę(Węzeł węzeł) {
        Trasa najlepszaTrasa = null;
        double maks = POCZĄTKOWA_ATRAKCYJNOŚĆ;
        int ileTras = węzeł.getIleTras();

        for (int i = 0; i< ileTras; i++) {
            Trasa aktualnaTrasa = węzeł.getTrasa(i);
            double atrakcyjność = aktualnaTrasa.podajAtrakcyjność(this);
            if (atrakcyjność > maks) {
                maks = atrakcyjność;
                najlepszaTrasa = aktualnaTrasa;
            }
        }

        return najlepszaTrasa;
    }
}
