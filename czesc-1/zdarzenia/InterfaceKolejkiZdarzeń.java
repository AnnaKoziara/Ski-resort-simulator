package zdarzenia;

public interface InterfaceKolejkiZdarzeń {

    void wstaw(Zdarzenie zdarzenie);

    Zdarzenie pobierzZdarzenie() throws BrakZdarzeń;

    boolean czyPusta();
}
