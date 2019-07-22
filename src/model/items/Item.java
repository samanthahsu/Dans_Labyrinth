package model.items;

import model.Interactable;
import model.Map;

/**An item that can be picked off the floor, then used for various things**/
/*generally, items don't change, and have the similar properties of interactables, and the ability
 * to be used by the player. thus this need not be an abstract class, but a general one, and interactions can come
 * mainly from other classes recognizing the object name
 * they:
 * print out a description when interacted with
 * they only need x and y position for map placement, doesn't matter in equality*/
public class Item extends Interactable {

    public Item(String name) {
        super(0, 0);
        typeId = TYPE_ITEM;
    }

//    EFFECTS: sets isCreature to false, and isItem to true
    public Item(String name, int y, int x) {
        super(y, x);
        isCreature = false;
        isItem = true;
    }

    @Override
    public boolean interact(Map map) {
        return false;
    }

/*
    returns true if two items are equal if their name is the same
*/
    public boolean equals(Item otherItem) {
        return this.name.equals(otherItem.getName());
    }
}
