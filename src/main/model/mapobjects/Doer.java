package model.mapobjects;

public interface Doer {
        /*
                EFFECTS: does actions that should happen for each clock tick (determined
            by player actions)
        */
    void doPassiveActions();
}
