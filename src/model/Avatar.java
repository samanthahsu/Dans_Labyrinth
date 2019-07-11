package model;

public class Avatar {
    private int status = 0; //health bar of sorts: 0=healthy, 1=dying, 2=dead
    private int x; //tracks position of avatar
    private int y;
    private char ava_char = '*';
//    private ArrayList<Item> items = new ArrayList<>(); // for when player has weapons

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveCharN(Map map){
        moveAva(y-1, x, map);
    }
//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveCharW(Map map){
        moveAva(y+1, x, map);
    }
//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveCharE(Map map){
        moveAva(y, x+1, map);
    }
//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveCharS(Map map){
        moveAva(y, x-1, map);
    }

//    MODIFIES: map
//    EFFECTS: if y,x can be moved to, move ava, if not, print text response
    private void moveAva(int y, int x, Map map){
        if(map.isTileFloor(y, x)) {
            map.updateTileDisp(y, x, ava_char);
            map.updateTileDisp(y, x, ' ');
            map.revealSurroundings(y, x);
        }

    }

    //    EFFECTS: returns true if char is on the winning tile
    public void isWin(int x, int y){
//        todo stub
    }

}
