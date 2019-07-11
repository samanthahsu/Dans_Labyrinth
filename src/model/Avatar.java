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

//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveAvaN(Map map){
        moveAva(y-1, x, map);
    }
//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveAvaW(Map map){
        moveAva(y+1, x, map);
    }
//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveAvaE(Map map){
        moveAva(y, x+1, map);
    }
//    MODIFIES: map
//    EFFECT: checks if move direction is valid, move character to designated position and reveal tiles in scope
    public void moveAvaS(Map map){
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
