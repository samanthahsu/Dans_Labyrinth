package model;

import java.util.ArrayList;

public class Avatar {
    private static char ava_char = '*';
    private int status = 0; //health bar of sorts: 0=healthy, 1=dying, 2=dead
    private int y;
    private int x; //tracks position of avatar
    private ArrayList<Item> items = new ArrayList<>();

//    EFFECTS: constructs avatar setting it's coordinates
    public Avatar(int setY, int setX){
        y=setY;
        x=setX;
    }

    public int getStatus() {
        return status;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    MODIFIES: map
//    EFFECTS: handles move commands in 4 directions
    public void moveAva(String command, Map map){
        switch (command){
            case "n":
                moveAvaHelper(y-1, x, map, "northern");
                break;
            case "s":
                moveAvaHelper(y+1, x, map, "southern");
                break;
            case "e":
                moveAvaHelper(y, x+1, map, "eastern");
                break;
            case "w":
                moveAvaHelper(y, x-1, map, "western");
                break;
        }
    }

//    MODIFIES: this
//    EFFECTS: if y,x is can be moved, move ava and update ava coordinates, else print feedback text
    private void moveAvaHelper(int y, int x, Map map, String dir) {
        if (map.isTileFloor(y, x)) {
            map.updateTileDisp(y, x, ava_char);
            map.revealSurroundings(y, x);
            this.y = y;
            this.x = x;
            map.checkCreature(y, x);
        } else {
              map.printMovePlaceholder(dir);
        }
    }

}
