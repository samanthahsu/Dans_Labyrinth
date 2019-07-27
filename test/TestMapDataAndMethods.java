import exceptions.mapException;
import model.Avatar;
import model.Interactables.Interactable;
import model.Interactables.creatures.Ennui;
import model.Interactables.items.Item;
import model.Interactables.items.PizzaBox;
import model.Map;
import model.Tile;
import model.WriterReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TestMapDataAndMethods {

    //    CONSTANT FIELDS
    Map map1;
    Avatar ava1;
    List<Item> itemList1;
    List<Tile> tileList1;
    List<List<Tile>> tileMatrix1;
    List<Interactable> interList1 = new ArrayList<>();
    static final String TEST_MAP_1 =
                      "@@@ @@"
                    + "@  @ @"
                    + "@   @@"
                    + "@@@@@@";
    static final int TEST_HEIGHT_1 = 4;
    static final int TEST_WIDTH_1 = 6;
    static final int TEST_START_Y_1 = 2;
    static final int TEST_START_X_1 = 1;
    static final int WIN_Y_1 = 0;
    static final int WIN_X_1 = 3;


    Map map2;
    private Avatar ava2;
    List<Tile> tileList2;
    private List<Interactable> interList2 = new ArrayList<>();
    private List<List<Tile>> tileMatrix2;
    static final String TEST_MAP_2 =
            "@@@" +
                    "@ @" +
                    "@ @";
    static final int TEST_HEIGHT_2 = 3;
    static final int TEST_WIDTH_2 = 3;
    static final int TEST_START_Y_2 = 1;
    static final int TEST_START_X_2 = 1;
    static final int WIN_Y_2 = 2;
    static final int WIN_X_2 = 1;


    Map map3; //at the win
    private Avatar ava3;
    List<Tile> tileList3;
    private List<Interactable> interList3 = new ArrayList<>(
            /*Arrays.asList(new BloodFish(), new PizzaBox(), new Exo())*/
    );
    private List<List<Tile>> tileMatrix3;
    private static final String TEST_MAP_3 =
            "abcdef"
                    + "ghijkl"
                    + "mnopqr"
                    + "stuvwx";
    private static final int TEST_HEIGHT_3 = 4;
    private static final int TEST_WIDTH_3 = 6;
    private static final int TEST_START_Y_3 = 0;
    private static final int TEST_START_X_3 = 3;
    private static final int WIN_Y_3 = 0;
    private static final int WIN_X_3 = 3;


    Map mapCreature;
    Avatar avaC;
    List<Item> itemListC;
    List<Tile> tileListC;
    List<List<Tile>> tileMatrixC;
    Ennui testEnnui;
    List<Interactable> interListC = new ArrayList<>();
    static final String TEST_MAP_C =
              "@@@@@@@@ @"
            + "@ @    @ @"
            + "@   @@ @ @"
            + "@@@  @   @"
            + "@@@@@@@@@@";
    static final int TEST_HEIGHT_C = 5;
    static final int TEST_WIDTH_C = 10;
    static final int TEST_START_Y_C = 2;
    static final int TEST_START_X_C = 2;
    static final int WIN_Y_C = 0;
    static final int WIN_X_C = 8;
    static final int  ENNUI_CAPTURE_START_Y = 2;
    static final int  ENNUI_CAPTURE_START_X = 1;


    WriterReader wr = new WriterReader();


    /*TEST HELPER METHODS*/
    void initTestMaps() {
        testEnnui = new Ennui(ENNUI_CAPTURE_START_Y, ENNUI_CAPTURE_START_X);
        interListC.add(testEnnui);

//        tileList1 = buildTileArray(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, interList1);
//        tileList2 = buildTileArray(TEST_HEIGHT_2, TEST_WIDTH_2, TEST_MAP_2, interList2);
//        tileList3 = buildTileArray(TEST_HEIGHT_3, TEST_WIDTH_3, TEST_MAP_3, interList3);
//        tileListC = buildTileArray(TEST_HEIGHT_C, TEST_WIDTH_C, TEST_MAP_C, interListC);

        itemList1 = new ArrayList<Item>(
                Arrays.asList(new PizzaBox())
        );
        itemListC = new ArrayList<Item>(
                Arrays.asList(new PizzaBox())
        );

        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, interList1, TEST_MAP_1);
            map2 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                    new ArrayList<Item>(), interList2, TEST_MAP_2);
            map3 = new Map(TEST_HEIGHT_3, TEST_WIDTH_3, WIN_Y_3, WIN_X_3, TEST_START_Y_3, TEST_START_X_3,
                    new ArrayList<Item>(), interList3, TEST_MAP_3);
            mapCreature = new Map(TEST_HEIGHT_C, TEST_WIDTH_C, WIN_Y_C, WIN_X_C, TEST_START_Y_C, TEST_START_X_C,
                    itemListC, interListC, TEST_MAP_C);
        } catch (mapException e) {
            e.printStackTrace();
        }

