package stok;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sportowcy.Sportowiec;
import sportowcy.SportowiecLokalny;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Implementacja testów sprawdzających poprawność działania wyciągu.
 */
public class WyciągTest {
    private Wyciąg wyciąg;

    private Sportowiec utwórzSportowca(int id) {
        return new SportowiecLokalny(id, 1, 1, 1, 1, 1, 1, false);
    }
    @BeforeEach
    void przygotujWyciąg() {
        Węzeł stacjaPoczątkowa = new Węzeł(0, 0, 0, true);
        Węzeł stacjaKońcowa = new Węzeł (1, 1, 0, true);
        this.wyciąg = new Wyciąg(1, stacjaPoczątkowa, stacjaKońcowa, 1, 3, 1, 0);

    }

    @Test
    void pobranie3Z4PrzyPojemności3Test() {
        /* Ustawienie 4 sportowców w kolejce do wyciągu o pojemności 3. */
        for(int i = 0; i < 4; i++) {
            wyciąg.dodajDoKolejki(utwórzSportowca(i), 0);
        }

        Sportowiec[] pobrani = wyciąg.pobierzOsobyNaWyciąg(1);

        assertEquals(3, pobrani.length,
                "Wyciąg powinien pobrać 3 osoby do wjazdu.");
        assertEquals(3, wyciąg.getIleOsóbJechało(),
                "Licznik osób korzystających z wyciągu powinien wzrosnąć o 3." );
    }

    @Test
    void pobranieWszystkich2PrzyPojemności3Test() {
        /* Ustawienie 2 sportowców w kolejce do wyciągu o pojemności 3. */
        for(int i = 0; i < 2; i++) {
            wyciąg.dodajDoKolejki(utwórzSportowca(i), 0);
        }

        Sportowiec[] pobrani = wyciąg.pobierzOsobyNaWyciąg(1);

        assertEquals(2, pobrani.length,
                "Wszyscy oczekujący sportowcy powinnni wsiąść na wyciąg.");
        assertEquals(2, wyciąg.getIleOsóbJechało(),
                "Licznik osób korzystających z wyciągu powinien wzrosnąć o 2." );

    }

    @Test
    void zapamiętanieMaxDługościKolejki4PoOdjeździeTest() {
        /* Ustawienie 4 sportowców w kolejce do wyciągu o pojemności 3. */
        for(int i = 0; i < 4; i++) {
            wyciąg.dodajDoKolejki(utwórzSportowca(i), 0);
        }

        /* Wyjazd części osób, a następnie dołączenie kolejnego sportowca. */
        wyciąg.pobierzOsobyNaWyciąg(1);
        wyciąg.dodajDoKolejki(utwórzSportowca(4), 2);

        assertEquals(4, wyciąg.getMaksymalnaDługośćKolejki(),
                "Maksymalna długość kolejki powinna wynosić 4.");
    }


}
