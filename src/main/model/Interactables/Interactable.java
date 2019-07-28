package model.Interactables;

import model.Map;

import java.io.Serializable;
import java.util.Objects;

/*An object on the overlay map of interactables*/
public abstract class Interactable implements Serializable {
//interactable would be one per tile, could be an instance event, creature, or item

    public static final int TYPE_CREATURE = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FEATURE = 2;
    public static final int TYPE_SOUND = 3;
    public static final String EXIT_EXAMINATION = "back";

//    the map this belongs to
    protected Map map;
//    used to id interactable
    protected String name;
    protected String description;
//    detailed description accessible by the "examine" command
    protected String examineDescription;
    protected int currentY;
    protected int currentX;
//    identifies which kind of interactable this is
    protected int typeId; // 0=creature, 1=item, 3=feature
    protected String ui;

    public Interactable(int y, int x) {
        currentY = y;
        currentX = x;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYpos() {
        return currentY;
    }

    public int getXpos() {
        return currentX;
    }

    public String getExamineDescription() {
        return examineDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Interactable)) return false;
        Interactable that = (Interactable) o;
        return typeId == that.typeId &&
                Objects.equals(map, that.map) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(examineDescription, that.examineDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(map, name, description, examineDescription, typeId);
    }

    public abstract boolean interact(String target);

    public int getTypeId() {
        return typeId;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    /*effects: handles examining actions and returns string to be printed
    * gives user access to object specific actions*/
    public abstract boolean examine(String ui);
}

