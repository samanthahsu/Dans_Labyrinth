package model.mapobjects;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.mapobjects.items.Item;
import ui.PrintObserver;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Avatar extends Locatable implements Serializable {
//    TODO SPLIT OUT ITEM HANDLER FROM AVATAR

    public static final int SANITY_NORMAL = 3;
    public static final String NAME = "Dan";

    public static final String MAP_EDGE_MESSAGE = "Dan tries to walk of the edge"
            + " of the map! The abyss gazes back into him.\n";

//    private HashMap<String, Item> currItems;
    private int sanity; //sanity = 0 means stupid controls
    private ItemManager itemManager;

/* constructor
    effects: makes avatar setting it's coordinates, startingItems and sanity*/
    public Avatar(int startingY, int startingX, List<Item> startingItems, Map map) {
        super(map, startingY, startingX);
//        initItems(startingItems);
        sanity = SANITY_NORMAL;
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
    public void addObserver(PrintObserver o) {
        super.addObserver(o);
        itemManager.addObserver(o);
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
                && yc == avatar.yc
                && xc == avatar.xc
                && itemManager.equals(avatar.itemManager);
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(sanity, yc, xc, itemManager);
//    }

    //    REQUIRES: MAP WALLS HAVE NO GAPS EXCEPT WIN CONDITION
//    MODIFIES: map
//    EFFECTS: handles move commands in 4 directions
    public void moveAva(String command) {
        switch (command) {
            case "n":
                moveAvaHelper(yc - 1, xc, "northern");
                break;
            case "s":
                moveAvaHelper(yc + 1, xc, "southern");
                break;
            case "e":
                moveAvaHelper(yc, xc + 1, "eastern");
                break;
            case "w":
                moveAvaHelper(yc, xc - 1, "western");
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
                map.updateTileDisplay(yc, xc, Map.FLOOR);
                map.revealSurroundings(y, x);
                yc = y;
                xc = x;
                printExaminables(); // make it so even if tile is walkable, display stuff there
            } else {
                notifyHitWall(dir);
            }
        } catch (EdgeOfMapException e) {
            notifyObservers(MAP_EDGE_MESSAGE);
        }
    }

    public void printExaminables() {
        for (Examinable examinable : map.getTileMatrix().get(yc).get(xc).getCurrExaminables().values()) {
            notifyObservers(examinable.toString());
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
//                .get(yc).get(xc).getCurrExaminables();
//        Examinable chosenItem = tileItems.get(itemName);
//
//        if (chosenItem != null) {
//            getMap().removeExaminable(chosenItem, yc, xc);
//            currItems.put(itemName, (Item) chosenItem);
//            notifyObservers("Dan picked up '" + itemName + "'!");
//        }
//        notifyObservers("Couldn't pick up '" + itemName + "'.");
    }


//EFFECTS: uses the item on the target
    public void useItem(String itemName, String target) {
        itemManager.useItem(itemName, target);
//        if (!currItems.containsKey(itemName)) {
//            notifyObservers("Dan remembers he left the " + itemName + " at home again.");
//        } else {
//            Tile currentTile = getMap().getTileMatrix().get(yc).get(xc);
//            if (!currentTile.getCurrExaminables().containsKey(target)
//                    && !Pattern.matches("(D|d)an", target)) {
//                notifyObservers("Dan cannot find a " + target + " around him");
//            } else if (!currItems.get(itemName).use(target)) {
//                notifyObservers("<todo beef out text> that doesn't work");
//            }
//        }
    }

        /*MODIFIES: map, this
        EFFECTS: drops item from Item list, setting it on current map tile
        and setting item indexes accordingly*/
    public void dropItem(String itemName) {
        itemManager.dropItem(itemName);
//        if (!currItems.containsKey(itemName)) {
//            notifyObservers("Dan is glad that one could not lose something one never had.");
//        } else {
//            Item target = currItems.get(itemName);
//            target.setIndexes(yc, xc);
//            getMap().addExaminable(target, yc, xc);
//
//            currItems.remove(itemName);
//            notifyObservers("Dan drops the " + itemName + " to the floor.");
//        }
    }

}
