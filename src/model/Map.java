package model;

import exceptions.edgeOfMapException;
import exceptions.mismatchedMapSizeException;
import model.Interactables.Interactable;
import model.Interactables.creatures.Creature;
import model.Interactables.features.Feature;
import model.Interactables.items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/*manages the map portion of this adventure*/
public class Map implements Serializable { //todo add assertion stuff

    //    default characters representing each display element
    public static final char WALL = '@';
    public static final char FLOOR = ' ';
    public static final char AVATAR = '*';
    public static final char FOG = '#';

    // stores what the user can see of the map (character, FOG, walls) for easy printing
    private int height;
    private int width;
    private int winY;
    private int winX;
    private Avatar ava;
    //    holds interactables and other tile information
    private List<List<Tile>> tileMatrix;
    //    for easier access to all interactables
    private HashMap<String, Interactable> interactables;


    /*constructor
    EFFECTS: DEFAULT_HEIGHT*DEFAULT_WIDTH == tileList.size()
    sets DEFAULT_HEIGHT and DEFAULT_WIDTH of map, win coordinates, and interactables
    then sets initializes tileMatrix from DEFAULT_MAP_STRING
    initializes avatar at given coordinates with its items
        else throws mismatchedMapSizeException
    */
    public Map(int height, int width, int winY, int winX, int avaY, int avaX,
               List<Item> avaItems, List<Interactable> interactables, String mapString) throws mismatchedMapSizeException, edgeOfMapException {
        this.height = height;
        this.width = width;
        this.winY = winY;
        this.winX = winX;
//        this.interactables = interactables;
        initInteractables(interactables);
        initTileMatrix(mapString);
        initAvatar(avaY, avaX, avaItems);
    }

    /* modifies: this
    effects: sets the map variable in each interactable in list to this map
    * and sets interactables in map to interactables list*/
    private void initInteractables(List<Interactable> interactables) {
        this.interactables = new HashMap<>();
        for (Interactable i : interactables) { //todo make hashmap
            i.setMap(this);
            this.interactables.put(i.getName(), i);
        }
    }

    /*  initializes tileMatrix
        requires: DEFAULT_HEIGHT, DEFAULT_WIDTH have been initialized
        modifies: this
        effects: if tileList to be in order and of size h*w, h and w are init
        takes tileList, and formats it into a matrix for easier access
        else throws mismatchedMapSizeException
    */
    private void initTileMatrix(String mapString) throws mismatchedMapSizeException {
        if (mapString.length() != height * width) {
            throw new mismatchedMapSizeException();
        }
        List<Tile> tileList = buildTileList(mapString);
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

    /*builds a list of tiles that will be used to construct tileMatrix
    requires: DEFAULT_HEIGHT, DEFAULT_WIDTH, and interactables have been initialized
    effects: builds tile arrayList with characters form mapstring, and interactables from interList*/
    private List<Tile> buildTileList(String mapString) {
        List<Tile> returnList = new ArrayList<>();
        Tile newTile;
        int strIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newTile = new Tile(this , y, x, mapString.charAt(strIndex),
                        getInteractablesAtIndex(y, x, interactables));
                returnList.add(newTile);
                strIndex++;
            }
        }
        return returnList;
    }

    /*effects: returns ArrayList of all interactables with given indexes*/
    private ArrayList<Interactable> getInteractablesAtIndex(int y, int x, HashMap<String, Interactable> interList) {
        ArrayList<Interactable> temp = new ArrayList<>();
        for (Interactable i : interList.values()
                ) {
            if (i.getYpos() == y && i.getXpos() == x) {
                temp.add(i);
            }
        }
        return temp;
    }


    /* initializes avatar
        MODIFIES: this, ava
        EFFECTS: construct new Avatar with given coordinates and items
        places avatar character at ypos, xpos in tileMatrix, revealing adjacent tiles
    */
    private void initAvatar(int startY, int startX, List<Item> items) throws edgeOfMapException {
        ava = new Avatar(startY, startX, items, this);
        updateTileDisplay(startY, startX, AVATAR);
        revealSurroundings(startY, startX);
    }

    /*
    requires: y, x are valid coordinates on the map
    modifies: this, tileMatrix
    effects: updates display char at y, x to AVATAR in tileMatrix
    */
    public void updateTileDisplay(int y, int x, char c) throws edgeOfMapException {
        checkIfMapEdge(y, x);
        tileMatrix.get(y).get(x).setCurrChar(c);
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

    public List<List<Tile>> getTileMatrix() {
        return tileMatrix;
    }

    public HashMap<String, Interactable> getInteractables() {
        return interactables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map)) return false;
        Map map = (Map) o;
        return height == map.height &&
                width == map.width &&
                winY == map.winY &&
                winX == map.winX &&
                ava.equals(map.ava) &&
                tileMatrix.equals(map.tileMatrix) &&
                interactables.equals(map.interactables);
    }

    @Override
    public int hashCode() {

        return Objects.hash(height, width, winY, winX, ava, tileMatrix, interactables);
    }

    /*
        effects: returns true if a and b are of same size (indicated by DEFAULT_HEIGHT and DEFAULT_WIDTH)
        and a and b contain equal tiles in the same order,
        else returns false
    */
    public boolean tileMatrixEquals(List<List<Tile>> a, List<List<Tile>> b, int height, int width) {
        if (a.size() != height || b.size() != height) {
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
            tileMatrix.get(y).get(x).getCurrInteractables().put(i.getName(), i);
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
        return ava.getCurrY() == winY && ava.getCurrX() == winX;
    }

    /*
            modifies: this
            EFFECTS: executes the doPassiveActions for each feature or creature in interactables
            different for each type of creature including:
            sound scope: how far away from ava for ava to get message feedback about disturbance
            movement: how far and in what pattern each creature moves (if they try to avoid or to approach ava)
            checking if areas are open or closed
            todo
    */
    public void nextState() {
        for (Interactable i : interactables.values()) {
            if (i.getTypeId() == Interactable.TYPE_CREATURE) {
                ((Creature) i).doPassiveActions();
            } else if (i.getTypeId() == Interactable.TYPE_FEATURE) {
                ((Feature) i).doPassiveActions();
            }
        }
    }
}