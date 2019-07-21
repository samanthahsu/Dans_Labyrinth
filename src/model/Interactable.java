package model;

import java.io.Serializable;

/*An object on the overlay map of interactables*/
public abstract class Interactable implements Serializable {
//interactable would be one per tile, could be an instance event, creature, or item
    protected String name;
    protected String description;
    protected int startY;
    protected int startX;
    protected boolean isItem;
    protected boolean isCreature;

    public Interactable(int y, int x) {
        startY = y;
        startX = x;
    }

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

    public abstract boolean interact(Map map);

    /* returns true if interactable == this*/
    public boolean equals (Interactable interactable) {
        return false;
    }

}

