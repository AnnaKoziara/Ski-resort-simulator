package stok;

import sportowcy.Sportowiec;

/* Implementacja bufora cyklicznego przy użyciu tablicy. */
public class KolejkaSportowców {
    private static final int ROZMIAR_POCZĄTKOWY = 2;
    private Sportowiec[] osobyZKolejki;
    private int pierwszaOsoba;
    private int ostatniSportowiec;
    private int ileOsób;

    public KolejkaSportowców() {
        this.osobyZKolejki = new Sportowiec[ROZMIAR_POCZĄTKOWY];
        this.pierwszaOsoba = 0;
        this.ostatniSportowiec = 0;
        this.ileOsób = 0;
    }

    /* Dodanie nowego sportowca na koniec kolejki. */
    public void ustawNaKońcu(Sportowiec sportowiec) {
        if (ileOsób == osobyZKolejki.length) {
            powiększTablice();
        }
        osobyZKolejki[ostatniSportowiec] = sportowiec;
        ostatniSportowiec = następnyIndeks(ostatniSportowiec);
        ileOsób++;
    }

    /* Pobranie sportowca będącego na przodzie kolejki. */
    public Sportowiec pobierzZPrzodu() {
        if (jestPusta()) {
            return null;
        }
        Sportowiec sportowiec = osobyZKolejki[pierwszaOsoba];
        osobyZKolejki[pierwszaOsoba] = null;
        pierwszaOsoba = następnyIndeks(pierwszaOsoba);
        ileOsób--;
        return sportowiec;
    }

    public boolean jestPusta() {
        return (ileOsób == 0);
    }

    private void powiększTablice() {
        int nowyRozmiar = osobyZKolejki.length * 2;
        Sportowiec[] nowaTablica = new Sportowiec[nowyRozmiar];

        for (int i = 0; i < ileOsób; i++) {
            nowaTablica[i] = osobyZKolejki[(pierwszaOsoba + i) % osobyZKolejki.length];
        }

        this.osobyZKolejki = nowaTablica;
        this.pierwszaOsoba = 0;
        this.ostatniSportowiec = ileOsób;
    }

    private int następnyIndeks(int i) {
        return (i + 1) % osobyZKolejki.length;
    }

    public int getIleOsóbCzeka() {
        return ileOsób;
    }
}