package model.mapobjects;

import java.io.Serializable;
import java.util.Objects;

public abstract class Examinable extends Locatable implements Serializable {

    public static final int TYPE_CREATURE = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FEATURE = 2;
    //todo since everything has the same name, set as public static ID instead
//    used to id interactable
    protected String name;
    protected String description;
    protected String examineDescription;
//    identifies which kind of interactable this is
    protected int typeId; // 0=creature, 1=item, 3=feature

    public Examinable(int y, int x) {
        super(y, x);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getExamineDescription() {
        return examineDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Examinable)) {
            return false;
        }
        Examinable that = (Examinable) o;
        return  typeId == that.typeId
                && Objects.equals(name, that.name)
                && Objects.equals(description, that.description)
                && Objects.equals(examineDescription, that.examineDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, examineDescription, typeId);
    }

    /*requires: given y,x are within map
    * modifies: this, map
    * effects: sets examinable location to y, x*/
    public void setIndexes(int y, int x) {
        int oldy = getYc();
        int oldx = getXc();
        super.setYc(y);
        super.setXc(x);
        if (!getMap().getTileMatrix().get(y).get(x).getCurrExaminables().containsKey(getName())) {
            getMap().moveExaminableOnTiles(this, oldy, oldx, y, x);
        }
    }

    /* requires: typeId is not null
    * effects: returns id*/
    public int getTypeId() {
        return typeId;
    }

    /*effects: lets user examine and interact with examinable appropriately
    * returns true if input is recognized
    * false otherwise*/
    public abstract boolean examine(String ui);
}

