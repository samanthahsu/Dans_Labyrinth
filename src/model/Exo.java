package model;

public class Exo extends Interactable implements Item, Creature {

//    EFFECTS:
    Exo(){
        description = "a fuzzy black orange sized creature stares back at " +
                "you with a deplorable gaze";
        name = "Exo";
        status = 0;

    }
//    EFFECTS: moves one tile
    @Override
    public void move() {

    }

//    EFFECTS: explodes causing those on the same tile mild damage
    @Override
    public void attack() {

    }

//    EFFECTS: prints
    @Override
    public void speak() {
        System.out.println("tick tick tock?");
    }

// EFFECTS: gets used as a ranged weapon (like grenade) against walls and creatures
    @Override
    public void useItem() {

    }
}
