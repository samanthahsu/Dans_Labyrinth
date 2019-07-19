import model.Map;

import java.io.*;

/*this class reads and writes game states from the saves folder*/
public class WriterReader {

    private static final String SAVES_PATH = System.getProperty("user.dir") + "\\saves\\";
//    public static final String FILENAME = "testSerializable.txt";

    private String savePath;

/*
    public static void main(String[] args) {
        Map map1;
        final String TEST_MAP_1 =
                "@@@ @@"
                        + "@@ @ @"
                        + "@   @@"
                        + "@@@@@@";
        final int TEST_HEIGHT_1 = 4;
        final int TEST_WIDTH_1 = 6;
        final int TEST_START_Y_1 = 2;
        final int TEST_START_X_1 = 1;
        final int WIN_Y_1 = 0;
        final int WIN_X_1 = 3;

        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, TEST_START_Y_1,
                TEST_START_X_1, WIN_Y_1, WIN_X_1,
                new ArrayList<Interactable>(), new ArrayList<Interactable>());


        String savePath = SAVES_PATH + FILENAME;
        File file = new File(savePath);
        try {
            if (file.createNewFile()) {
                System.out.println("New save file created");
            } else {
                System.out.println("Save file already present");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file creation throws exception");
        }

        try {
            FileOutputStream f = new FileOutputStream(new File(FILENAME));
            ObjectOutputStream o = new ObjectOutputStream(f);

//            write objects to file
            o.writeObject(map1);

            o.close();
            f.close();
//========================
            FileInputStream fi = new FileInputStream(new File(FILENAME));
            ObjectInputStream oi = new ObjectInputStream(fi);

//            Read objects
            Map mapr1 = (Map) oi.readObject();
            System.out.println(mapr1.getHeight());

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
    }
*/

//    Initializes this with default save path (within project in saves directory)
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

    //    Writes (map) into text file named (filename) at (savePath)
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

    //    requires: file (filename) contains valid Map object, if it doesn't, returns null
//  Reads from (fileName) at (savePath) which it returns as a Map object.
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
