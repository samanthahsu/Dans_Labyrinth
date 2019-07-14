package model;

public class Avatar {
    private static char ava_char = '*';
    private int status = 0; //health bar of sorts: 0=healthy, 1=dying, 2=dead
    private int y;
    private int x; //tracks position of avatar
//    private ArrayList<Item> items = new ArrayList<>(); // for when player has weapons

//    EFFECTS: constructs obj setting it's coordinates
    public Avatar(int setY, int setX){
        y=setY;
        x=setX;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    //    MODIFIES: map
//    EFFECTS: if y,x can be moved to, move ava, if not, print text response
    public void moveAva(String command, Map map){
        switch (command){
            case "n":
                moveAvaHelper(y-1, x, map);
                break;
            case "s":
                moveAvaHelper(y+1, x, map);
                break;
            case "e":
                moveAvaHelper(y, x+1, map);
                break;
            case "w":
                moveAvaHelper(y, x-1, map);
                break;
        }
    }
//    MODIFIES: this
//    EFFECTS: if y,x is can be moved, move ava and update ava coordinates, else print feedback text
    private void moveAvaHelper(int y, int x, Map map) {
        if (map.isTileFloor(y, x)) {
            map.updateTileDisp(y, x, ava_char);
            map.updateTileDisp(y, x, ' ');
            map.revealSurroundings(y, x);
            this.y = y;
            this.x = x;
        } else {
//            todo placeholder text
            System.out.println("ouch");
//            map.printMovePlaceholder();
        }
    }

}
