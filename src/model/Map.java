package model;

/*manages the map portion of this adventure*/
public class Map {
//    stores the 2D matrix of the map
//    private ArrayList<ArrayList<Character>> map;
    private char[][] map;

// create different maps for different rooms
//    EFFECT: constructs map with height of size m, width of size n
    public Map(int m, int n){
        map = new char[m][n];
    }

//portions are revealed as things are explored???

//    called each time a change is made to the room

//    REQUIRES: x, y are within bounds of the matrix
//    MODIFIES: this
//    EFFECT: replaces character at index x,y with c
    public void updateTile(int x, int y, char c){

    }

//    EFFECT: prints current map
    public void print(){}

}
