package model.MapObjects;

public abstract class Doer extends Examinable {

    public Doer(int y, int x) {
        super(y, x);
    }
    /*
                EFFECTS: does actions that should happen for each clock tick (determined
            by player actions)
        */
        public abstract void doPassiveActions();
}
