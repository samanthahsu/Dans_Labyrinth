package model.creatures;

import model.Map;
import model.features.AbsolutelyNothing;

public class Exo extends Creature {

    //    EFFECTS: set starting coordinates
    public Exo(int y, int x) {
        super(y, x);
        description = "a fuzzy black orange sized creature stares back at "
                + "you with a deploring gaze";
        name = "Exo";
        health = 1;
        isCreature = true;
        isItem = false;
    }

    @Override
    void move(Map map) {
//        todo stub
    }

//    EFFECTS:
    @Override
    public void interact(Map map) {
//        todo stub
        attack(map);
    }

//    REQUIRES: only called when ava is on same tile
//    EFFECTS: explodes causing those on the same tile by 1,
//      eliminates itself from map
    @Override
    public void attack(Map map) {
        int futureStat = map.getAva().getStatus() - 1;
        if (futureStat >= 0) {
            map.getAva().setStatus(futureStat);
        }
        map.getInteractables().get(startY).set(startX, new AbsolutelyNothing());
        System.out.println("KABOOM!");
    }

//    EFFECTS: prints
    @Override
    public void speak() {
        System.out.println("a sad ticking noise fills the hall");
    }
}
