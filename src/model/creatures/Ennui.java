package model.creatures;

import model.Map;

/*the one that tries to run away*/
/*if within 2 tiles of dan give indication of faint sound
* if within 1 tile, give loud sound indication*/
public class Ennui extends Creature {

    boolean hasKey;
    /*effects: set coordinates, name, and description*/
    public Ennui(int y, int x) {
        super(y, x);
        name = "ennui";
        description = "a flash of turquoise fuzz in the dark";
        hasKey = true;
    }

    @Override
    void move(Map map) {

    }

    @Override
    void attack(Map map) {

    }

    @Override
    void speak() {

    }

    @Override
    public void doPassiveActions() {

    }

    @Override
    public boolean interact(Map map) {
        return false;
    }
}
