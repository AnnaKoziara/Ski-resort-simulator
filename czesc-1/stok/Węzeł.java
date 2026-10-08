package stok;

import java.util.ArrayList;
import java.util.List;

/**
 *  Reprezentacja stacji na stoku narciarskim.
 */
public class Węzeł {
    private final int wysokość;
    private final int x;
    private final int y;
    private final boolean czySkomunikowany;
    private final List<Wyciąg> listaWyciągów;
    private final List<Trasa> listaTras;

    public Węzeł(int wysokość, int x, int y, boolean czySkomunikowany)  {
        this.wysokość = wysokość;
        this.x = x;
        this.y = y;
        this.czySkomunikowany = czySkomunikowany;
        this.listaWyciągów = new ArrayList<>();
        this.listaTras = new ArrayList<>();
    }

    public void dodajWyciąg(Wyciąg nowy) {
        listaWyciągów.add(nowy);
    }

    public void dodajTrasę(Trasa nowa) {
        listaTras.add(nowa);
    }

    public int getIleWyciągów() {
        return listaWyciągów.size();
    }

    public Wyciąg getWyciąg(int indeks) {
        return listaWyciągów.get(indeks);
    }

    public List<Wyciąg> getListaWyciągów() {
        return new ArrayList<>(listaWyciągów);
    }

    public int getIleTras() {
        return listaTras.size();
    }

    public Trasa getTrasa(int indeks) {
        return listaTras.get(indeks);
    }

    public List<Trasa> getListaTras() {
        return new ArrayList<>(listaTras);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean czySkomunikowany() {
        return czySkomunikowany;
    }
}
