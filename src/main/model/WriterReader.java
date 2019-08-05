package model;

import model.exceptions.BadFileNameException;
import model.exceptions.EdgeOfMapException;
import model.exceptions.MismatchedMapSizeException;
import ui.PrintObservable;

import java.io.*;
import java.util.regex.Pattern;

/*reads and writes game states from the saves folder*/
public class WriterReader extends PrintObservable implements DefaultMapData {

//    private static final String FILE_TYPE = ".txt";
    private static final String SAVES_PATH = System.getProperty("user.dir") + "\\saves\\";
    private String savePath = "";

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

    /*requires: path is valid
    effects: Writes (map) into text file named (filename) at (savePath)*/
    public void writeMap(Map map, String fileName) throws IOException, BadFileNameException {
        if (!Pattern.matches("[a-zA-Z_0-9]*", fileName)) {
            throw new BadFileNameException();
        }
        String newSavePath = this.savePath + fileName;
        File file = new File(newSavePath);
        if (file.createNewFile()) {
            notifyObservers("New save file '" + fileName + "' created!");
        } else {
            notifyObservers("Save file '" + fileName + "' already exists. Overwriting...");
        } // todo prevent accidentally overwriting
        FileOutputStream f = new FileOutputStream(new File(fileName));
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(map);
        o.close();
        f.close();
    }

    /*effects: constructs and returns a fresh default map
    * catches MismatchedMapSizeException*/
    public Map buildDefaultMap() throws MismatchedMapSizeException, EdgeOfMapException {
        return new Map(DEFAULT_HEIGHT, DEFAULT_WIDTH, DEFAULT_WIN_Y, DEFAULT_WIN_X, DEFAULT_START_Y,
                DEFAULT_START_X, DEFAULT_AVA_ITEMS_LIST, DEFAULT_INTERACTABLES_LIST, DEFAULT_MAP_STRING);
    }

//    /*requires: map already initialized
//    modifies: this
//    sets map field in each interactable*/
//    private void setMapsInInteractables(Map map, ArrayList<Examinable> examinableArrayList) {
//        for (Examinable i : examinableArrayList) {
//            i.setMap(map);
//        }
//    }

//        /*effects: returns hash set of all interactables with given indexes*/
//    private ArrayList<Examinable> parseAllInteractables(int y, int x) {
//        ArrayList<Examinable> tempList = new ArrayList<>();
//        for (Examinable exa : DEFAULT_INTERACTABLES_LIST) {
//            if (exa.getYc() == y && exa.getXc() == x) {
//                tempList.add(exa);
//            }
//        }
//        return tempList;
//    }

    //    requires: file (filename) contains valid Map object, if it doesn't, returns null
    //  effects: Reads from (fileName) at (savePath) which it returns as a Map object.
    public Map readMap(String fileName) throws IOException, ClassNotFoundException {
        Map savedMap;
        FileInputStream fi = new FileInputStream(new File(fileName));
        ObjectInputStream oi = new ObjectInputStream(fi);
        savedMap = (Map) oi.readObject(); // read in map
        oi.close();
        fi.close();
        return savedMap;
    }
}
