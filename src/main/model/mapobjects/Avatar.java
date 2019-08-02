package model.mapobjects;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.mapobjects.items.Item;
import ui.GameRunner;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Avatar extends Locatable implements Serializable {
//    TODO SPLIT OUT ITEM HANDLER FROM AVATAR

    public static final int NORMAL = 3;
    public static final int POISONED = 1;
    public static String NAME;

    public static final String MAP_EDGE_MESSAGE = "Dan tries to walk of the edge"
            + " of the map! The abyss gazes back into him.\n";

//    private HashMap<String, Item> currItems;
    private int sanity; //sanity = 0 means stupid controls
    private ItemManager itemManager;

/* constructor
    EFFECTS: makes avatar setting it's coordinates, startingItems and sanity*/
    public Avatar(int startingY, int startingX, List<Item> startingItems, Map map) {
        super(map, startingY, startingX);
//        initItems(startingItems);
        sanity = NORMAL;
        NAME = "Dan";
        itemManager = new ItemManager(map, startingItems);
    }

//    /* modifies: this
//    effects: initializes items from list into hashmap*/
//    private void initItems(List<Item> items) {
//        HashMap<String, Item> hashMap = new HashMap<>();
//        for (Item i : items) {
//            i.setMap(getMap());
//            hashMap.put(i.getName(), i);
//        }
//        currItems = hashMap;
//    }

    //    GETTERS
    public int getSanity() {
        return sanity;
    }

    public HashMap<String, Item> getCurrItems() {
//        return currItems;
        return itemManager.getCurrItems();
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    //    todo for tests only
    public void setSanity(int sanity) {
        this.sanity = sanity;
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
        return sanity == avatar.sanity
                && getYc() == avatar.getYc()
                && getXc() == avatar.getXc()
                && itemManager.equals(avatar.itemManager);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sanity, getYc(), getXc(), itemManager);
    }

    //    REQUIRES: MAP WALLS HAVE NO GAPS EXCEPT WIN CONDITION
//    MODIFIES: map
//    EFFECTS: handles move commands in 4 directions
    public void moveAva(String command) {
        switch (command) {
            case "n":
                moveAvaHelper(getYc() - 1, getXc(), "northern");
                break;
            case "s":
                moveAvaHelper(getYc() + 1, getXc(), "southern");
                break;
            case "e":
                moveAvaHelper(getYc(), getXc() + 1, "eastern");
                break;
            case "w":
                moveAvaHelper(getYc(), getXc() - 1, "western");
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
                map.updateTileDisplay(getYc(), getXc(), Map.FLOOR);
                map.revealSurroundings(y, x);
                setYc(y);
                setXc(x);
                for (Examinable inter : map.getTileMatrix().get(y).get(x).getCurrExaminables().values()
                     ) {
                    notifyObservers(inter.getName());
                }
            } else {
                GameRunner.printMovePlaceholder(dir);
            }
        } catch (EdgeOfMapException e) {
            notifyObservers(MAP_EDGE_MESSAGE);
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
        itemManager.pickUpItem(itemName);
//        HashMap<String, Examinable> tileItems =  getMap().getTileMatrix()
//                .get(getYc()).get(getXc()).getCurrExaminables();
//        Examinable chosenItem = tileItems.get(itemName);
//
//        if (chosenItem != null) {
//            getMap().removeExaminable(chosenItem, getYc(), getXc());
//            currItems.put(itemName, (Item) chosenItem);
//            System.out.println("Dan picked up '" + itemName + "'!");
//        }
//        System.out.println("Couldn't pick up '" + itemName + "'.");
    }


//EFFECTS: uses the item on the target
    public void useItem(String itemName, String target) {
        itemManager.useItem(itemName, target);
//        if (!currItems.containsKey(itemName)) {
//            System.out.println("Dan remembers he left the " + itemName + " at home again.");
//        } else {
//            Tile currentTile = getMap().getTileMatrix().get(getYc()).get(getXc());
//            if (!currentTile.getCurrExaminables().containsKey(target)
//                    && !Pattern.matches("(D|d)an", target)) {
//                System.out.println("Dan cannot find a " + target + " around him");
//            } else if (!currItems.get(itemName).use(target)) {
//                System.out.println("<todo beef out text> that doesn't work");
//            }
//        }
    }

        /*MODIFIES: map, this
        EFFECTS: drops item from Item list, setting it on current map tile
        and setting item indexes accordingly*/
    public void dropItem(String itemName) {
        itemManager.dropItem(itemName);
//        if (!currItems.containsKey(itemName)) {
//            System.out.println("Dan is glad that one could not lose something one never had.");
//        } else {
//            Item target = currItems.get(itemName);
//            target.setIndexes(getYc(), getXc());
//            getMap().addExaminable(target, getYc(), getXc());
//
//            currItems.remove(itemName);
//            System.out.println("Dan drops the " + itemName + " to the floor.");
//        }
    }

}
