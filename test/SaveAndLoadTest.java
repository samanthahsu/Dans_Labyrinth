import model.Map;
import model.SaveAndLoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveAndLoadTest extends TestHelpers {

    private static final String PROJECT_PATH = System.getProperty("user.dir");

    private Map map1;
    private SaveAndLoad svl;
    private static final String test_map1 =
            "@@@ @@"+
            "@@ @ @"+
            "@   @@"+
            "@@@@@@";
    private static final int test_height1 = 4;
    private static final int test_width1 = 6;
    private static final int test_startY1 = 2;
    private static final int test_startX1 = 1;
    private static final int winY1 = 0;
    private static final int winX1 = 3;

    private Map map2;
    private static final String test_map2 =
            "  +  g"+
            "  +  h"+
            "  +  i"+
            "  +  k";
    private static final int test_height2 = 4;
    private static final int test_width2 = 6;
    private static final int test_startY2 = 2;
    private static final int test_startX2 = 1;
    private static final int winY2 = 0;
    private static final int winX2 = 3;


    @BeforeEach
    void BeforeEach() {
        svl = new SaveAndLoad();
        map1 = new Map(test_height1, test_width1, test_map1, test_startY1,
                test_startX1, winY1, winX1, cs);
        map2 = new Map(test_height2, test_width2, test_map2, test_startY2, test_startX2, winY2, winX2, cs);
    }

    @Test
    void saveGameTest() {
        svl.saveGame("svl", map1, map1.getAva());
        assertEqualsFile(PROJECT_PATH+"\\test\\svlExpected1",
                PROJECT_PATH+"\\saves\\svl");

        svl.saveGame("svl", map2, map2.getAva());
        assertEqualsFile(PROJECT_PATH+"\\test\\svlExpected2",
                PROJECT_PATH+"\\saves\\svl");

    }

    @Test
    void loadFileTest() {
        assertEquals(null, svl.loadFile("thisOneDoesn'tExist"));

        svl.saveGame("svl", map2, map2.getAva());
        Map newMap = svl.loadFile("svl");
        assertEqualsMapObj(map2, newMap);
    }


}
