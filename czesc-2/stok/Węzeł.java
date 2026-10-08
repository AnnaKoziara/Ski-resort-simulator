package stok;

import java.util.Arrays;

/* Reprezentacja stacji na stoku narciarskim. */
public class Węzeł {
    private final int wysokość;
    private final int x;
    private final int y;
    private final boolean czySkomunikowany;

    private Wyciąg[] tablicaWyciągów = new Wyciąg[2];
    private int ileWyciągów = 0;

    private Trasa[] tablicaTras = new Trasa[2];
    private int ileTras = 0;

    public Węzeł(int wysokość, int x, int y, boolean czySkomunikowany)  {
        this.wysokość = wysokość;
        this.x = x;
        this.y = y;
        this.czySkomunikowany = czySkomunikowany;
    }

    public void dodajWyciąg(Wyciąg nowy) {
        if (ileWyciągów == tablicaWyciągów.length) {
            tablicaWyciągów = Arrays.copyOf(tablicaWyciągów, ileWyciągów * 2);
        }
        tablicaWyciągów[ileWyciągów] = nowy;
        ileWyciągów++;
    }

    public void dodajTrasę(Trasa nowa) {
        if (ileTras == tablicaTras.length) {
            tablicaTras = Arrays.copyOf(tablicaTras, ileTras * 2);
        }
        tablicaTras[ileTras] = nowa;
        ileTras++;
    }

    public int getIleTras() {
        return ileTras;
    }
    public Trasa getTrasa(int indeks) {
        return tablicaTras[indeks];
    }

    public int getIleWyciągów() {
        return ileWyciągów;
    }

    public Wyciąg getWyciąg(int indeks) {
        return tablicaWyciągów[indeks];
    }
}
