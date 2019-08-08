package model;

import model.exceptions.EdgeOfMapException;
import model.exceptions.MismatchedMapSizeException;
import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.Tile;
import model.mapobjects.creatures.Creature;
import model.mapobjects.features.Feature;
import model.mapobjects.items.Item;
import ui.PrintObserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*manages the map portion of this adventure*/
public class Map implements Serializable {

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
    //    holds allExaminables and other tile information
    // TODO @DEMO8: tileMatrix and allExaminables in TileMatrix needs to be synced
    private List<List<Tile>> tileMatrix;
    //    for easier access to all allExaminables @DEMO8
    private HashMap<String, Examinable> allExaminables;


    /*constructor
    * requires: height and width match that of mapString
    * effects: constructor, initializes all fields*/
    public Map(int height, int width, int winY, int winX, int avaY, int avaX,
               List<Item> avaItems, List<Examinable> allExaminables, String mapString)
            throws MismatchedMapSizeException, EdgeOfMapException {
        this.height = height;
        this.width = width;
        this.winY = winY;
        this.winX = winX;
        initExaminables(allExaminables);
        initTileMatrix(mapString);
        initAvatar(avaY, avaX, avaItems);
    }

/*effects adds given observer to every observable on the map*/
    public void addObservers(PrintObserver po) {
        ava.addObserver(po);
        for (Examinable e : allExaminables.values()) {
            e.addObserver(po);
        }
    }

    /* modifies: this
    * effects: initializes all examinables in the map*/
    private void initExaminables(List<Examinable> examinables) {
        this.allExaminables = new HashMap<>();
        for (Examinable exa : examinables) {
            exa.setMap(this);
            this.allExaminables.put(exa.getName(), exa);
        }
    }

    /*  initializes tileMatrix
        requires: DEFAULT_HEIGHT, DEFAULT_WIDTH have been initialized
        modifies: this
        effects: if tileList to be in order and of size h*w, h and w are init
        takes tileList, and formats it into a matrix for easier access
        else throws MismatchedMapSizeException
    */
    private void initTileMatrix(String mapString) throws MismatchedMapSizeException {
        if (mapString.length() != height * width) {
            throw new MismatchedMapSizeException();
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
    requires: DEFAULT_HEIGHT, DEFAULT_WIDTH, and allExaminables have been initialized
    effects: builds tile arrayList with characters form mapstring, and allExaminables from interList*/
    private List<Tile> buildTileList(String mapString) {
        List<Tile> returnList = new ArrayList<>();
        Tile newTile;
        int strIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newTile = new Tile(this, y, x, mapString.charAt(strIndex),
                        getInteractablesAtIndex(y, x, allExaminables));
                returnList.add(newTile);
                strIndex++;
            }
        }
        return returnList;
    }

