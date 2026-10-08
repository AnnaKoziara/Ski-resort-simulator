package sportowcy;

import narzedzia.Losowanie;
import stok.KrawędźGrafu;
import stok.Trasa;
import stok.Wyciąg;
import stok.Węzeł;

public class Sportowiec {
    private static final double POCZĄTKOWA_ATRAKCYJNOŚĆ = -1.0;
    private final int id;
    private final int poziomZaawansowania;
    private final double spontaniczność;
    private final double wagaTrudności;
    private final double wagaWyrównania;
    private final boolean czyŚledzony;

    public Sportowiec(int id,
                      int poziomZaawansowania,
                      double spontaniczność,
                      double wagaTrudności,
                      double wagaWyrównania,
                      boolean czyŚledzony) {

        this.id = id;
        this.poziomZaawansowania = poziomZaawansowania;
        this.spontaniczność = spontaniczność;
        this.wagaTrudności = wagaTrudności;
        this.wagaWyrównania = wagaWyrównania;
        this.czyŚledzony = czyŚledzony;
    }

    private KrawędźGrafu losowyWybór(Węzeł węzeł) {
        int ileTras = węzeł.getIleTras();
        int ileWyciągów = węzeł.getIleWyciągów();

        int losowaDecyzja = Losowanie.losowyWybór(ileTras + ileWyciągów);
        if (losowaDecyzja < ileTras) {
            return węzeł.getTrasa(losowaDecyzja);
        } else {
            return węzeł.getWyciąg(losowaDecyzja - ileTras);
        }
    }

    /* Podjęcie decyzji przez sportowca co zrobić po zejściu z wyciągu. */
    public KrawędźGrafu wybierzDrogę(Węzeł węzeł) {
        if (Losowanie.czySpontaniczny(this.spontaniczność)) {
            return losowyWybór(węzeł);
        }

        double maksAtrakcyjność = POCZĄTKOWA_ATRAKCYJNOŚĆ;
        KrawędźGrafu ostatecznyWybór = null;

        /* Analiza atrakcyjności tras wychodzących z bieżącego węzła. */
        int ileTras = węzeł.getIleTras();
        for (int i = 0; i < ileTras; i++) {
            Trasa aktualnaTrasa = węzeł.getTrasa(i);
            double aktualnaAtrakcyjność = aktualnaTrasa.podajAtrakcyjność(this);
            if (maksAtrakcyjność < aktualnaAtrakcyjność) {
                ostatecznyWybór = aktualnaTrasa;
                maksAtrakcyjność = aktualnaAtrakcyjność;
            }
        }

        /* Analiza atrakcyjności tras dostępnych bezpośrednio po wjechaniu wyciągiem. */
        int ileWyciągów = węzeł.getIleWyciągów();
        for (int i = 0; i < ileWyciągów; i++) {
            Wyciąg aktualnyWyciąg = węzeł.getWyciąg(i);
            Węzeł górnaStacja = aktualnyWyciąg.getKońcowaStacja();

            int ilośćTrasNaGórze = górnaStacja.getIleTras();
            for (int j = 0; j < ilośćTrasNaGórze; j++) {
                Trasa trasaNaGórze = górnaStacja.getTrasa(j);
                double atrakcyjnośćNaGórze = trasaNaGórze.podajAtrakcyjność(this);
                if (maksAtrakcyjność < atrakcyjnośćNaGórze) {
                    maksAtrakcyjność = atrakcyjnośćNaGórze;
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

    public int getId() { return id; }

    public int getPoziomZaawansowania() { return poziomZaawansowania; }

    public double getWagaTrudności() { return wagaTrudności; }

    public double getWagaWyrównania() { return wagaWyrównania; }

    public boolean czyŚledzony() { return czyŚledzony; }
}