//        setting avatar and tileMatrix for easier access in tests
        ava1 = map1.getAva();
        ava2 = map2.getAva();
        ava3 = map3.getAva();
        avaC = mapCreature.getAva();
        tileMatrix1 = map1.getTileMatrix();
        tileMatrix2 = map2.getTileMatrix();
        tileMatrix3 = map3.getTileMatrix();
        tileMatrixC = mapCreature.getTileMatrix();
        }


//    /*builds tile arrayList with characters form mapstring, and interactables from interList*/
//    List<Tile> buildTileArray(int DEFAULT_HEIGHT, int DEFAULT_WIDTH, String DEFAULT_MAP_STRING, List<Interactable> interList) {
//        List<Tile> returnList = new ArrayList<>();
//        Tile newTile;
//        int strIndex = 0;
//        for (int y = 0; y < DEFAULT_HEIGHT; y++) {
//            for (int x = 0; x < DEFAULT_WIDTH; x++) {
//                newTile = new Tile(, y, x,
//                        DEFAULT_MAP_STRING.charAt(strIndex), parseAllInteractables(y, x, interList));
//                returnList.add(newTile);
//                strIndex++;
//            }
//        }
//        return returnList;
//    }
//    /*returns Arraylist of all interactables with given indexes*/
//    private ArrayList<Interactable> parseAllInteractables(int y, int x, List<Interactable> interList) {
//        ArrayList<Interactable> temp = new ArrayList<>();
//        for (Interactable i : interList
//                ) {
//            if (i.getCurrY() == y && i.getCurrX() == x) {
//                temp.add(i);
//            }
//        }
//        return temp;
//    }

    /*  initializes tileMatrix
    requires: tileList to be in order and of size h*w, h and w are init
    modifies: this
    effects: takes tileList, and formats it into a matrix for easier access
*/
    protected ArrayList<ArrayList<Tile>> initTileMatrix(List<Tile> tileList, int height, int width) {
        ArrayList<ArrayList<Tile>> returnTileMatrix = new ArrayList<>(height);
        ArrayList<Tile> tileRow;
        int i = 0;
        for (int m = 0; m < height; m++) {
            tileRow = new ArrayList<>(width);
            for (int n = 0; n < width; n++) {
                tileRow.add(tileList.get(i));
                i++;
            }
            returnTileMatrix.add(tileRow);
        }
        return returnTileMatrix;
    }

    /*requires the two lists to be of the same size
effects: returns true if both lists have items in the same order of the same name*/
    boolean itemListEquals(List<Item> itemList, List<Item> otherItemList) {
        String item;
        String otherItem;
        if (itemList.size() != otherItemList.size()) {
            return false;
        }
        for (int i = 0; i < itemList.size(); i++) {
            item = itemList.get(i).getName();
            otherItem = otherItemList.get(i).getName();
            if (item == null && otherItem == null) {
            } else if (item == null || otherItem == null) {
                return false;
            } else if (!item.equals(otherItem)) {
                return false;
            }
        }
        return true;
    }

    void printDisplayMap(Map map) {
        int height = map.getHeight();
        int width = map.getWidth();
        List<List<Tile>> tileMatrix = map.getTileMatrix();
        char displayTile;
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
                displayTile = tileMatrix.get(m).get(n).getActualChar();
                System.out.print(displayTile);
            }
            System.out.println();
        }
    }
    /*contains method for ArrayList<Interactables>
    returns true if interactable named name, is present int interactables*/
    boolean interactablesContains(ArrayList<Interactable> interactables, String name) {
        for (Interactable i : interactables
                ) {
            if (i.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
