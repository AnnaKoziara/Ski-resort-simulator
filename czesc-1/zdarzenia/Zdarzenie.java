package zdarzenia;

/* Abstrakcyjna klasa dla wszystkich zdarzeń symulacji. */
public abstract class Zdarzenie implements Comparable<Zdarzenie> {
    /* Zmienna globalna umożliwiająca stwierdzenie, które zdarzenie pojawiło się wcześniej. */
    private static int licznikZdarzeń = 0;
    /* Czas (15:00:00), w którym sportowcy przestają podejmować decyzje. */
    protected static final int KONIEC_CZASU_DECYZJI = 15 * 3600;

    /* Czas (16:00:00), w którym wyciągi kończą działać. */
    protected static final int KONIEC_CZASU_WYCIĄGÓW = 16 * 3600;

    private final int czas;
    private final InterfaceKolejkiZdarzeń kolejkaZdarzeń;
    private final int numerZdarzenia;

    public Zdarzenie(int czas, InterfaceKolejkiZdarzeń kolejkaZdarzeń) {
        this.czas = czas;
        this.kolejkaZdarzeń = kolejkaZdarzeń;
        this.numerZdarzenia = licznikZdarzeń++;
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
    public int getNumerZdarzenia() { return numerZdarzenia; }
    protected InterfaceKolejkiZdarzeń getKolejkaZdarzeń() {
        return kolejkaZdarzeń;
    }

    @Override
    public int compareTo(Zdarzenie inne) {
        if(this.czas != inne.getCzas()) {
            return Integer.compare(this.czas, inne.getCzas());
        }
        return Integer.compare(this.numerZdarzenia, inne.getNumerZdarzenia());
    }
}
