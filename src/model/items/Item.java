package model.items;

import model.Interactable;
import model.Map;

/**An item that can be picked off the floor, then used for various things**/
/*generally, items don't change, and have the similar properties of interatables, and the ability
 * to be used by the player. thus this need not be an abstract class, but a general one, and interactions can come
 * mainly from other classes recognizing the object name
 * they:
 * print out a description when interacted with
 * they only need x and y position for map placement, doesn't matter in equality*/
public class Item extends Interactable {

//    EFFECTS: sets isCreature to false, and isItem to true
    public Item(int y, int x) {
        super(y, x);
        isCreature = false;
        isItem = true;
    }

    @Override
    public boolean interact(Map map) {
        return false;
    }

/*
    todo two items are equal if the name is the same
*/
    public boolean equals()
}
