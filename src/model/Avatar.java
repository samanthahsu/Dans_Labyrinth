package model;

import exceptions.edgeOfMapException;
import model.Interactables.Interactable;
import model.Interactables.items.Item;
import ui.GameRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Avatar implements Serializable {
//    todo let avatar know about map, and do it's own movements???
    private Map map; // pointer to map which ava is part of
    private int status; //health bar 0 = dead
    private int ypos;
    private int xpos; //tracks position of avatar
    private ArrayList<Item> itemList; // todo has a limit of three itemList to prevent hoarding
//    todo change itemList into set

/* constructor
    EFFECTS: makes avatar setting it's coordinates, items and status to 3
*/
public Avatar(int setY, int setX, ArrayList<Item> items, Map map) {
        status = 3;
        this.map = map;
        ypos = setY;
        xpos = setX;
        this.itemList = items;
    }

//    GETTERS
    public int getStatus() {
        return status;
    }

    public int getYpos() {
        return ypos;
    }

    public int getXpos() {
        return xpos;
    }

//    todo for tests only
    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

//    effects: returns true if otherAva has same params as this todo
    public boolean equals(Avatar otherAva) {
        boolean statEq = status == otherAva.getStatus();
        boolean yEq = ypos == otherAva.getYpos();
        boolean xEq = xpos == otherAva.getXpos();
        boolean itemEq = itemListEquals(itemList, otherAva.getItemList()); // todo write this method
    return statEq && yEq && xEq && itemEq;
    }

    /*requires the two lists to be of the same size
    effects: returns true if both lists have items in the same order of the same name*/
    private boolean itemListEquals(ArrayList<Item> itemList, ArrayList<Item> otherItemList) {
        String item;
        String otherItem;
        if (itemList.size() != otherItemList.size()) {
            return false;
        }
        for (int i = 0; i < itemList.size(); i++) {
            item = itemList.get(i).getName();
            otherItem = otherItemList.get(i).getName();
            if (item == null && otherItem == null) {
            } else if (item == null || otherItem == null) {
                return false;
            } else if (!item.equals(otherItem)) {
                return false;
            }
        }
        return true;
    }

/*
//    EFFECT: remove item with given name from itemList if it exists
//    else do nothing
    public void removeItem(String name) {
        for (Interactable i: itemList) {
            if (i.getName().equals(name)) {
                itemList.remove(i);
                return;
            }
        }
    }
*/

//    REQUIRES: MAP WALLS HAVE NO GAPS EXCEPT WIN CONDITION
//    MODIFIES: map
//    EFFECTS: handles move commands in 4 directions
    public void moveAva(String command) {
        switch (command) {
            case "n":
                moveAvaHelper(ypos - 1, xpos, "northern");
                break;
            case "s":
                moveAvaHelper(ypos + 1, xpos, "southern");
                break;
            case "e":
                moveAvaHelper(ypos, xpos + 1, "eastern");
                break;
            case "w":
                moveAvaHelper(ypos, xpos - 1, "western");
                break;
            default:
        }
    }

//    MODIFIES: this
//    EFFECTS: if ypos, xpos is can be moved, move ava and update ava coordinates, else print feedback text
    private void moveAvaHelper(int y, int x, String dir) {
        try {
            if (map.isTileWalkable(y, x)) {
                char avaChar = '*';
                map.updateTileDisplay(y, x, avaChar);
                map.revealSurroundings(y, x);
                this.ypos = y;
                this.xpos = x;
    //            todo activate beings on the tile printing descriptions etc. alternatively done in gamerunner already
                } else {
                GameRunner.printMovePlaceholder(dir);
            }
        } catch (edgeOfMapException e) {
            System.out.println("Dan tries to walk of the edge of the map! The abyss gazes back into him.\n"
                    + "There is no way he is going into that hell hole.");
        }
    }

/*
  REQUIRES: map is initialized
  MODIFIES: this, map
  EFFECTS: iff item corresponding itemName is present on current tile
  remove item from tile, add item to itemList in ava and print "picked up itemName"
  otherwise do nothing and print "unable to pick up itemName"
*/
    public void pickUpItem(String itemName) {
        HashSet<Interactable> tileItems = map.getTileMatrix().get(ypos).get(xpos).getInteractables();
        Item chosenItem;
        for (Interactable i : tileItems) {
            if (i.getName().equals(itemName) && i.getTypeId() == Interactable.TYPE_ITEM) {
                chosenItem = (Item) i;
                tileItems.remove(chosenItem);
                itemList.add(chosenItem);
                System.out.println("Picked up " + itemName + "!");
                return;
            }
        }
        System.out.println("Unable to pick up " + itemName + ".");
        }


//EFFECTS: uses itemNm if corresponding item exists in itemList
//    otherwise do nothing
    public void useItem(String itemNm) {
        boolean shouldRemove = false;
        Interactable iUsed = null;
        switch (itemNm) {
            case "pizza":
                for (Item i: itemList) {
                    if (i.getName() != null && i.getName().equals("pizza")) {
                        i.interact(map);
                    }
                }
                break;
            default:
        }
        if (shouldRemove) {
            itemList.remove(iUsed);
        }
    }

    //    MODIFIES: map, this
    //    EFFECTS: drops item from Item list, setting it on current map tile
    protected void dropItem(Map map) {
//        todo might not need this
    }
}
