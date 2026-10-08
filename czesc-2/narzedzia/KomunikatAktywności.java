package narzedzia;

import sportowcy.Sportowiec;
import zdarzenia.Zdarzenie;

public class KomunikatAktywności {

    /* Uniemożliwienie tworzenia obiektów tej klasy. */
    private KomunikatAktywności() {}

    /* Wypisuje komunikat o aktywności podjętej przez sportowca, jeśli jest śledzony. */
    public static void odnotuj(int czas, Sportowiec sportowiec, String aktywność) {
        if (sportowiec.czyŚledzony()) {
            System.out.println(Zdarzenie.czasTekstowy(czas) +
                    ": Sportowiec " + sportowiec.getId() + " " + aktywność);
        }
    }
}
