package model.Interactables;

public abstract class Doer extends Interactable {

    public Doer(int y, int x) {
        super(y, x);
    }

    /*
            EFFECTS: does actions that should happen for each clock tick (determined
        by player actions)
    */
        public abstract void doPassiveActions();
}
