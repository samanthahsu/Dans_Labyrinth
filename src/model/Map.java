package model;

import model.creatures.Creature;
import model.items.Item;

import java.io.Serializable;
import java.util.ArrayList;

/*manages the map portion of this adventure*/
public class Map implements Serializable { //todo add assertion stuff

//    default characters representing each display element
    private static final char wall = '@';
    private static final char floor = ' ';
    private static final char c = '*';
    private static final char fog = '#';

    // stores what the user can see of the map (character, fog, walls) for easy printing
    private int height;
    private int width;
    private int winY;
    private int winX;
    private Avatar ava;
//    holds interactables and other tile information
    private ArrayList<ArrayList<Tile>> tileMatrix;


/*constructor
requires: height*width == tileList.size() // todo throw mismatched map size exception
EFFECTS: sets height and width of map, win coordinates, and tileMatrix from tileList.
initializes avatar at given coordinates with its items
*/
    public Map(int height, int width, int winY, int winX, int avaY, int avaX,
               ArrayList<Item> avaItems, ArrayList<Tile> tileList) {
        this.height = height;
        this.width = width;
        this.winY = winY;
        this.winX = winX;
        initTileMatrix(tileList);
        initAvatar(avaY, avaX, avaItems);
    }

/*  initializes tileMatrix
    requires: tileList to be in order and of size h*w, h and w are init
    modifies: this // todo throw mismatched map size exception
    effects: takes tileList, and formats it into a matrix for easier access
*/
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

/* initializes avatar
        MODIFIES: this, ava
        EFFECTS: construct new Avatar with given coordinates and items
        places avatar character at ypos, xpos in tileMatrix, revealing adjacent tiles
*/
    private void initAvatar(int startY, int startX, ArrayList<Item> items) {
        ava = new Avatar(startY, startX, items, this);
        updateTileDisp(startY, startX, c);
        revealSurroundings(startY, startX);
    }

    /*
    requires: y, x are valid coordinates on the map // todo throw edge of map exception
    modifies: this, tileMatrix
    effects: updates display char at y, x to c in tileMatrix
    */
    public void updateTileDisp(int y, int x, char c) {
        tileMatrix.get(y).get(x).setDisplayChar(c);
    }

    //    getters...
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

/*
        EFFECTS: returns true if this and otherMap are equal to each other
*/
    public boolean equals(Map otherMap) {
        boolean heightEq = height == otherMap.getHeight();
        boolean widthEq = width == otherMap.getWidth();
        boolean winYEq = winY == otherMap.getWinY();
        boolean winXEq = winX == otherMap.getWinX();
        boolean avaEq = ava.equals(otherMap.getAva());
        boolean tileMatrixEq = tileMatrixEquals(tileMatrix, otherMap.getTileMatrix(), height, width);

        return heightEq && widthEq && winYEq && winXEq && avaEq && tileMatrixEq;
    }

/*
    requires: a and b are of same size (indicated by height and width) // todo throw mismatched map size exception
    effects: returns true if a and b contain equal tiles in the same order,
    else returns false
*/
    public boolean tileMatrixEquals(ArrayList<ArrayList<Tile>> a, ArrayList<ArrayList<Tile>> b, int height, int width) {
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

    //    EFFECTS: return true if index within bounds of the map // TODO MAKE THIS THROW EDGE OF MAP EXCEPTION
    public boolean isIndexValid(int y, int x) {
        return y >= 0 && x >= 0 && y < height && x < width;
    }

/*
        REQUIRES: the position y, x is a valid position on the map // todo throw edge of map exc
        EFFECTS: returns true if tile of requested index is walkable, else false
*/
    public boolean isTileWalkable(int y, int x) {
        return tileMatrix.get(y).get(x).isWalkable();
    }

/*
        MODIFIES: this
        EFFECTS: iff given index is valid, reveal actual tile char at given index
        todo throw edge of map exception, alt fail quietly since is expected to happen
*/
    private void checkAndRevealTileDisp(int y, int x) {
        if (isIndexValid(y, x)) {
            tileMatrix.get(y).get(x).revealTile();
        }
    }

/*
        MODIFIES: this
        EFFECTS: if tile index is valid, reveal the 4 (or less) tiles orthogonally around y, x (no diagonals)
        else do nothing? todo throw exc?
*/
    public void revealSurroundings(int y, int x) {
        checkAndRevealTileDisp(y, x - 1);
        checkAndRevealTileDisp(y, x + 1);
        checkAndRevealTileDisp(y - 1, x);
        checkAndRevealTileDisp(y + 1, x);
    }

/*
        EFFECTS: returns true if ava is on the winning tile
*/
    public boolean isWin() {
        return ava.getYpos() == winY && ava.getXpos() == winX;
    }

/*
        EFFECTS: executes each creature on the map's default actions
        different for each type of creature including:
         sound scope: how far away from ava for ava to get message feedback
        about disturbance
        movement: how far and in what pattern each creature moves (if they try to avoid or to approach ava)
*/
    public void nextState() {
//        System.out.println("rumbles in the distance");
        for (ArrayList<Tile> tileArrayList: tileMatrix) {
            for (Tile tile : tileArrayList) {
                for (Interactable i : tile.getInteractables()) {
                    if (i.getTypeId() == Interactable.TYPE_CREATURE) {
                        ((Creature) i).doPassiveActions();
                    }
                }
            }
        }
    }

}