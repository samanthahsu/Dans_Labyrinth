package model.MapObjects;

import model.Map;

public abstract class Doer extends Examinable {

    public Doer(Map map, int y, int x) {
        super(map, y, x);
    }
    /*
                EFFECTS: does actions that should happen for each clock tick (determined
            by player actions)
        */
        public abstract void doPassiveActions();
}
