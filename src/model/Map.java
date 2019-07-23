package model;

import exceptions.edgeOfMapException;
import exceptions.mismatchedMapSizeException;
import model.Interactables.Interactable;
import model.Interactables.creatures.Creature;
import model.Interactables.items.Item;

import java.io.Serializable;
import java.util.ArrayList;

/*manages the map portion of this adventure*/
public class Map implements Serializable { //todo add assertion stuff

//    default characters representing each display element
    public static final char wall = '@';
    public static final char floor = ' ';
    public static final char c = '*';
    public static final char fog = '#';

    // stores what the user can see of the map (character, fog, walls) for easy printing
    private int height;
    private int width;
    private int winY;
    private int winX;
    private Avatar ava;
//    holds interactables and other tile information
    private ArrayList<ArrayList<Tile>> tileMatrix;


/*constructor
EFFECTS: height*width == tileList.size()
sets height and width of map, win coordinates, and tileMatrix from tileList.
initializes avatar at given coordinates with its items
    else throws mismatchedMapSizeException
*/
    public Map(int height, int width, int winY, int winX, int avaY, int avaX,
               ArrayList<Item> avaItems, ArrayList<Tile> tileList) throws mismatchedMapSizeException, edgeOfMapException {
        this.height = height;
        this.width = width;
        this.winY = winY;
        this.winX = winX;
        initTileMatrix(tileList);
        initAvatar(avaY, avaX, avaItems);
    }

/*  initializes tileMatrix
    modifies: this
    effects: if tileList to be in order and of size h*w, h and w are init
    takes tileList, and formats it into a matrix for easier access
    else throws mismatchedMapSizeException
*/
    private void initTileMatrix(ArrayList<Tile> tileList) throws mismatchedMapSizeException {
        if (tileList.size() != height * width) {
            throw new mismatchedMapSizeException();
        }
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
    private void initAvatar(int startY, int startX, ArrayList<Item> items) throws edgeOfMapException {
        ava = new Avatar(startY, startX, items, this);
        updateTileDisplay(startY, startX, c);
        revealSurroundings(startY, startX);
    }

    /*
    requires: y, x are valid coordinates on the map // todo throw edge of map exception
    modifies: this, tileMatrix
    effects: updates display char at y, x to c in tileMatrix
    */
    public void updateTileDisplay(int y, int x, char c) throws edgeOfMapException {
        checkIfMapEdge(y, x);
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
    effects: returns true if a and b are of same size (indicated by height and width)
    and a and b contain equal tiles in the same order,
    else returns false
*/
    public boolean tileMatrixEquals(ArrayList<ArrayList<Tile>> a, ArrayList<ArrayList<Tile>> b, int height, int width) {
        if (a.size() != height || b.size()!= height) {
            return false;
        }
        for (int m = 0; m < height; m++) {
            if (a.get(m).size() != width || b.get(m).size() != width) {
                return false;
            }
            for (int n = 0; n < width; n++) {
                if (!a.get(m).get(n).equals(b.get(m).get(n))) {
                    return false;
                }
            }
        }
        return true;
    }

    //    EFFECTS: return true if index within bounds of the map
    public boolean isIndexValid(int y, int x) {
        return y >= 0 && x >= 0 && y < height && x < width;
    }

    public void addInteractable(Interactable i, int y, int x) {
        if (isIndexValid(y, x)) {
            tileMatrix.get(y).get(x).getInteractables().add(i);
        }
    }
/*
        EFFECTS: returns true if tile of requested index is walkable, else false
        if index requested is not on map, throw edgeOfMapException
*/
    public boolean isTileWalkable(int y, int x) throws edgeOfMapException {
        checkIfMapEdge(y, x);
        return tileMatrix.get(y).get(x).isWalkable();
    }

    private void checkIfMapEdge(int y, int x) throws edgeOfMapException {
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new edgeOfMapException();
        }
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
        checkAndRevealTileDisp(y, x);
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
        for (ArrayList<Tile> tileArrayList : tileMatrix) {
            for (Tile tile : tileArrayList) {
                for (Interactable i : tile.getInteractables()) {
                    if (i.getName().equals("ennui")) {
                        ((Creature) i).doPassiveActions(); // todo ennui removing and adding itself
                        return;
                    }
                }
            }
        }
    }

}