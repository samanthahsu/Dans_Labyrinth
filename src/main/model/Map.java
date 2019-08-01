package model;

import model.MapObjects.Avatar;
import model.MapObjects.Examinable;
import model.MapObjects.Tile;
import model.MapObjects.creatures.Creature;
import model.MapObjects.features.Feature;
import model.MapObjects.items.Item;
import model.exceptions.edgeOfMapException;
import model.exceptions.mismatchedMapSizeException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
               List<Item> avaItems, List<Examinable> allExaminables, String mapString) throws mismatchedMapSizeException, edgeOfMapException {
        this.height = height;
        this.width = width;
        this.winY = winY;
        this.winX = winX;
        initExaminables(allExaminables);
        initTileMatrix(mapString);
        initAvatar(avaY, avaX, avaItems);
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

////    require: given name of valid examinable, with two strings of y and x
////
////    returns examinable from given strings
//    private Examinable parseToExaminable(String name, String y, String x) {
//        Examinable examinable;
//        int startY = Integer.parseInt(y);
//        int startX = Integer.parseInt(x);
//        switch (name) {
//            case Ennui.NAME:
//                examinable = new Ennui(startY, startX);
//                break;
//            case MossyGate.NAME:
//                examinable = new MossyGate(startY, startX);
//        }
//    }

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
    requires: DEFAULT_HEIGHT, DEFAULT_WIDTH, and allExaminables have been initialized
    effects: builds tile arrayList with characters form mapstring, and allExaminables from interList*/
    private List<Tile> buildTileList(String mapString) {
        List<Tile> returnList = new ArrayList<>();
        Tile newTile;
        int strIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newTile = new Tile(this , y, x, mapString.charAt(strIndex),
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
        for (Examinable i : interList.values()
                ) {
            if (i.getY() == y && i.getX() == x) {
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
        ava.getItemManager().setAvatar();
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
        if (this == o) return true;
        if (!(o instanceof Map)) return false;
        Map map = (Map) o;
        return height == map.height &&
                width == map.width &&
                winY == map.winY &&
                winX == map.winX &&
                ava.equals(map.ava) &&
                tileMatrix.equals(map.tileMatrix) &&
                allExaminables.equals(map.allExaminables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(height, width, winY, winX, ava, tileMatrix, allExaminables);
    }

//    /*
//        effects: returns true if a and b are of same size (indicated by DEFAULT_HEIGHT and DEFAULT_WIDTH)
//        and a and b contain equal tiles in the same order,
//        else returns false
//    */
//    public boolean tileMatrixEquals(List<List<Tile>> a, List<List<Tile>> b, int height, int width) {
//        if (a.size() != height || b.size() != height) {
//            return false;
//        }
//        for (int m = 0; m < height; m++) {
//            if (a.get(m).size() != width || b.get(m).size() != width) {
//                return false;
//            }
//            for (int n = 0; n < width; n++) {
//                if (!a.get(m).get(n).equals(b.get(m).get(n))) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

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
            newExaminable.setY(startY);
            newExaminable.setX(startX);
            if (newExaminable.getMap() != this) {
                newExaminable.setMap(this);
            }
        }
    }

    /*requires:
    * modifies: this, examinable
    * effects: moves examinable to new position in tile matrix, and sets it's
    * internal indexes accordingly*/
    public void moveExaminable (Examinable examinable, int oldy, int oldx, int newy, int newx) {
        String examinableName = examinable.getName();
        if (isIndexValid(oldy, oldx)) {
            tileMatrix.get(oldy).get(oldx).getCurrExaminables().remove(examinableName);
        }
        tileMatrix.get(newy).get(newx).getCurrExaminables().put(examinableName, examinable);
        if (examinable.getY() != newy || examinable.getX() != newx) {
            examinable.setY(newy);
            examinable.setX(newx);
        }
    }

    /*requires: examinable is not null @DEMO8
    * modifies: this
    * effects: removes the examinable from the map at the given location*/
    public void removeExaminable (Examinable toRemove, int currentY, int currentX) {
        String toRemoveName = toRemove.getName();
        tileMatrix.get(currentY).get(currentX).getCurrExaminables().remove(toRemoveName);
        allExaminables.remove(toRemoveName);
    }

    /*requires:
    * modifies:
    * effects: returns true if the tile of requested index is walkable, else false
            if index requested is not on map and throws an exception*/
    public boolean isTileWalkable(int y, int x) throws edgeOfMapException {
        checkIfMapEdge(y, x);
        return tileMatrix.get(y).get(x).isWalkable();
    }

    /* effects: throws exception if given position is not on the map*/
    private void checkIfMapEdge(int y, int x) throws edgeOfMapException {
        if (y < 0 || y >= height || x < 0 || x >= width) {
            throw new edgeOfMapException();
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
        return ava.getY() == winY && ava.getX() == winX;
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