package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/*manages the map portion of this adventure*/
public class Map implements Serializable {

//    default characters representing each display element
    private static final char wall = '@';
    private static final char floor = ' ';
    private static final char c = '*';
    private static final char fog = '#';

    // stores what the user can see of the map (character, fog, walls) for easy printing
    private char[][] mapDisplay;
    private int height;
    private int width;
    private int winY;
    private int winX;
    private Avatar ava;
//    holds interactables and other tile information
    private ArrayList<ArrayList<Tile>> tileMatrix; //todo make separate class for tileMatrix?? >> handle browsing etc. in more appropriate place?


//    EFFECTS: sets height, width, win coordinates, fills in mapDisplay and tileMatrix from tileList
//          initializes ava at given coordinates
    public Map(int height, int width, int winY, int winX, int avaY, int avaX,
               ArrayList<Interactable> avaItems, ArrayList<Tile> tileList) {
        this.height = height;
        this.width = width;
        this.winY = winY;
        this.winX = winX;
        initTileMatrix(tileList);
        initMapDisplay(tileList);

        initAvatar(avaY, avaX, avaItems);
    }

//    requires tileList to be in order and of size h*w, h and w are init
//    modifies this
//    takes tileList, and formats it into a matrix for easier access
    private void initTileMatrix(ArrayList<Tile> tileList) {
        tileMatrix = new ArrayList<>(height);
        ArrayList<Tile> tileRow;
        int i = 0;
        for (int m = 0; m < height; m++) {
                tileRow = new ArrayList<>(width);
                for (int n = 0; n < width; n++) {
                    tileRow.add(tileList.get(i));
                    i++;
            }
            tileMatrix.add(tileRow);
        }
    }

    //    REQUIRES: given string is fitting of given height and width
    //    MODIFIES: this
    //    EFFECTS: fills mapDisplay according displayChar in tileList
    private void initMapDisplay(ArrayList<Tile> tileList) {
        mapDisplay = new char[height][width];
        int i = 0;
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
                if (tileList.get(i).isRevealed) {
                    mapDisplay[m][n] = tileList.get(i).displayChar;
                } else {
                    mapDisplay[m][n] = fog;
                }
                i++;
            }
        }
    }

    //    MODIFIES: mapDisplay
    //    EFFECTS: places avatar at ypos, xpos revealing adjacent tiles
    private void initAvatar(int startY, int startX, ArrayList<Interactable> items) {
        ava = new Avatar(startY, startX, items);
        updateTileDisp(startY, startX, c);
        revealSurroundings(startY, startX);
    }

//    getters...
    public char[][] getMapDisplay() {
        return mapDisplay;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getWinY() {
        return winY;
    }

    public int getWinX() {
        return winX;
    }

    public Avatar getAva() {
        return ava;
    }

    public ArrayList<ArrayList<Tile>> getTileMatrix() {
        return tileMatrix;
    }

    //    EFFECTS: returns true if maps are equal
    public boolean equals(Map otherMap) {
        boolean mapDisplayEq = mapDisplay == otherMap.getMapDisplay();
        boolean heightEq = height == otherMap.getHeight();
        boolean widthEq = width == otherMap.getWidth();
        boolean winYEq = winY == otherMap.getWinY();
        boolean winXEq = winX == otherMap.getWinX();
        boolean avaEq = ava.equals(otherMap.getAva());
        boolean tileMatrixEq = tileMatrixEquals(tileMatrix, otherMap.getTileMatrix(), height, width);

        return mapDisplayEq && heightEq && widthEq && winYEq && winXEq && avaEq && tileMatrixEq;
    }

//    requires: a and b are of same size (indicated by height and width)
//    effect: returns true if a and b contain the tiles in the same order
    private boolean tileMatrixEquals(ArrayList<ArrayList<Tile>> a, ArrayList<ArrayList<Tile>> b, int height, int width) {
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
                if (!a.get(m).get(n).equals(b.get(m).get(n))) {
                    return false;
                }
            }
        }
        return true;
    }

