package ui;

import java.util.ArrayList;
import java.util.List;

public class PrintObservable {

    private List<PrintObserver> observers = new ArrayList<>();

    protected void addObserver(PrintObserver o) {
        observers.add(o);
    }
    /*requires:
     * modifies:
     * effects: notifies observers in list*/
    protected void notifyObservers(String message) {
        for (PrintObserver o : observers) {
            o.update(message);
        }
    }
}
