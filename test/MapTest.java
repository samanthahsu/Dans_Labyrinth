import model.Exo;
import model.Interactable;
import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapTest extends TestHelpers{

    private Map map;
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
    private static final int cY1 = 1;
    private static final int cX1 = 3;

    private static final String test_map2 =
            "@@@"+
            "@ @"+
            "@ @";
    private static final int test_height2 = 3;
    private static final int test_width2 = 3;
    private static final int test_startY2 = 1;
    private static final int test_startX2 = 1;
    private static final int winY2 = 2;
    private static final int winX2 = 1;
    private static final int cY2 = 1;
    private static final int cX2 = 1;

    private static final ArrayList<Interactable> cs = new ArrayList<>();


    @BeforeEach
    void beforeEachTest(){
        cs.add(new Exo(cY1, cX1));
        map = new Map(test_height1, test_width1, test_map1, test_startY1, test_startX1, winY1, winX1, cs);
    }



    @Test
    void ConstructorTestMap1(){
        assertEquals(test_height1, map.getHeight());
        assertEquals(test_width1, map.getWidth());

        assertEqualsMapDisp(
        "######" +
                "#@####" +
                "@* ###" +
                "#@####", map,
                test_height1, test_width1);
        String expMapStr =
                "@@@ @@"+
                "@@ @ @"+
                "@   @@"+
                "@@@@@@";
        char[][] expMap = strToTestCharMtrx(expMapStr, test_height1, test_width1);
        assertEqualsCharMtrx(expMap, map.getMap(), test_height1, test_width1);

        ArrayList<Interactable> expected = new ArrayList<>();
        expected.add(new Exo(cY1, cX1));
        assertEqualsInteractableMtrx(InteractableMatrix(expected, map.getHeight(), map.getWidth()), map.getInteractables(),
                map.getHeight(), map.getWidth());
    }
    @Test
    void ConstructorTestMap2(){
        cs.remove(0);
        cs.add(new Exo(cY2, cX2));
        map = new Map(test_height2, test_width2, test_map2, test_startY2, test_startX2, winY2, winX2, cs);
        assertEquals(test_height2, map.getHeight());
        assertEquals(test_width2, map.getWidth());

        assertEqualsMapDisp(
            "#@#" +
                     "@*@" +
                     "# #", map,
                test_height2, test_width2);
        String expMapStr =
                "@@@"+
                "@ @"+
                "@ @";
        char[][] expMap = strToTestCharMtrx(expMapStr, test_height2, test_width2);
        assertEqualsCharMtrx(expMap, map.getMap(), test_height2, test_width2);
    }



    @Test
    void isIndexValidTest(){
        assertFalse(map.isIndexValid(-1, 0));
        assertFalse(map.isIndexValid(test_height1, 0));
        assertFalse(map.isIndexValid(0, -1));
        assertFalse(map.isIndexValid(0, test_width1));

        assertTrue(map.isIndexValid(2, 3));
    }

    @Test
    void isTileFloorTest(){
        assertFalse(map.isTileFloor(0, 1));
        assertTrue(map.isTileFloor(2, 2));
    }


    @Test
    void updateTileDispOneTest(){
        map.updateTileDisp(0, 0, 'G');
        assertEqualsMapDisp(
        "G#####" +
                "#@####" +
                "@* ###" +
                "#@####", map, test_height1, test_width1);
    }
    @Test
    void updateTileDispTwoSameTest(){
        map.updateTileDisp(0, 0, 'G');
        map.updateTileDisp(0, 0, ' ');
        assertEqualsMapDisp(
                " #####" +
                        "#@####" +
                        "@* ###" +
                        "#@####", map, test_height1, test_width1);
    }
    @Test
    void updateTileDispTwoDiffTest(){
        map.updateTileDisp(1, 0, 'G');
        map.updateTileDisp(2, 3, 'X');
        assertEqualsMapDisp(
                "######" +
                        "G@####" +
                        "@* X##" +
                        "#@####", map, test_height1, test_width1);
    }



    @Test
    void revealSurroundingsMidTest(){
        map.revealSurroundings(2, 2);
        assertEqualsMapDisp(
        "######"+
                "#@ ###"+
                "@   ##"+
                "#@@###", map, test_height1, test_width1);
    }
    @Test
    void revealSurroundingsNWTest() {
        map.revealSurroundings(0, 0);
        assertEqualsMapDisp(
            "#@####" +
                    "@@####" +
                    "@* ###" +
                    "#@####", map, test_height1, test_width1);

    }
    @Test
    void revealSurroundingsNETest(){
        map.revealSurroundings(0, test_width1 -1);
        assertEqualsMapDisp(
            "####@#" +
                    "#@###@" +
                    "@* ###" +
                    "#@####", map, test_height1, test_width1);
    }
    @Test
    void revealSurroundingsSWTest(){
        map.revealSurroundings(test_height1 -1, 0);
        assertEqualsMapDisp(
                "######" +
                        "#@####" +
                        "@* ###" +
                        "#@####", map, test_height1, test_width1);
    }
    @Test
    void revealSurroundingsSETest(){
        map.revealSurroundings(test_height1 -1, test_width1 -1);
        assertEqualsMapDisp(
            "######" +
                    "#@####" +
                    "@* ##@" +
                    "#@##@#", map, test_height1, test_width1);
    }

    @Test
    void isWinTest() {
        assertFalse(map.isWin());
        map = new Map(test_height1, test_width1, test_map1, winY1, winX1, winY1, winX1, cs);
        assertTrue(map.isWin());
    }

    @Test
    void printDisplayMapTest(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setErr(new PrintStream(err));

        map.printDisplayMap();
        assertEquals("######\r\n" +
                    "#@####\r\n" +
                    "@* ###\r\n" +
                    "#@####\r\n", output.toString());
    }
    @Test
    void printDisplayMapUpdateTest(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setErr(new PrintStream(err));

        map.revealSurroundings(2, 3);
        map.printDisplayMap();
        assertEquals("######\r\n" +
                            "#@#@##\r\n" +
                            "@* #@#\r\n" +
                            "#@#@##\r\n", output.toString());
    }
}
