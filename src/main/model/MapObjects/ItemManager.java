package model.MapObjects;

import model.Map;
import model.MapObjects.items.Item;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ItemManager implements Serializable {

    private Map map;
    private Avatar avatar;
    private HashMap<String, Item> currItems;

    public ItemManager(Map map, List<Item> startingItems) {
        this.map = map;
        this.avatar = map.getAva();
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
                .get(avatar.getY()).get(avatar.getX()).getCurrExaminables();
        Examinable chosenItem = tileItems.get(itemName);

        if (chosenItem != null) {
            map.removeExaminable(chosenItem, avatar.getY(), avatar.getX());
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
            Tile currentTile = map.getTileMatrix().get(avatar.getY()).get(avatar.getX());
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
            target.setIndexes(avatar.getY(), avatar.getX());
            map.addExaminable(target, avatar.getY(), avatar.getX());

            currItems.remove(itemName);
            System.out.println("Dan drops the " + itemName + " to the floor.");
        }
    }
}
