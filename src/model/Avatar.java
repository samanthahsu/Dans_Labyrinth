package model;

import java.util.ArrayList;

public class Avatar {
    private int status = 0; //health bar of sorts: 0=healthy, 1=dying, 2=dead todo reverse the numbers
    private int ypos;
    private int xpos; //tracks position of avatar
    private ArrayList<Item> items;

//    EFFECTS: constructs avatar setting it's coordinates
    public Avatar(int setY, int setX, ArrayList<Item> items) {
        ypos = setY;
        xpos = setX;
        this.items = items;
    }

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

    public ArrayList<Item> getItems() {
        return items;
    }

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
        if (map.isTileFloor(y, x)) {
            char avaChar = '*';
            map.updateTileDisp(y, x, avaChar);
            map.revealSurroundings(y, x);
            this.ypos = y;
            this.xpos = x;
            Interactable i = map.getInteractable(y, x);
            if (i.getName() != null) {
                System.out.println(i.getDescription());
            }
        } else {
            map.printMovePlaceholder(dir);
        }
    }

/*    REQUIRES: map is not null
*   MODIFIES: this
*    EFFECTS: if item is present in current tile, pick up item,
*      otherwise print message
*/
    public void pickUpItem(Map map) {
        Interactable inter = map.getInteractable(ypos, xpos);
        if (inter != null && inter.isItem) {
            items.add((Item) inter);
            map.removeInteractable(ypos, xpos);
            System.out.println("Picked up an item!");
        }
    }

// EFFECTS: prints current items
    public void printItems() {
        System.out.println("You are carrying:");
        for (Item i: items) {
            System.out.println(i.getName());
        }
    }

//EFFECTS: uses current item todo only one allowed
    public void useItem(String itemNm, Map map) {
        switch (itemNm) {
            case "Exo":
                for (Item i:items) {
                    if (i.getName() != null && i.getName().equals("Exo")) {
                        i.useItem(map);
                    }
                }
                break;
            default:
        }
    }
}
