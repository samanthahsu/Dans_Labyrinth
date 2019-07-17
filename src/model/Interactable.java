package model;

public abstract class Interactable {
//todo change this to interface, and change item and creature to abstract classes
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
}
// abstract classes because
// - implements can be done multiple times, while extends can only be done once
// - you lose the ability to not have to implement all the methods in the class,
// - you might accidentally instantiate it, but compared to the loss of the other
//   two, it's alright