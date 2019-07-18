package model.items;

import model.Interactable;

/**An item that can be picked off the floor, then used for various things**/
public abstract class Item extends Interactable {

//    EFFECTS: sets isCreature to false, and isItem to true
    public Item(int y, int x) {
        super(y, x);
        isCreature = false;
        isItem = true;
    }
}
