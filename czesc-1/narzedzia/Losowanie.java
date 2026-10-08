package narzedzia;

import java.util.Random;

public class Losowanie {
    private static final Random generatorLosowy = new Random();

    /* Uniemożliwienie tworzenia obiektów tej klasy. */
    private Losowanie() {}

    /* Sprawdzenie czy nastąpiła decyzja podjęta spontanicznie. */
    public static boolean czySpontaniczny(double współczynnikSpontaniczności) {
        return generatorLosowy.nextDouble(0.0, 1.0) < współczynnikSpontaniczności;
    }

    /* Losowanie liczby całkowitej z przedziału [0, zakres). */
    public static int losowyWybór(int zakres) {
        return generatorLosowy.nextInt(zakres);
    }
}
