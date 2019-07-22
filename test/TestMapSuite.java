import exceptions.mismatchedMapSizeException;
import model.Avatar;
import model.Interactables.Interactable;
import model.Interactables.items.Item;
import model.Interactables.items.PizzaBox;
import model.Map;
import model.Tile;

import java.util.ArrayList;
import java.util.HashSet;

public class TestMapSuite {

    //    CONSTANT FIELDS
    protected Map map1;
    protected Avatar ava1;
    protected ArrayList<Item> itemList1;
    ArrayList<Tile> tileList1;
    protected ArrayList<ArrayList<Tile>> tileMatrix1;
    protected ArrayList<Interactable> interList1 = new ArrayList<>();
    protected static final String TEST_MAP_1 =
                    "@@@ @@"
                    + "@@ @ @"
                    + "@   @@"
                    + "@@@@@@";
    protected static final int TEST_HEIGHT_1 = 4;
    protected static final int TEST_WIDTH_1 = 6;
    protected static final int TEST_START_Y_1 = 2;
    protected static final int TEST_START_X_1 = 1;
    protected static final int WIN_Y_1 = 0;
    protected static final int WIN_X_1 = 3;


    protected Map map2;
    protected Avatar ava2;
    ArrayList<Tile> tileList2;
    protected ArrayList<Interactable> interList2 = new ArrayList<>();
    protected ArrayList<ArrayList<Tile>> tileMatrix2;
    protected static final String TEST_MAP_2 =
            "@@@" +
                    "@ @" +
                    "@ @";
    protected static final int TEST_HEIGHT_2 = 3;
    protected static final int TEST_WIDTH_2 = 3;
    protected static final int TEST_START_Y_2 = 1;
    protected static final int TEST_START_X_2 = 1;
    protected static final int WIN_Y_2 = 2;
    protected static final int WIN_X_2 = 1;

    protected Map map3; //at the win
    protected Avatar ava3;
    ArrayList<Tile> tileList3;
    protected ArrayList<Interactable> interList3 = new ArrayList<>(
            /*Arrays.asList(new BloodFish(), new PizzaBox(), new Exo())*/
    );
    protected ArrayList<ArrayList<Tile>> tileMatrix3;
    protected static final String TEST_MAP_3 =
            "abcdef"
                    + "ghijkl"
                    + "mnopqr"
                    + "stuvwx";
    protected static final int TEST_HEIGHT_3 = 4;
    protected static final int TEST_WIDTH_3 = 6;
    protected static final int TEST_START_Y_3 = 0;
    protected static final int TEST_START_X_3 = 3;
    protected static final int WIN_Y_3 = 0;
    protected static final int WIN_X_3 = 3;

    protected void initTestMaps() {
        tileList1 = buildTileArray(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, interList1);
        itemList1 = new ArrayList<>();
        itemList1.add(new PizzaBox());
        tileList2 = buildTileArray(TEST_HEIGHT_2, TEST_WIDTH_2, TEST_MAP_2, interList2);
        tileList3 = buildTileArray(TEST_HEIGHT_3, TEST_WIDTH_3, TEST_MAP_3, interList3);

        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, tileList1);
            map2 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                    new ArrayList<Item>(), tileList2);
            map3 = new Map(TEST_HEIGHT_3, TEST_WIDTH_3, WIN_Y_3, WIN_X_3, TEST_START_Y_3, TEST_START_X_3,
                    new ArrayList<Item>(), tileList3);
        } catch (mismatchedMapSizeException e) {
            e.printStackTrace();
        }

        ava1 = map1.getAva();
        tileMatrix1 = map1.getTileMatrix();
        ava2 = map2.getAva();
        tileMatrix2 = map2.getTileMatrix();
        ava3 = map3.getAva();
        tileMatrix3 = map3.getTileMatrix();

    }


    /*builds tile arrayList with characters form mapstring, and interactables from hashsets*/
    protected ArrayList<Tile> buildTileArray(int height, int width, String mapString, ArrayList<Interactable> interList) {
        ArrayList<Tile> returnList = new ArrayList<>();
        Tile newTile;
        int strIndex = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                newTile = new Tile(y, x, mapString.charAt(strIndex),
                        parseAllInteractables(y, x, interList));
                returnList.add(newTile);
                strIndex++;
            }
        }
        return returnList;
    }
    /*returns hash set of all interactables with given indexes*/
    private HashSet<Interactable> parseAllInteractables(int y, int x, ArrayList<Interactable> interList) {
        HashSet<Interactable> temp = new HashSet<>();
        for (Interactable i : interList
                ) {
            if (i.getYpos() == y && i.getXpos() == x) {
                temp.add(i);
            }
        }
        return temp;
    }

    /*  initializes tileMatrix
    requires: tileList to be in order and of size h*w, h and w are init
    modifies: this // todo throw mismatched map size exception
    effects: takes tileList, and formats it into a matrix for easier access
*/
    protected ArrayList<ArrayList<Tile>> initTileMatrix(ArrayList<Tile> tileList, int height, int width) {
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
    protected boolean itemListEquals(ArrayList<Item> itemList, ArrayList<Item> otherItemList) {
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


}
