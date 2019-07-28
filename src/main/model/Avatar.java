package model;

import model.Interactables.Interactable;
import model.Interactables.items.Item;
import model.exceptions.edgeOfMapException;
import ui.GameRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Avatar extends Locatable implements Serializable {

    public static final int MAX_HP = 3;
    public static final String MAP_EDGE_MESSAGE = "Dan tries to walk of the edge of the map! The abyss gazes back into him.\n"
            + "There is no way he is going into that hell hole.";

    private HashMap<String, Item> currItems;
    private int status; //health bar 0 = dead

/* constructor
    EFFECTS: makes avatar setting it's coordinates, startingItems and status to 3
*/
    public Avatar(int startingY, int startingX, List<Item> startingItems, Map map) {
        super(map, startingY, startingX);
        initItems(startingItems);
        status = MAX_HP;
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

    public HashMap<String, Item> getCurrItems() {
        return currItems;
    }

    //    todo for tests only
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Avatar)) {
            return false;
        }
        Avatar avatar = (Avatar) o;
        return status == avatar.status
                && getY() == avatar.getY()
                && getX() == avatar.getY()
                && currItems.equals(avatar.currItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, getY(), getX(), currItems);
    }

    //    REQUIRES: MAP WALLS HAVE NO GAPS EXCEPT WIN CONDITION
//    MODIFIES: map
//    EFFECTS: handles move commands in 4 directions
    public void moveAva(String command) {
        switch (command) {
            case "n":
                moveAvaHelper(getY() - 1, getX(), "northern");
                break;
            case "s":
                moveAvaHelper(getY() + 1, getX(), "southern");
                break;
            case "e":
                moveAvaHelper(getY(), getX() + 1, "eastern");
                break;
            case "w":
                moveAvaHelper(getY(), getX() - 1, "western");
                break;
            default:
        }
    }

//    MODIFIES: this
//    EFFECTS: if currY, currX is can be moved, move ava and update ava coordinates, else print feedback text
    private void moveAvaHelper(int y, int x, String dir) {
        Map map = getMap();
        try {
            if (map.isTileWalkable(y, x)) {
                map.updateTileDisplay(y, x, Map.AVATAR);
                map.updateTileDisplay(getY(), getX(), Map.FLOOR);
                map.revealSurroundings(y, x);
                setY(y);
                setX(x);
                for (Interactable inter : map.getTileMatrix().get(y).get(x).getCurrInteractables().values()
                     ) {
                    System.out.println(inter.getName());
                }
            } else {
                GameRunner.printMovePlaceholder(dir);
            }
        } catch (edgeOfMapException e) {
            System.out.println(MAP_EDGE_MESSAGE);
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
        HashMap<String, Interactable> tileItems =  getMap().getTileMatrix()
                .get(getY()).get(getX()).getCurrInteractables();
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
        if (!currItems.containsKey(itemName)) {
            System.out.println("Dan remembers he left the " + itemName + " at home again.");
        } else if (!getMap().getTileMatrix().get(getY()).get(getX())
                .getCurrInteractables().containsKey(target) && !target.equals("Dan")) {
            System.out.println("Dan cannot find a " + target + " around him");
        } else if (!currItems.get(itemName).interact(target)) {
            System.out.println("<todo beef out text> that doesn't work");
        }
    }

    //    MODIFIES: map, this
    //    EFFECTS: drops item from Item list, setting it on current map tile
    protected void dropItem(Map map) {
//        todo might not need this
    }

}
