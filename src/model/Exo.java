package model;

public class Exo extends Interactable implements Item, Creature {

    String name;
    String description;
    int status;
    int startY;
    int startX;

    //    EFFECTS: set starting coordinates
    public Exo(int y, int x){
        startY = y;
        startX = x;
        description = "a fuzzy black orange sized creature stares back at " +
                "you with a deploring gaze";
        name = "Exo";
        status = 0;
        isCreature = true;
        isItem = true;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public int getStartY() {
        return startY;
    }

    public int getStartX() {
        return startX;
    }

    //    EFFECTS: doesn't move at all
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
        System.out.println("a sad ticking noise fills the hall");
    }

// EFFECTS: gets used as a ranged weapon (like grenade) against walls and creatures
    @Override
    public void useItem() {

    }
}
