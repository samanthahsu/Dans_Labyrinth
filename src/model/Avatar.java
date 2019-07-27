package model;

import exceptions.edgeOfMapException;
import model.Interactables.Interactable;
import model.Interactables.items.Item;
import ui.GameRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avatar)) return false;
        Avatar avatar = (Avatar) o;
        return status == avatar.status &&
                currY == avatar.currY &&
                currX == avatar.currX &&
                currItems.equals(avatar.currItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, currY, currX, currItems);
    }

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
                map.updateTileDisplay(y, x, Map.AVATAR);
                map.updateTileDisplay(currY, currX, Map.FLOOR);
                map.revealSurroundings(y, x);
                this.currY = y;
                this.currX = x;
                for (Interactable inter : map.getTileMatrix().get(y).get(x).getCurrInteractables().values()
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
        HashMap<String, Interactable> tileItems =  map.getTileMatrix().get(currY).get(currX).getCurrInteractables();
        Interactable chosenItem = tileItems.get(itemName);

        if (chosenItem != null) {
            tileItems.remove(itemName);
            currItems.put(itemName, (Item) chosenItem);
            System.out.println("Dan picked up '" + itemName + "'!");
        }
        System.out.println("Couldn't pick up '" + itemName + "'.");
    }


//EFFECTS: uses the item called itemName on target
    public void useItem(String itemName, String target) {
//        boolean shouldRemove = false;
//        Interactable iUsed = null;
        if (!currItems.containsKey(itemName)) {
            System.out.println("Dan remembers he left the " + itemName + " at home again.");
        } else if (!map.getTileMatrix().get(currY).get(currX)
                .getCurrInteractables().containsKey(target) && !target.equals("Dan")) {
            System.out.println("Dan cannot find a " + target + " around him");
        } else if (!currItems.get(itemName).interact(target)) {
            System.out.println("<todo beef out text> that doesn't work");
        }
/*

        switch (itemName) {
            case "pizza":
                switch (target) {
                    case "Dan":
                        Item i = currItems.get(itemName);
                        if (i != null) {
                            i.interact(target); //todo, figure out how this is gonna work
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
*/
    }

    //    MODIFIES: map, this
    //    EFFECTS: drops item from Item list, setting it on current map tile
    protected void dropItem(Map map) {
//        todo might not need this
    }
}
