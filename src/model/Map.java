package model;

import java.util.Random;

/*manages the map portion of this adventure*/
public class Map {

    private static final char wall = '@';
    private static final char floor = ' ';
    private static final char c = '*';
    private static final char fog = '#';

    private static final String default_map =
            "@@@@@@@@@ @@@@@@@@@@@"+
            "@     @ @ @       @ @"+
            "@ @ @ @ @   @@@ @ @ @"+
            "@@@ @ @ @@@@@ @ @ @ @"+
            "@   @   @   @   @ @ @"+
            "@ @@@@@@@ @ @@@@@   @"+
            "@ @ @     @   @     @"+
            "@ @   @  @@ @ @ @@@@@"+
            "@ @@@@@@  @ @ @ @   @"+
            "@         @@@       @"+
            "@@@@@@@@@@@@@@@@@@@@@";
    private static final int default_width = 21; // hardcoded for now just to see it working
    private static final int default_height = 10;
    private static final int default_startX = 3;
    private static final int default_startY = 7;
    private static final int winX = 9;
    private static final int winY = 0;

    private char[][] map; //    stores the 2D matrix of the full map (not modified after)
    private char[][] mapDisplay; // stores what the user can see of the map (character, fog, walls)

//    EFFECT: constructs map with height of size m, width of size n, filling it with default flooring
    public Map(){
        initMap();
        initMapDisplay();
        initAvatar();
    }
//    MODIFIES: mapDisplay
//    EFFECTS:places avatar icon on map, revealing adjacent tiles
    private void initAvatar() {
//      todo stub
    }

//    MODIFIES: this
//    EFFECT: fills mapDisplay with fog tiles
    private void initMapDisplay(){
        mapDisplay = new char[default_height][default_width];
        for (char[] tileset: map) {
            for(char tile: tileset){
                tile = fog;
            }
        }
    }

//    MODIFIES this
//    EFFECT store default_map into map
    private void initMap(){
        map = new char[default_height][default_width];
        int i = 0;
        for (char[] tileset: map) {
            for(char tile: tileset){
                tile = default_map.charAt(i);
                i++;
            }
        }
    }

//    **GETTER**

    public char[][] getMapDisplay() {
        return mapDisplay;
    }

//    REQUIRES: x and y are the coordinates to be moved to
//    EFFECTS: returns true if tile of requested index is floor in map, otherwise false
    public boolean isTileFloor(int x, int y){
//        todo stub
        return false;}

//    REQUIRES: x, y are within bounds of the matrix
//    MODIFIES: this
//    EFFECTS: replaces character at index x,y with c in mapDisplay
    public void updateDisplayTile(int x, int y, char c) {
//        todo stub
    }

//    REQUIRES: given x, y are currently being moved to
//    MODIFIES: this
//    EFFECTS: reveal any fog tiles immediately around given x,y on mapDisplay
    public void revealTiles(int x, int y) {
//        todo stub
    }

//    PRINTING*************
//    EFFECTS: prints mapDisplay to screen
    public boolean printDisplayMap(){
//        todo stub
        return false;}

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
