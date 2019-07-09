package model;

import java.util.Random;

/*manages the map portion of this adventure*/
public class Map {

    private static final char wall = '@';
    private static final char floor = ' ';
    private static final char c = '#';

//    stores the 2D matrix of the map
//    private ArrayList<ArrayList<Character>> map;
    private char[][] map;

//    todo read from given file
// create different maps for different rooms
//    EFFECT: constructs map with height of size m, width of size n, filling it with default flooring
    public Map(int m, int n){
        map = new char[m][n];
        for (char[] tileset: map) {
            for(char tile: tileset){
                tile = floor;
            }
        }
    }

//EFFECT returns true if tile of requested index is floor, otherwise false
    public boolean isTileFloor(int m, int n){return false;}
//portions are revealed as things are explored???

//    called each time a change is made to the room

//    REQUIRES: x, y are within bounds of the matrix
//    MODIFIES: this
//    EFFECT: replaces character at index x,y with c
    public void updateTile(int x, int y, char c){

    }

//    EFFECT: prints current map
    public void print(){}

//    EFFECT: prints description of character's current location tile
    public void printTileDescription() {
    }

//todo delete this pls
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
