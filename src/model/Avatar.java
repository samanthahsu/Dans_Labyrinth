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
        int new_x = x;
        int new_y = y-1;
        if(map.isTileFloor(new_x, new_y)) {
            map.updateDisplayTile(new_x, new_y, ava_char);
            map.updateDisplayTile(x, y, ' ');
            map.revealTiles(new_x, new_y);
        }
    }

    //    EFFECTS: returns true if char is on the winning tile
    public void isWin(int x, int y){
//        todo stub
    }

}
