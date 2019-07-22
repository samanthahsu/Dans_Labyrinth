package model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

/*reads and writes game states from the saves folder*/
public class WriterReader implements DefaultMapInfo {

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
    public void writeMap(Map map, String fileName) {
        final String FILENAME = fileName.concat(".txt"); // specifying file type
        String savePath = SAVES_PATH + FILENAME;
        File file = new File(savePath);
        try {
            if (file.createNewFile()) {
                System.out.println("New save file created");
            } else {
                System.out.println("Save file already present... overwriting");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File creation throws exception");
        }

        try {
            FileOutputStream f = new FileOutputStream(new File(FILENAME));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(map); // write map to file
            o.close();
            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
        }
    }

    /*effects: constructs and returns a fresh default map*/
    public Map buildDefaultMap() {
        ArrayList<Tile> newTiles = buildTileArray();
        Map map = new Map(height, width, winY, winX, startY, startX, avaItems, newTiles);
        return map;
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
    public Map readMap (String fileName) {
        final String FILENAME = fileName.concat(".txt"); // specifying file type
        Map savedMap = null;
        try {
            FileInputStream fi = new FileInputStream(new File(FILENAME));
            ObjectInputStream oi = new ObjectInputStream(fi);
            savedMap = (Map) oi.readObject(); // read in map
            oi.close();
            fi.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error initializing stream");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found");
            e.printStackTrace();
        }
        return savedMap;
    }
}
