package model;

import exceptions.edgeOfMapException;
import model.Interactables.Interactable;
import model.Interactables.items.Item;
import ui.GameRunner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Avatar implements Serializable {
//    todo let avatar know about map, and do it's own movements???
    private Map map; // pointer to map which ava is part of
    private int status; //health bar 0 = dead
    private int currY;
    private int currX; //tracks position of avatar
    private HashMap<String, Item> currItems;

/* constructor
    EFFECTS: makes avatar setting it's coordinates, startingItems and status to 3
*/
public Avatar(int startY, int startX, List<Item> startingItems, Map map) {
        status = 3;
        this.map = map;
        currY = startY;
        currX = startX;
        initItems(startingItems);
    }

    /* modifies: this
    effects: initializes items from list into hashmap*/
    private void initItems(List<Item> items) {
        HashMap<String, Item> hashMap = new HashMap<>();
        for (Item i : items
             ) {
            hashMap.put(i.getName(), i);
        }
        currItems = hashMap;
    }

    //    GETTERS
    public int getStatus() {
        return status;
    }

    public int getCurrY() {
        return currY;
    }

    public int getCurrX() {
        return currX;
    }

    public HashMap<String, Item> getCurrItems() {
        return currItems;
    }

    //    todo for tests only
    public void setStatus(int status) {
        this.status = status;
    }


//    effects: returns true if otherAva has same params as this todo
    public boolean equals(Avatar otherAva) {
        boolean statEq = status == otherAva.getStatus();
        boolean yEq = currY == otherAva.getCurrY();
        boolean xEq = currX == otherAva.getCurrX();
        boolean itemEq = currItems.equals(otherAva.getCurrItems());
    return statEq && yEq && xEq && itemEq;
    }

/*
    */
/*requires the two lists to be of the same size
    effects: returns true if both lists have items in the same order of the same name*//*

    private boolean itemListEquals(HashMap<String, Item> currItems, List<Item> otherItemList) {
        String item;
        String otherItem;
        if (currItems.size() != otherItemList.size()) {
            return false;
        }
        for (int i = 0; i < currItems.size(); i++) {
            item = currItems.get(i).getName();
            otherItem = otherItemList.get(i).getName();
            if (item == null && otherItem == null) {
//                continue on
            } else if (item == null || otherItem == null) {
                return false;
            } else if (!item.equals(otherItem)) {
                return false;
            }
        }
        return true;
    }
*/

//    REQUIRES: MAP WALLS HAVE NO GAPS EXCEPT WIN CONDITION
//    MODIFIES: map
//    EFFECTS: handles move commands in 4 directions
    public void moveAva(String command) {
        switch (command) {
            case "n":
                moveAvaHelper(currY - 1, currX, "northern");
                break;
            case "s":
                moveAvaHelper(currY + 1, currX, "southern");
                break;
            case "e":
                moveAvaHelper(currY, currX + 1, "eastern");
                break;
            case "w":
                moveAvaHelper(currY, currX - 1, "western");
                break;
            default:
        }
    }

//    MODIFIES: this
//    EFFECTS: if currY, currX is can be moved, move ava and update ava coordinates, else print feedback text
    private void moveAvaHelper(int y, int x, String dir) {
        try {
            if (map.isTileWalkable(y, x)) {
                map.updateTileDisplay(y, x, Map.c);
                map.updateTileDisplay(currY, currX, Map.floor);
                map.revealSurroundings(y, x);
                this.currY = y;
                this.currX = x;
                for (Interactable inter : map.getTileMatrix().get(y).get(x).getInteractables()
                     ) {
                    System.out.println(inter.getName());
                }
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
  remove item from tile, add item to currItems in ava and print "picked up itemName"
  otherwise do nothing and print "unable to pick up itemName"
*/
    public void pickUpItem(String itemName) {
        ArrayList<Interactable> tileItems = map.getTileMatrix().get(currY).get(currX).getInteractables();
        Item chosenItem;
        for (Interactable i : tileItems) {
            if (i.getName().equals(itemName) && i.getTypeId() == Interactable.TYPE_ITEM) {
                chosenItem = (Item) i;
                tileItems.remove(chosenItem);
                currItems.put(itemName, chosenItem);
                System.out.println("Picked up " + itemName + "!");
                return;
            }
        }
        System.out.println("Unable to pick up " + itemName + ".");
        }


//EFFECTS: uses itemName if corresponding item exists in currItems
//    otherwise do nothing
    public void useItem(String itemName, String target) {
        boolean shouldRemove = false;
        Interactable iUsed = null;
        switch (itemName) {
            case "pizza":
                switch (target) {
                    case "Dan":
                        Item i = currItems.get(itemName);
                        if (i != null) {
                            i.interact(map); //todo, figure out how this is gonna work
                        }
                    break;
                }
                break;
            case "rusty key":
                break;
            default:
        }
        if (shouldRemove) {
            currItems.remove(iUsed);
        }
    }

    //    MODIFIES: map, this
    //    EFFECTS: drops item from Item list, setting it on current map tile
    protected void dropItem(Map map) {
//        todo might not need this
    }
}
