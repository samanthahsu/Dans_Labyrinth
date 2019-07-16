package model;

public class Exo  implements Item, Creature {

    String name;
    String description;
    int status;
    int startY;
    int startX;

    //    EFFECTS:
    public Exo(int y, int x){
        startY = y;
        startX = x;
        description = "a fuzzy black orange sized creature stares back at " +
                "you with a deploring gaze";
        name = "Bob";
        status = 0;

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
