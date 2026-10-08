package zdarzenia;

/* Implementacja kolejki zdarzeń oparta na dynamicznie powiększanej tablicy.
 Zdarzenia są posortowane zgodnie z chronologią czasu. */
public class TablicaZdarzeń implements InterfaceKolejkiZdarzeń {
    private Zdarzenie[] tablicaZdarzeń;
    private int liczbaZdarzeń;
    private int pierwszeZdarzenie;

    public TablicaZdarzeń() {
        this.tablicaZdarzeń = new Zdarzenie[2];
        this.liczbaZdarzeń = 0;
        this.pierwszeZdarzenie = 0;
    }

    private void powiększTablice() {
        int nowyRozmiar = tablicaZdarzeń.length * 2;
        Zdarzenie[] nowaTablica = new Zdarzenie[nowyRozmiar];

        for (int i = 0; i < liczbaZdarzeń; i++) {
            nowaTablica[i] = tablicaZdarzeń[pierwszeZdarzenie + i];
        }

        this.tablicaZdarzeń = nowaTablica;
        this.pierwszeZdarzenie = 0;
    }

    @Override
    public void wstaw(Zdarzenie zdarzenie) {
        if (pierwszeZdarzenie + liczbaZdarzeń == tablicaZdarzeń.length) {
            powiększTablice();
        }

        int indeks = pierwszeZdarzenie + liczbaZdarzeń - 1;
        while (indeks >= pierwszeZdarzenie &&
                tablicaZdarzeń[indeks].getCzas() > zdarzenie.getCzas()) {
            tablicaZdarzeń[indeks + 1] = tablicaZdarzeń[indeks];
            indeks--;
        }

        tablicaZdarzeń[indeks + 1] = zdarzenie;
        liczbaZdarzeń++;
    }

    @Override
    public Zdarzenie pobierzZdarzenie() throws BrakZdarzeń {
        if (czyPusta()) {
            throw new BrakZdarzeń("Brak zdarzeń do pobrania.");
        }

        Zdarzenie najwcześniejszeZdarzenie = tablicaZdarzeń[pierwszeZdarzenie];
        tablicaZdarzeń[pierwszeZdarzenie] = null;
        pierwszeZdarzenie++;
        liczbaZdarzeń--;

        return najwcześniejszeZdarzenie;
    }

    @Override
    public boolean czyPusta() {
        return (liczbaZdarzeń == 0);
    }

}
