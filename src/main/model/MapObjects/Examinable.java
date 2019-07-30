package model.MapObjects;

import java.io.Serializable;
import java.util.Objects;

public abstract class Examinable extends Locatable implements Serializable {

    public static final int TYPE_CREATURE = 0;
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FEATURE = 2;
    public static final String EXIT_EXAMINATION = "back";

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
        if (this == o) return true;
        if (!(o instanceof Examinable)) return false;
        Examinable that = (Examinable) o;
        return  typeId == that.typeId &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(examineDescription, that.examineDescription);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, description, examineDescription, typeId);
    }

    public int getTypeId() {
        return typeId;
    }

    /*effects: handles examining actions and returns string to be printed
    * gives user access to object specific actions*/
    public abstract boolean examine(String ui);
}

