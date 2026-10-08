package zdarzenia;

/* Wyjątek wywoływany, gdy symulacja próbuje pobrać zdarzenie z pustej kolejki zdarzeń. */
public class BrakZdarzeń extends RuntimeException {
    public BrakZdarzeń(String message) {
        super(message);
    }
}
