package model;

public abstract class Interactable {
//todo change this to interface, and change item and creature to abstract classes
//interactable would be one per tile, could be an instance event, creature, or item
    String name;
    String description;
    int startY;
    int startX;
    boolean isItem;
    boolean isCreature;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYpos() {
        return startY;
    }

    public int getXpos() {
        return startX;
    }

    public void interact() {}
}

