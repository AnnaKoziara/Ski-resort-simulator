package symulacja;

import java.util.Scanner;

/*
 * Klasa uruchomieniowa służąca do przeprowadzenia symulacji
 * na podstawie danych wczytanych z wejścia.
 */
public class Main {

    public static void main(String[] args) {
        Symulacja symulacja = new Symulacja();
        Scanner skaner = new Scanner(System.in);

        symulacja.wczytajDane(skaner);
        symulacja.wykonajSymulację();
    }
}
