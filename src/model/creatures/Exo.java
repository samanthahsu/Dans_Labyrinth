package model.creatures;

import model.Map;

public class Exo extends Creature {

    //    EFFECTS: set starting coordinates
    public Exo(int y, int x) {
        super(y, x);
        description = "a fuzzy black orange sized creature stares back at "
                + "you with a deploring gaze";
        name = "Exo";
        status = 0;
        isCreature = true;
        isItem = false;
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

    @Override
    void move(Map map) {

    }

    @Override
    public void interact(Map map) {

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
}
