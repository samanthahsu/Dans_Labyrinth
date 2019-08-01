package model.mapobjects;

import model.Map;
import model.mapobjects.items.Item;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ItemManager implements Serializable {

    private Map map;
    private HashMap<String, Item> currItems;
    private Avatar avatar;

    public ItemManager(Map map, List<Item> startingItems) {
        this.map = map;
        initItems(startingItems);
    }

    /* modifies: this
    effects: initializes items from list into hashmap*/
    private void initItems(List<Item> items) {
        HashMap<String, Item> hashMap = new HashMap<>();
        for (Item i : items) {
            i.setMap(map);
            hashMap.put(i.getName(), i);
        }
        currItems = hashMap;
    }

    public void setAvatar() {
        avatar = map.getAva();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemManager)) {
            return false;
        }
        ItemManager that = (ItemManager) o;
        return Objects.equals(currItems, that.currItems);
    }

    @Override
    public int hashCode() {

        return Objects.hash(currItems);
    }

    public HashMap<String, Item> getCurrItems() {
        return currItems;
    }

    /*
  REQUIRES: map is initialized
  MODIFIES: this, map
  EFFECTS: iff item corresponding itemName is present on current tile
  remove item from tile, add item to currItems in ava and print "picked up itemName"
  otherwise do nothing and print "unable to pick up itemName"
*/
    public void pickUpItem(String itemName) {
        HashMap<String, Examinable> tileItems = map.getTileMatrix()
                .get(avatar.getYc()).get(avatar.getXc()).getCurrExaminables();
        Examinable chosenItem = tileItems.get(itemName);

        if (chosenItem != null) {
            map.removeExaminable(chosenItem, avatar.getYc(), avatar.getXc());
            currItems.put(itemName, (Item) chosenItem);
            System.out.println("Dan picked up '" + itemName + "'!");
        }
        System.out.println("Couldn't pick up '" + itemName + "'.");
    }


    //EFFECTS: uses the item on the target
    public void useItem(String itemName, String target) {
        if (!currItems.containsKey(itemName)) {
            System.out.println("Dan remembers he left the " + itemName + " at home again.");
        } else {
            Tile currentTile = map.getTileMatrix().get(avatar.getYc()).get(avatar.getXc());
            if (!currentTile.getCurrExaminables().containsKey(target)
                    && !Pattern.matches("(D|d)an", target)) {
                System.out.println("Dan cannot find a " + target + " around him");
            } else if (!currItems.get(itemName).use(target)) {
                System.out.println("<todo beef out text> that doesn't work");
            }
        }
    }

    /*MODIFIES: map, this
    EFFECTS: drops item from Item list, setting it on current map tile
    and setting item indexes accordingly*/
    public void dropItem(String itemName) {
        if (!currItems.containsKey(itemName)) {
            System.out.println("Dan is glad that one could not lose something one never had.");
        } else {
            Item target = currItems.get(itemName);
            target.setIndexes(avatar.getYc(), avatar.getXc());
            map.addExaminable(target, avatar.getYc(), avatar.getXc());

            currItems.remove(itemName);
            System.out.println("Dan drops the " + itemName + " to the floor.");
        }
    }
}
