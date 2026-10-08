package sportowcy;

import stok.Trasa;
import stok.WynikPrzeszukiwania;

import java.util.List;

/**
 * Implementacja sportowca zachłannego, który układa
 * plan przejazdu do najbardziej atrakcyjnej trasy.
 */
public class SportowiecZachłanny extends SportowiecZeStrategią {

    public SportowiecZachłanny(int id,
                                 int poziomZaawansowania,
                                 double spontaniczność,
                                 double współczynnikZnudzenia,
                                 double wagaTrudności,
                                 double wagaWyrównania,
                                 double wagaZnudzenia,
                                 boolean czyŚledzony) {
        super(id, poziomZaawansowania, spontaniczność, współczynnikZnudzenia,
                wagaTrudności, wagaWyrównania, wagaZnudzenia, czyŚledzony);
    }

    @Override
    protected Trasa wybierzCel(List<Trasa> wszystkieTrasy, WynikPrzeszukiwania wynik) {
        Trasa najlepszaTrasa = null;
        double maksAtrakcyjność = POCZĄTKOWA_ATRAKCYJNOŚĆ;

        for (Trasa pobrana: wszystkieTrasy) {
            double atrakcyjnośćPobranej = pobrana.podajAtrakcyjność(this);
            if (atrakcyjnośćPobranej > maksAtrakcyjność) {
                maksAtrakcyjność = atrakcyjnośćPobranej;
                najlepszaTrasa = pobrana;
            }
        }
        return najlepszaTrasa;
    }
}
