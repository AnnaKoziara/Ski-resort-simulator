package symulacja;

import kadra.mapki.pliki.WyjatekSystemuPlikow;

import java.util.Scanner;

/**
 * Klasa uruchomieniowa służąca do przeprowadzenia symulacji
 * na podstawie danych wczytanych z wejścia.
 */
public class Main {

    public static void main(String[] args) {

        /* Sytuacja, w której nie została podana ścieżka pliku. */
        if(args.length == 0) {
            System.err.println("Nie podano ścieżki do zapisu map.");
            return;
        }

        String ścieżkaPliku = args[0];

        Symulacja symulacja = new Symulacja();
        Scanner skaner = new Scanner(System.in);

        try {
            symulacja.wczytajDane(skaner);
            symulacja.wykonajSymulację();

            symulacja.wygenerujMapy(ścieżkaPliku);

            System.out.println("Mapki pomyślnie zostały wygenerowane i znajdują się w folderze " +
                    ścieżkaPliku);
        }
        catch (WyjatekSystemuPlikow błąd) {
            System.err.println("Błąd zapisu mapki do podanej ścieżki.");
            System.err.println("Upewnij się, że podana ścieżka jest poprawna i posiadasz odpowiednie uprawnienia.");
            błąd.printStackTrace();
        }
        catch (Exception błąd) {
            System.err.println("Wystąpił błąd obsługi programu.");
            System.err.println("Proszę zgłosić ten błąd zespołowi deweloperskiemu.");
            błąd.printStackTrace();
        }

        }
}