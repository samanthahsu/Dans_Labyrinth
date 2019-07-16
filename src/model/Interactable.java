package model;

public abstract class Interactable {

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

    public int getY() {
        return startY;
    }

    public int getX() {
        return startX;
    }
}
