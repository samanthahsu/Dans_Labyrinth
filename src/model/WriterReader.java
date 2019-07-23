package model;

import exceptions.edgeOfMapException;
import exceptions.mapIsNullException;
import exceptions.mismatchedMapSizeException;
import model.Interactables.Interactable;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/*reads and writes game states from the saves folder*/
public class WriterReader implements DefaultMapData {

    private static final String SAVES_PATH = System.getProperty("user.dir") + "\\saves\\";
    private String savePath;

//    effects: Initializes this with default save path (within project in saves directory)
    public WriterReader() {
        savePath = SAVES_PATH;
    }

//    Initializes this with custom save path
    public WriterReader(String customPath) {
        savePath = customPath;
    }

//    todo for testing only
    public String getSavePath() {
        return savePath;
    }

/*
requires: path is valid
        effects: Writes (map) into text file named (filename) at (savePath)
*/
    public void writeMap(Map map, String fileName) throws IOException {
        final String FILENAME = fileName.concat(".txt"); // specifying file type
        String savePath = SAVES_PATH + FILENAME;
        File file = new File(savePath);
            if (file.createNewFile()) {
                System.out.println("New save file created");
            } else {
                System.out.println("Save file already present... overwriting");
            }

            FileOutputStream f = new FileOutputStream(new File(FILENAME));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(map); // write map to file
            o.close();
            f.close();
    }

    /*effects: constructs and returns a fresh default map
    * catches mismatchedMapSizeException
     */
    public Map buildDefaultMap() throws mapIsNullException, mismatchedMapSizeException, edgeOfMapException {
        ArrayList<Tile> newTiles = buildTileArray();
        Map map = null;
            map = new Map(height, width, winY, winX, startY, startX, avaItems, newTiles);
        setMapsInTiles(map);
        return map;
    }

    /*requires: map already initialized
    modifies: this
    effects: sets map field in each tile todo public for tests*/
    public void setMapsInTiles(Map map) throws mapIsNullException {
        if (map == null) {
            throw new mapIsNullException();
        }
        for (ArrayList<Tile> tiles : map.getTileMatrix()) {
            for (Tile t : tiles) {
                t.setMap(map);
                setMapsInInteractables(map, t.getInteractables());
            }
        }
    }

    /*requires: map already initialized
    modifies: this
    sets map field in each interactable*/
    private void setMapsInInteractables(Map map, HashSet<Interactable> set) {
        for (Interactable i : set) {
            i.setMap(map);
        }
    }

    /*effects: builds tile arrayList with characters from mapstring, and interactables from hashsets*/
    private ArrayList<Tile> buildTileArray() {
        ArrayList<Tile> returnList = new ArrayList<>();
        Tile newTile;
        int strIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newTile = new Tile(y, x, mapString.charAt(strIndex),
                        parseAllInteractables(y, x));
                returnList.add(newTile);
                strIndex++;
            }
        }
        return returnList;
        }

        /*effects: returns hash set of all interactables with given indexes*/
    private HashSet<Interactable> parseAllInteractables(int y, int x) {
        HashSet<Interactable> temp = new HashSet<>();
        for (Interactable i : allInteractables
             ) {
            if (i.getYpos() == y && i.getXpos() == x) {
                temp.add(i);
            }
        }
        return temp;
    }

    //    requires: file (filename) contains valid Map object, if it doesn't, returns null
    //  effects: Reads from (fileName) at (savePath) which it returns as a Map object.
    public Map readMap (String fileName) throws IOException, ClassNotFoundException {
        final String FILENAME = fileName.concat(".txt"); // specifying file type
        Map savedMap = null;
            FileInputStream fi = new FileInputStream(new File(FILENAME));
            ObjectInputStream oi = new ObjectInputStream(fi);
            savedMap = (Map) oi.readObject(); // read in map
            oi.close();
            fi.close();
        return savedMap;
    }
}
