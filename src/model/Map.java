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

    private char[][] map; //    stores the 2D matrix of the full map (not modified after)
    private char[][] mapDisplay; // stores what the user can see of the map (character, fog, walls)

// create different maps for different rooms
//    EFFECT: constructs map with height of size m, width of size n, filling it with default flooring
//      todo initialize avatar to starting position on mapDisplay
    public Map(){

        map = new char[default_height][default_width];
        int i = 0;
        for (char[] tileset: map) {
            for(char tile: tileset){
                tile = default_map.charAt(i);
                        i++;
            }
        }

        mapDisplay = new char[default_height][default_width];
        for (char[] tileset: map) {
            for(char tile: tileset){
                tile = floor;
            }
        }
    }

//    **GETTER**
    public char[][] getMapDisplay() {
        return mapDisplay;
    }

    //    REQUIRES: x and y are the coordinates to be moved to
//    EFFECTS: returns true if tile of requested index is floor, otherwise false
    public boolean isTileFloor(int x, int y){return false;}
    //portions are revealed as things are explored???

//    REQUIRES: x, y are within bounds of the matrix
//    MODIFIES: this
//    EFFECTS: replaces character at index x,y with c
    public void updateDisplayTile(int x, int y, char c){

    }

//    this is for when tiles are implemented as objects not chars
//    EFFECT: prints description of character's current location tile
//    public void printTileDescription() {
//    }

//    EFFECTS: update map that user sees (called when they explore a new area)
//    public void updateDisplayMap(){}

//    REQUIRES: given x, y are currently being moved to
//    MODIFIES: this
//    EFFECTS: reveal any fog tiles immediately around given x,y on mapDisplay
    public void revealTiles(int x, int y) {
    }

//    EFFECTS: returns true if char is on winning tile
    public void isWin(int x, int y){}

//    PRINTING*************
//    EFFECTS: prints mapDisplay to screen
    public boolean printDisplayMap(){return false;}

    //todo delete this
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
