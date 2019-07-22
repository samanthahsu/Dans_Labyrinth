package model;

import java.io.Serializable;

/*An object on the overlay map of interactables*/
public abstract class Interactable implements Serializable {
//interactable would be one per tile, could be an instance event, creature, or item

    public static final int TYPE_CREATURE = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FEATURE = 2;

    protected String name;
    protected String description;
    protected int startY;
    protected int startX;
//    identifies which kind of interactable this is
    protected int typeId; // 0=creature, 1=item, 3=feature

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

