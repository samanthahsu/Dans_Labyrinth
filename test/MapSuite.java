import model.Interactable;
import model.Map;

import java.util.ArrayList;

public class MapSuite {

    //    CONSTANT FIELDS
    protected Map map1;
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

    public MapSuite() {
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, TEST_START_Y_1, TEST_START_X_1, WIN_Y_1, WIN_X_1,
                new ArrayList<Interactable>(), new ArrayList<Interactable>());
        map2 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, TEST_MAP_2, TEST_START_Y_2, TEST_START_X_2, WIN_Y_2, WIN_X_2,
                new ArrayList<Interactable>(), new ArrayList<Interactable>());
        map3 = new Map(TEST_HEIGHT_3, TEST_WIDTH_3, TEST_MAP_3, TEST_START_Y_3, TEST_START_X_3, WIN_Y_3, WIN_X_3,
                new ArrayList<Interactable>(), new ArrayList<Interactable>());

    }
}
