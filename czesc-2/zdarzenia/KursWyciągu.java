package zdarzenia;

import narzedzia.KomunikatAktywności;
import sportowcy.Sportowiec;
import stok.Wyciąg;

/* Zdarzenie reprezentujące cykliczny przejazd wyciągu. */
public class KursWyciągu extends Zdarzenie{
    private final Wyciąg wyciąg;

    public KursWyciągu(int czas, InterfaceKolejkiZdarzeń kolejkaZdarzeń, Wyciąg wyciąg){
        super(czas, kolejkaZdarzeń);
        this.wyciąg = wyciąg;
    }

    @Override
    public void wykonaj() {
        if (getCzas() >= KONIEC_CZASU_WYCIĄGÓW) {
            return;
        }

        int czasNastępnegoKursu = getCzas() + wyciąg.getOdstępCzasowy();

        /* Wstawienie zdarzenia do kolejki, jeśli nie wykroczy poza godzinę 16:00:00. */
        if (czasNastępnegoKursu < KONIEC_CZASU_WYCIĄGÓW) {
            getKolejkaZdarzeń().wstaw(new KursWyciągu(
                    czasNastępnegoKursu,
                    getKolejkaZdarzeń(),
                    wyciąg
            ));
        }

        /* Po godzinie 15:00:00 wyciąg startuje pusty. */
        if (getCzas() >= KONIEC_CZASU_DECYZJI) {
            return;
        }

        Sportowiec[] osobyZKolejki = wyciąg.pobierzOsobyNaWyciąg();

        for(int i = 0; i < osobyZKolejki.length; i++) {
            wyciąg.odnotujPrzejazd();
            KomunikatAktywności.odnotuj(
                    getCzas(),
                    osobyZKolejki[i],
                    "rozpoczął wjazd wyciągiem nr " + wyciąg.getId() + "."
            );

            getKolejkaZdarzeń().wstaw(new KoniecWjazdu(
                    getCzas() + wyciąg.getCzasPrzejazdu(),
                    osobyZKolejki[i],
                    wyciąg,
                    getKolejkaZdarzeń()
            ));
        }
    }
}
