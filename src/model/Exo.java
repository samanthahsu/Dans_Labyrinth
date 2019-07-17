package model;

public class Exo extends Interactable implements Item, Creature {

    String name;
    String description;
    int status;
    int ypos;
    int xpos;

    //    EFFECTS: set starting coordinates
    public Exo(int y, int x) {
        this.ypos = y;
        this.xpos = x;
        description = "a fuzzy black orange sized creature stares back at "
                + "you with a deploring gaze";
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

    public int getYpos() {
        return ypos;
    }

    public int getXpos() {
        return xpos;
    }

    //    EFFECTS: doesn't move at all
    @Override
    public void move() {

    }

//    EFFECTS: explodes causing those on the same tile mild damage
    @Override
    public void attack(Map map) {
        int futureStat = map.getAva().getStatus() + 1;
        if (futureStat <= 2) {
            map.getAva().setStatus(futureStat);
        }
    }

//    EFFECTS: prints
    @Override
    public void speak() {
        System.out.println("a sad ticking noise fills the hall");
    }

// EFFECTS: explodes everything on the tile
//  todo gets used as a ranged weapon (like grenade) against walls and creatures
    @Override
    public void useItem(Map map) {
        attack(map);
    }
}
