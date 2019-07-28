package model.MapObjects.items;

import model.Map;
import model.MapObjects.Examinable;

import java.util.regex.Pattern;

/**An item that can be picked off the FLOOR, then used for various things**/
/*generally, items don't change, and have the similar properties of interactables, and the ability
 * to be used by the player. thus this need not be an abstract class, but a general one, and interactions can come
 * mainly from other classes recognizing the object name
 * they:
 * print out a description when interacted with
 * they only need x and y position for map placement, doesn't matter in equality*/
/*nevermind, scratch that i need separate objects for each*/
public abstract class Item extends Examinable {

    private boolean isHeld;

//constructor for already held objects
    public Item(Map map) {
        super(map, 0, 0);
        typeId = TYPE_ITEM;
        isHeld = true;
    }

//    constructor for items found at specific coordinates
    public Item(Map map, int y, int x) {
        super(map, y, x);
        typeId = TYPE_ITEM;
        isHeld = false;
    }

    public boolean isHeld() {
        return isHeld;
    }

    /*requires: isHeld is instantiated*/
    @Override
    public boolean examine(String ui) {
        if (isHeld) {
            return true;
        } else if (Pattern.matches("(pick( )!up|get)", ui)) {
            isHeld = true;
            getMap().getAva().getCurrItems().put(name, this);
            return true;
        }
            return false;
    }

    public abstract boolean use(String target);
}
