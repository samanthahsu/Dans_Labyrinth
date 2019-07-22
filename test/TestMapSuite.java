import model.Avatar;
import model.Map;
import model.Tile;
import model.items.Item;

import java.util.ArrayList;

public class TestMapSuite {

    //    CONSTANT FIELDS
    protected Map map1;
    protected Avatar ava1;
    protected ArrayList<ArrayList<Tile>> tileMatrix1;
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

    protected Map map3;
    protected Avatar ava3;
    protected ArrayList<ArrayList<Tile>> tileMatrix3;
    protected static final String TEST_MAP_3 =
            "abcdef"
                    + "ghijkl"
                    + "mnopqr"
                    + "stuvwx";
    protected static final int TEST_HEIGHT_3 = 4;
    protected static final int TEST_WIDTH_3 = 6;
    protected static final int TEST_START_Y_3 = 2;
    protected static final int TEST_START_X_3 = 1;
    protected static final int WIN_Y_3 = 0;
    protected static final int WIN_X_3 = 3;

    public void initMaps() {
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                new ArrayList<Item>(), new ArrayList<Tile>());
        ava1 = map1.getAva();
        tileMatrix1 = map1.getTileMatrix();

        map2 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                new ArrayList<Item>(), new ArrayList<Tile>());
        ava2 = map2.getAva();
        tileMatrix2 = map2.getTileMatrix();

        map3 = new Map(TEST_HEIGHT_3, TEST_WIDTH_3, WIN_Y_3, WIN_X_3, TEST_START_Y_3, TEST_START_X_3,
                new ArrayList<Item>(), new ArrayList<Tile>());
        ava3 = map3.getAva();
        tileMatrix3 = map3.getTileMatrix();
    }
}
