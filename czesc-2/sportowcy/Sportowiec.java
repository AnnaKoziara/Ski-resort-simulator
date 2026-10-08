package sportowcy;

import narzedzia.Losowanie;
import stok.*;

import java.util.*;

public abstract class Sportowiec {
    protected static final double POCZĄTKOWA_ATRAKCYJNOŚĆ = -1.0;
    private final int id;
    private final int poziomZaawansowania;
    protected final double spontaniczność;
    protected final double współczynnikZnudzenia;
    private final double wagaTrudności;
    private final double wagaWyrównania;
    private final double wagaZnudzenia;
    private final boolean czyŚledzony;
    private final Map<Trasa, Integer> zjazdyTrasą;
    private final Map<KrawędźGrafu, List<Integer>> historiaPrzejazdów;
    private final Map<Trasa, ParametryZnudzenia> wartościZnudzeń;
    protected int liczbaZjazdów = 0;
    private int numerPrzejazdu = 1;

    public Sportowiec(int id,
                      int poziomZaawansowania,
                      double spontaniczność,
                      double współczynnikZnudzenia,
                      double wagaTrudności,
                      double wagaWyrównania,
                      double wagaZnudzenia,
                      boolean czyŚledzony) {

        this.id = id;
        this.poziomZaawansowania = poziomZaawansowania;
        this.spontaniczność = spontaniczność;
        this.współczynnikZnudzenia = współczynnikZnudzenia;
        this.wagaTrudności = wagaTrudności;
        this.wagaWyrównania = wagaWyrównania;
        this.wagaZnudzenia = wagaZnudzenia;
        this.czyŚledzony = czyŚledzony;

        this.zjazdyTrasą = new HashMap<>();
        this.historiaPrzejazdów = new LinkedHashMap<>();
        this.wartościZnudzeń = new HashMap<>();
    }

    /* Wybranie drogi docelowej zgodnie z określonym typem sportowca. */
    public KrawędźGrafu wybierzDrogę(Węzeł węzeł, List<Trasa> wszystkieTrasy) {
        KrawędźGrafu wybranaDroga = podejmijDecyzję(węzeł, wszystkieTrasy);

        if (wybranaDroga != null) {
            if (!historiaPrzejazdów.containsKey(wybranaDroga)) {
                historiaPrzejazdów.put(wybranaDroga, new ArrayList<>());
            }

            historiaPrzejazdów.get(wybranaDroga).add(numerPrzejazdu);
            numerPrzejazdu++;
        }
        return wybranaDroga;
    }

    protected abstract KrawędźGrafu podejmijDecyzję(Węzeł węzeł, List<Trasa> wszystkieTrasy);

    public void zarejestrujZjazdTrasą(Trasa trasa) {
        /* Aktualizacja licznika zjazdów daną trasą. */
        zjazdyTrasą.put(trasa, zjazdyTrasą.getOrDefault(trasa, 0) + 1);

        double aktualneZnudzenie = obliczAktualneZnudzenie(trasa);

        this.liczbaZjazdów++;

        /* Wyznaczenie nowego znudzenia dla trasy za pomocą mechanizmu wygładzania wykładniczego. */
        double noweZnudzenie = this.współczynnikZnudzenia +
                (1 - this.współczynnikZnudzenia) * aktualneZnudzenie;

        wartościZnudzeń.put(trasa, new ParametryZnudzenia(this.liczbaZjazdów, noweZnudzenie));
    }

    public double obliczAktualneZnudzenie(Trasa trasa) {
        /* Jeśli sportowiec nigdy nie jechał tą trasą to znudzenie wynosi 0. */
        if (!wartościZnudzeń.containsKey(trasa)) {
            return 0.0;
        }

        ParametryZnudzenia znudzenie = wartościZnudzeń.get(trasa);
        int ilośćZjazdów= this.liczbaZjazdów - znudzenie.getNumerZjazdu();

        return znudzenie.getWartośćZnudzenia() *
                Math.pow(1.0 - this.współczynnikZnudzenia, ilośćZjazdów);
    }

    protected KrawędźGrafu losowyWybór(Węzeł węzeł) {
        int ileTras = węzeł.getIleTras();
        int ileWyciągów = węzeł.getIleWyciągów();

        int losowaDecyzja = Losowanie.losowyWybór(ileTras + ileWyciągów);
        if (losowaDecyzja < ileTras) {
            return węzeł.getTrasa(losowaDecyzja);
        }
        else {
            return węzeł.getWyciąg(losowaDecyzja - ileTras);
        }
    }

    public int podajLiczbęZjazdów(Trasa trasa) {
        return zjazdyTrasą.getOrDefault(trasa, 0);
    }

    public int getId() { return id; }

    public int getPoziomZaawansowania() { return poziomZaawansowania; }

    public double getWagaTrudności() { return wagaTrudności; }

    public double getWagaWyrównania() { return wagaWyrównania; }

    public double getWagaZnudzenia() {
        return wagaZnudzenia;
    }

    public boolean czyŚledzony() { return czyŚledzony; }

    public Map<KrawędźGrafu, List<Integer>> getHistoriaPrzejazdów() {
        return new HashMap<>(historiaPrzejazdów);
    }
}



