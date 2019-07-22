package model.Interactables.creatures;

import model.Map;

/*the one that tries to run away*/
/*if within 2 tiles of dan give indication of faint sound
* if within 1 tile, give loud sound indication*/
public class Ennui extends Creature {

    boolean hasKey = true;
    /*effects: set coordinates, name, and description*/
    public Ennui(int y, int x) {
        super(y, x);
        name = "ennui";
        description = "a flash of turquoise fuzz in the dark";
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

    /*moves one tile in a random direction (that is floor)
    if ava is in a 2 block radius of this, go in direction away from ava
    if there are no directions left to go, don't move
    emits sound in a 2 block radius of varying noise degrees and direction*/
    @Override
    public void doPassiveActions() {
//        move();
//        makeSound();
    }

    /*enters instance where if ennui has key either:
    * attacks (diminishes hp of ava by one)
    * speaks chitters nervously while squishing into corner hiding the object its holding
    * runs away (if anywehre to go)
    * gets fed drops key onto tile
    *
    * OR if ennui doesn't have key (aka is fed)
    * chatters about more friendly*/
    @Override
    public boolean interact(Map map) {
        return false;
    }
}
