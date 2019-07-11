package model;

import java.util.Random;

/*manages the map portion of this adventure*/
public class Map {

    private static final char wall = '@';
    private static final char floor = ' ';
    private static final char c = '*';
    private static final char fog = '#';

    private char[][] map; //    stores the 2D matrix of the full map (not modified after)
    private char[][] mapDisplay; // stores what the user can see of the map (character, fog, walls)
    private int height;
    private int width;

    //    REQUIRES: mapString is of length h multiplied by w,
    //      startX and startY are indexes within the matrix of size h,w
    //    EFFECTS: sets height and width
    //      fills map with mapString
    //      fills mapDisplay with fog
    public Map(int h, int w, String mapString, int startY, int startX){
        height = h;
        width = w;
        initMap(mapString);
        initMapDisplay();
    //        initAvatar(startX, startY);
    }

    //    MODIFIES: this
    //    EFFECTS: store mapString into map
    private void initMap(String mapString){
        map = new char[height][width];
        int strIndex = 0;
        for (int i = 0; i < height; i++)  {
            for (int j = 0; j < width; j++) {
                map[i][j] = mapString.charAt(strIndex);
                strIndex++;
            }
        }
    }
    //    MODIFIES: this
    //    EFFECTS: fills mapDisplay with fog tiles
    private void initMapDisplay(){
        mapDisplay = new char[height][width];
        for (int i = 0; i < height; i++) {
            for(int j = 0; j< width; j++){
                mapDisplay[i][j] = fog;
            }
        }
    }

    //    MODIFIES: mapDisplay
    //    EFFECTS:places avatar at startY, startX revealing adjacent tiles
/*
    private void initAvatar(int startY, int startX) {
//      todo stub
    }
*/

    //    **GETTERS**
    public char[][] getMap() {
        return map;
    }
    public char[][] getMapDisplay() {
        return mapDisplay;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    //    EFFECTS: return true if index within bounds of map
    public boolean isIndexValid(int y, int x){
        return y >= 0 && x >= 0 && y < height && x < width;
    }
    //    REQUIRES: y and x are valid indexes in map
    //    EFFECTS: returns true if tile of requested index is floor in map, else false
    public boolean isTileFloor(int y, int x){
        return map[y][x] == ' ';
    }

    //    REQUIRES: y and x are valid indexes in map
    //    MODIFIES: this
    //    EFFECTS: replaces character at index y,x with c in mapDisplay
    public void updateTileDisp(int y, int x, char c) {
        mapDisplay[y][x] = c;
    }
    //    MODIFIES: this
    //    EFFECTS: if given index is valid, replaces character at index y,x with c in mapDisplay
    //        otherwise does nothing
    private void checkAndUpdateTileDisp(int y, int x){
        if (isIndexValid(y, x)) {
            updateTileDisp(y, x, map[y][x]);
        }
    }

    //    MODIFIES: this
    //    EFFECTS: replace any fog tiles with corresponding tiles on map orthogonally about index y,x
    //      on mapDisplay (no diagonals), if tile index not valid, do nothing
    public void revealSurroundings(int y, int x) {
        checkAndUpdateTileDisp(y, x-1);
        checkAndUpdateTileDisp(y, x+1);
        checkAndUpdateTileDisp(y-1, x);
        checkAndUpdateTileDisp(y+1, x);
    }

    //    *************PRINTING*************

    //    REQUIRES: mapDisplay is not null
    //    EFFECTS: prints mapDisplay to screen
    public void printDisplayMap(){
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(mapDisplay[i][j]);
            }
            System.out.println();
        }
    }

    //  todo delete this
    //    EFFECTS: prints silly statements when you try to move to a wall tile
    public void printMovePlaceholder(String dir, Random ran) {
        switch(ran.nextInt(5)){
            case 0:
                System.out.println("You smack hilariously against the "+dir+" wall.");
                break;
            case 1:
                System.out.println("Your toe is painfully stubbed on the "+dir+" wall.");
                break;
            case 2:
                System.out.println("You flop desperately against the "+dir+" wall.");
                break;
            case 3:
                System.out.println("You sit and ponder how your life has culminated in this moment.");
                break;
            case 4:
                System.out.println("You flop against the "+dir+" wall for fun.");
                break;
        }
    }

}
