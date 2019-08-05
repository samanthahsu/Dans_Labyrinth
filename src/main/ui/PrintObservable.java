package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrintObservable {

    private List<PrintObserver> observers = new ArrayList<>();

    public void addObserver(PrintObserver o) {
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

    /*EFFECTS: prints feedback when ava tries to move into a WALL tile todo move to observer*/
    protected void notifyHitWall(String dir) {
        Random ran = new Random();
        switch (ran.nextInt(4)) {
            case 0:
                notifyObservers("Dan smacks hilariously against the " + dir + " WALL.");
                break;
            case 1:
                notifyObservers("Dan stubs his toe painfully on the " + dir + " WALL.");
                break;
            case 2:
                notifyObservers("Dan flops desperately against the " + dir + " WALL.");
                break;
            case 3:
                notifyObservers("Dan sits and ponders how his life has culminated in this moment.");
                break;
            default:
        }
    }
}
