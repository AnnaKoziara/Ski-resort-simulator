package zdarzenia;

import java.util.PriorityQueue;

public class KolejkaZdarzeń implements InterfaceKolejkiZdarzeń{
    private final PriorityQueue<Zdarzenie> kolejka = new PriorityQueue<>();


    @Override
    public void wstaw(Zdarzenie zdarzenie) {
        kolejka.add(zdarzenie);
    }

    @Override
    public Zdarzenie pobierzZdarzenie() throws BrakZdarzeń {
        if(czyPusta()) throw new BrakZdarzeń("Brak zdarzeń do obsłużenia.");
        return kolejka.poll();
    }

    @Override
    public boolean czyPusta() {
        return kolejka.isEmpty();
    }
}