/*STUFF DEALING WITH INTERACTABLES THAT MAY OR MAY NOT BE NECESSARY ANYMORE
    //    REQUIRES: interactables is not null
//    EFFECTS: if creature exists at given index, returns Interactable
    public Interactable getInteractable(int y, int x) {
        return interactables.get(y).get(x);
    }

    public void removeInteractable(int y, int x) {
        interactables.get(y).set(x, new AbsolutelyNothing());
    }

    public ArrayList<ArrayList<Interactable>> getInteractables() {
        return interactables;
    }
*/

    //    EFFECTS: return true if index within bounds of the map
    public boolean isIndexValid(int y, int x) {
        return y >= 0 && x >= 0 && y < height && x < width;
    }

    //    REQUIRES: the position ypos, xpos are a valid position on the map
    //    EFFECTS: returns true if tile of requested index is floor in map, else false
    public boolean isTileFloor(int y, int x) {
        char c = tileMatrix.get(y).get(x).displayChar;
        return c == ' ';
    }

    //    REQUIRES: ypos and xpos are valid indexes in map
    //    MODIFIES: this
    //    EFFECTS: replaces character at index ypos,xpos with c in mapDisplay
    public void updateTileDisp(int y, int x, char c) { //todo move this method to Tile class
        mapDisplay[y][x] = c;
    }

    //    MODIFIES: this
    //    EFFECTS: if given index is valid, replaces character at index ypos,xpos with c in mapDisplay
    //        otherwise does nothing
    private void checkAndUpdateTileDisp(int y, int x) {
        if (isIndexValid(y, x)) {
            updateTileDisp(y, x, map[y][x]);
//            updateTileDisp(y, x, map[y][x]);
        }
    }

    //    MODIFIES: this
    //    EFFECTS: replace any fog tiles with corresponding tiles on map orthogonally about index ypos,xpos
    //      on mapDisplay (no diagonals), if tile index not valid, do nothing
    public void revealSurroundings(int y, int x) { //todo make this update tile as well...
        checkAndUpdateTileDisp(y, x - 1);
        checkAndUpdateTileDisp(y, x + 1);
        checkAndUpdateTileDisp(y - 1, x);
        checkAndUpdateTileDisp(y + 1, x);
    }

    //    EFFECTS: returns true if ava is on the winning tile
    public boolean isWin() {
        return ava.getYpos() == winY && ava.getXpos() == winX;
    }

    //    EFFECTS: executes interactables actions
    public void nextState() {
//        System.out.println("rumbles in the distance");
        for (ArrayList<Interactable> is: interactables) {
            for (Interactable i: is) {
                i.interact(this); // todo add bg checking for each creature and use diff method here
            }
        }
    }

    //    *************PRINTING*************

    //    REQUIRES: tileMatrix is not null
    //    EFFECTS: prints mapDisplay to screen todo make this fetch char from tilematrix >> elim mapDisplay???
    public void printDisplayMap() {
        char displayTile;
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
                displayTile = tileMatrix.get(m).get(n).getDisplayChar();
                System.out.print(displayTile);
            }
            System.out.println();
        }
/*
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(mapDisplay[i][j]);
            }
            System.out.println();
        }
*/
    }

    //    EFFECTS: prints silly statements when you try to move to a wall tile
    public void printMovePlaceholder(String dir) {
        Random ran = new Random();
        switch (ran.nextInt(5)) {
            case 0:
                System.out.println("You smack hilariously against the " + dir + " wall.");
                break;
            case 1:
                System.out.println("Your toe is painfully stubbed on the " + dir + " wall.");
                break;
            case 2:
                System.out.println("You flop desperately against the " + dir + " wall.");
                break;
            case 3:
                System.out.println("You sit and ponder how your life has culminated in this moment.");
                break;
            case 4:
                System.out.println("You flop against the " + dir + " wall for fun.");
                break;
            default:
        }
    }
}