package model;

import ui.GameRunner;

import java.io.Serializable;
import java.util.ArrayList;

public class Avatar implements Serializable {
//    todo let avatar know about map, and do it's own movements???
    private int status; //health bar 0 = dead
    private int ypos;
    private int xpos; //tracks position of avatar
    private ArrayList<Interactable> itemList; // todo has a limit of three itemList to prevent hoarding
//    todo change itemList into set

/* constructor
    EFFECTS: makes avatar setting it's coordinates, items and status to 3
*/
    Avatar(int setY, int setX, ArrayList<Interactable> items) {
        status = 3;
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

    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Interactable> getItemList() {
        return itemList;
    }

//    effects: returns true if otherAva has same params as this todo
    public boolean equals(Avatar otherAva) {
        boolean statEq = status == otherAva.getStatus();
        boolean yEq = ypos == otherAva.getYpos();
        boolean xEq = xpos == otherAva.getXpos();
        boolean itemEq = itemList.equals(otherAva.getItemList()); // todo write this method
    return statEq && yEq && xEq && itemEq;
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
    public void moveAva(String command, Map map) {
        switch (command) {
            case "n":
                moveAvaHelper(ypos - 1, xpos, map, "northern");
                break;
            case "s":
                moveAvaHelper(ypos + 1, xpos, map, "southern");
                break;
            case "e":
                moveAvaHelper(ypos, xpos + 1, map, "eastern");
                break;
            case "w":
                moveAvaHelper(ypos, xpos - 1, map, "western");
                break;
            default:
        }
    }

//    MODIFIES: this
//    EFFECTS: if ypos,xpos is can be moved, move ava and update ava coordinates, else print feedback text
    private void moveAvaHelper(int y, int x, Map map, String dir) {
        if (map.isTileWalkable(y, x)) {
            char avaChar = '*';
            map.updateTileDisp(y, x, avaChar);
            map.revealSurroundings(y, x);
            this.ypos = y;
            this.xpos = x;
//            todo activate beings on the tile printing descriptions etc. alternatively done in gamerunner already
/*
            Interactable i = map.getInteractable(y, x);
            if (i.getName() != null) {
                System.out.println(i.getDescription());
*/
            }
        } else {
            GameRunner.printMovePlaceholder(dir);
        }
    }

//  REQUIRES: map is not null
//  MODIFIES: this
//  EFFECTS: if item is present in current tile, pick up item,
//      otherwise print message
    public void pickUpItem(Map map) {
        Interactable interactable = map.getInteractable(ypos, xpos);
        if (interactable != null && interactable.isItem) {
            itemList.add(interactable);
            map.removeInteractable(ypos, xpos);
            System.out.println("Picked up an item!");
        } else {
            System.out.println("Nothing to pick up!");
        }
    }

// EFFECTS: prints out current itemList
    public void printItems() {
        System.out.println("You are carrying:");
        for (Interactable i: itemList) {
            System.out.println(i.getName());
        }
    }

//EFFECTS: uses itemNm if corresponding item exists in itemList
//    otherwise do nothing
    public void useItem(String itemNm, Map map) {
        boolean shouldRemove = false;
        Interactable iUsed = null;
        switch (itemNm) {
            case "apple":
                for (Interactable i: itemList) {
                    if (i.getName() != null && i.getName().equals("apple")) {
                        shouldRemove = i.interact(map);
                        iUsed = i;
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
//        todo stub
    }
}
