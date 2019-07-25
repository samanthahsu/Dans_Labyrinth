package model.Interactables;

import model.Map;

import java.io.Serializable;

/*An object on the overlay map of interactables*/
public abstract class Interactable implements Serializable {
//interactable would be one per tile, could be an instance event, creature, or item

    public static final int TYPE_CREATURE = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FEATURE = 2;
    public static final int TYPE_SOUND = 3;

//    the map this belongs to
    protected Map map;
    protected String name;
    protected String description;
    protected int currY;
    protected int currX;
//    identifies which kind of interactable this is
    protected int typeId; // 0=creature, 1=item, 3=feature

    public Interactable(int y, int x) {
        currY = y;
        currX = x;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYpos() {
        return currY;
    }

    public int getXpos() {
        return currX;
    }

    public abstract boolean interact(Map map);

    public int getTypeId() {
        return typeId;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /* returns true if interactable == this*/
    public boolean equals (Interactable interactable) {
        return false;
    }//todo

}

