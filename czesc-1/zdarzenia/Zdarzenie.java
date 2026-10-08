package zdarzenia;

/* Abstrakcyjna klasa dla wszystkich zdarzeń symulacji. */
public abstract class Zdarzenie {
    /* Czas (15:00:00), w którym sportowcy przestają podejmować decyzje. */
    protected static final int KONIEC_CZASU_DECYZJI = 15 * 3600;

    /* Czas (16:00:00), w którym wyciągi kończą działać. */
    protected static final int KONIEC_CZASU_WYCIĄGÓW = 16 * 3600;

    private final int czas;
    private final InterfaceKolejkiZdarzeń kolejkaZdarzeń;

    public Zdarzenie(int czas, InterfaceKolejkiZdarzeń kolejkaZdarzeń) {
        this.czas = czas;
        this.kolejkaZdarzeń = kolejkaZdarzeń;
    }

    public abstract void wykonaj();

    /* Zamiana sekund na tekstową reprezentacje godziny w formacie GG:MM:SS. */
    public static String czasTekstowy(int ilośćSekund) {
        int godziny = ilośćSekund / 3600;
        int minuty = (ilośćSekund % 3600) / 60;
        int sekundy = ilośćSekund % 60;

        return String.format("%02d:%02d:%02d", godziny, minuty, sekundy);
    }

    public int getCzas() {
        return czas;
    }

    protected InterfaceKolejkiZdarzeń getKolejkaZdarzeń() {
        return kolejkaZdarzeń;
    }
}