    /*effects: returns ArrayList of all allExaminables with given indexes*/
    private ArrayList<Examinable> getInteractablesAtIndex(int y, int x, HashMap<String, Examinable> interList) {
        ArrayList<Examinable> temp = new ArrayList<>();
        for (Examinable i : interList.values()) {
            if (i.getYc() == y && i.getXc() == x) {
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
    private void initAvatar(int startY, int startX, List<Item> items) throws EdgeOfMapException {
        ava = new Avatar(startY, startX, items, this);
        updateTileDisplay(startY, startX, AVATAR);
        revealSurroundings(startY, startX);
        ava.getItemManager().setAvatar();
    }

    /*
    requires: y, x are valid coordinates on the map
    modifies: this, tileMatrix
    effects: updates display char at y, x to AVATAR in tileMatrix
    */
    public void updateTileDisplay(int y, int x, char c) throws EdgeOfMapException {
        checkIfMapEdge(y, x);
        tileMatrix.get(y).get(x).setCurrChar(c);
    }

    //    getters
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

    public HashMap<String, Examinable> getAllExaminables() {
        return allExaminables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Map)) {
            return false;
        }
        Map map = (Map) o;
        return height == map.height
                && width == map.width
                && ava.equals(map.ava)
                && tileMatrix.equals(map.tileMatrix)
                && allExaminables.equals(map.allExaminables);
    }

    //    effects: returns true if index is within bounds of the map
    public boolean isIndexValid(int y, int x) {
        return y >= 0 && x >= 0 && y < height && x < width;
    }

    /*requires: examinable is not null @DEMO8
    * effects: this
    * requires: adds new examinable to the map at the given position*/
    public void addExaminable(Examinable newExaminable, int startY, int startX) {
        if (isIndexValid(startY, startX)) {
            tileMatrix.get(startY).get(startX).getCurrExaminables().put(newExaminable.getName(), newExaminable);
            allExaminables.put(newExaminable.getName(), newExaminable);
            newExaminable.setYc(startY);
            newExaminable.setXc(startX);
            if (newExaminable.getMap() != this) {
                newExaminable.setMap(this);
            }
        }
    }

    /*requires:
    * modifies: this, examinable
    * effects: moves examinable to new position in tile matrix, and sets it's
    * internal indexes accordingly*/
    public void moveExaminableOnTiles(Examinable examinable, int newy, int newx) {
        String examinableName = examinable.getName();
        int oldy = examinable.getYc();
        int oldx = examinable.getXc();
        if (isIndexValid(oldy, oldx)) {
            tileMatrix.get(oldy).get(oldx).getCurrExaminables().remove(examinableName);
        }
        tileMatrix.get(newy).get(newx).getCurrExaminables().put(examinableName, examinable);
        if (oldy != newy || oldx != newx) {
            examinable.setYc(newy);
            examinable.setXc(newx);
        }
    }

    /*requires: examinable is not null @DEMO8
    * modifies: this
    * effects: removes the examinable from the map at the given location*/
    public void removeExaminable(Examinable toRemove, int currentY, int currentX) {
        String toRemoveName = toRemove.getName();
        tileMatrix.get(currentY).get(currentX).getCurrExaminables().remove(toRemoveName);
        allExaminables.remove(toRemoveName);
    }

    public Examinable tileFetchExaminable(int y, int x, String name) {
        return tileMatrix.get(y).get(x).getCurrExaminables().get(name);
    }

    /*requires:
    * modifies:
    * effects: returns true if the tile of requested index is walkable, else false
            if index requested is not on map and throws an exception*/
    public boolean isTileWalkable(int y, int x) throws EdgeOfMapException {
        checkIfMapEdge(y, x);
        return tileMatrix.get(y).get(x).isWalkable();
    }

    /* effects: throws exception if given position is not on the map*/
    private void checkIfMapEdge(int y, int x) throws EdgeOfMapException {
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new EdgeOfMapException();
        }
    }

    /* modifies: this
     * effects: iff given index is valid, reveal the tile at the given index
            todo throw edge of map exception, alt fail quietly since is expected to happen*/
    private void checkAndRevealTileDisplay(int y, int x) {
        if (isIndexValid(y, x)) {
            tileMatrix.get(y).get(x).revealTile();
        }
    }

    /* modifies: this
      * effects: if tile index is valid, reveal the 4 (or less) tiles orthogonally
      * around y, x (no diagonals) else do nothing? todo throw exc?*/
    public void revealSurroundings(int y, int x) {
        checkAndRevealTileDisplay(y, x);
        checkAndRevealTileDisplay(y, x - 1);
        checkAndRevealTileDisplay(y, x + 1);
        checkAndRevealTileDisplay(y - 1, x);
        checkAndRevealTileDisplay(y + 1, x);
    }

    /*effects: returns true if player is on the winning tile*/
    public boolean isWin() {
        return ava.getYc() == winY && ava.getXc() == winX;
    }

    /*modifies: this
    effects: executes the doPassiveActions for each feature or creature in allExaminables
    different for each type of creature including:
    sound scope: how far away from ava for ava to get message feedback about disturbance
    movement: how far and in what pattern each creature moves (if they try to avoid or to approach ava)
    checking if areas are open or closed*/
    public void nextState() {
        for (Examinable i : allExaminables.values()) {
            if (i.getTypeId() == Examinable.TYPE_CREATURE) {
                ((Creature) i).doPassiveActions();
            } else if (i.getTypeId() == Examinable.TYPE_FEATURE) {
                ((Feature) i).doPassiveActions();
            }
        }
    }
}