package model.Interactables.items;

import model.Interactables.Interactable;

/**An item that can be picked off the FLOOR, then used for various things**/
/*generally, items don't change, and have the similar properties of interactables, and the ability
 * to be used by the player. thus this need not be an abstract class, but a general one, and interactions can come
 * mainly from other classes recognizing the object name
 * they:
 * print out a description when interacted with
 * they only need x and y position for map placement, doesn't matter in equality*/
/*nevermind, scratch that i need separate objects for each*/
public abstract class Item extends Interactable {

    /*constructor
    * for already held objects */
    public Item() {
        super(0, 0);
        typeId = TYPE_ITEM;
    }

/*
    constructor for items found at specific coordinates
*/
    public Item(int y, int x) {
        super(y, x);
        typeId = TYPE_ITEM;
    }

    @Override
    /*general interact method*/
    public abstract boolean interact(String target);
/*
    returns true if two items are equal if their name is the same
*/
    public boolean equals(Item otherItem) {
        return this.name.equals(otherItem.getName());
    }
}
