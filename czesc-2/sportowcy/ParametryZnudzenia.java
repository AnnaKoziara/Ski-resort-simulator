package sportowcy;

/**
 * Implementacja określająca dane związane ze znudzeniem.
 */
public class ParametryZnudzenia {
    private final int numerZjazdu;
    private final double wartośćZnudzenia;

    public ParametryZnudzenia(int numerZjazdu, double wartośćZnudzenia) {
        /* Liczba zjazdów sportowca w momencie pokonywania danej trasy. */
        this.numerZjazdu = numerZjazdu;

        /* Wartość znudzenia tuż po zjechaniu daną trasą. */
        this.wartośćZnudzenia = wartośćZnudzenia;
    }

    public int getNumerZjazdu() {
        return numerZjazdu;
    }

    public double getWartośćZnudzenia() {
        return wartośćZnudzenia;
    }
}
