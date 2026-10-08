package stok;

/* Implementacja kalkulatora, który oblicza atrakcyjność tras. */
public class KalkulatorAtrakcyjności {

    /* Uniemożliwienie tworzenia obiektów tej klasy. */
    private KalkulatorAtrakcyjności(){}

    /* Kalkulator atrakcyjności trasy na podstawie jej trudności. */
    public static double obliczDopasowanie(int poziomTrudności, int poziomZaawansowania) {
        if (poziomTrudności >= poziomZaawansowania + 5) {
            return 0.0;
        }
        double różnica = poziomZaawansowania - poziomTrudności;

        if (różnica < 0.0) {
            return 1.0 + różnica / 5.0;
        }

        return Math.max(0.2, 1.0 - różnica / 7.0);
    }

    /* Kalkulator atrakcyjności trasy wynikającej z wyrównania nawierzchni. */
    public static double obliczWyrównanie(double odporność,
                                          double bazowaAtrakcyjność,
                                          int ilośćPrzejazdów) {
        return bazowaAtrakcyjność +
                (1.0 - bazowaAtrakcyjność) * Math.pow(odporność, ilośćPrzejazdów);
    }
}