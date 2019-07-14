import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

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

    @BeforeEach
    void beforeEachTest(){
        map = new Map(test_height1, test_width1, test_map1, test_startY1, test_startX1, winY1, winX1);
    }



    @Test
    void ConstructorTestMap1(){
        assertEquals(test_height1, map.getHeight());
        assertEquals(test_width1, map.getWidth());

        assertEqualsMapDisp(
        "######" +
                "#@####" +
                "@* ###" +
                "#@####",
                test_height1, test_width1);
        String expMapStr =
                "@@@ @@"+
                "@@ @ @"+
                "@   @@"+
                "@@@@@@";
        char[][] expMap = strToTestCharMtrx(expMapStr, test_height1, test_width1);
        assertEqualsCharMtrx(expMap, map.getMap(), test_height1, test_width1);
    }
    @Test
    void ConstructorTestMap2(){
        map = new Map(test_height2, test_width2, test_map2, test_startY2, test_startX2, winY2, winX2);
        assertEquals(test_height2, map.getHeight());
        assertEquals(test_width2, map.getWidth());

        assertEqualsMapDisp(
            "#@#" +
                     "@*@" +
                     "# #",
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
                "#@####", test_height1, test_width1);
    }
    @Test
    void updateTileDispTwoSameTest(){
        map.updateTileDisp(0, 0, 'G');
        map.updateTileDisp(0, 0, ' ');
        assertEqualsMapDisp(
                " #####" +
                        "#@####" +
                        "@* ###" +
                        "#@####", test_height1, test_width1);
    }
    @Test
    void updateTileDispTwoDiffTest(){
        map.updateTileDisp(1, 0, 'G');
        map.updateTileDisp(2, 3, 'X');
        assertEqualsMapDisp(
                "######" +
                        "G@####" +
                        "@* X##" +
                        "#@####", test_height1, test_width1);
    }



    @Test
    void revealSurroundingsMidTest(){
        map.revealSurroundings(2, 2);
        assertEqualsMapDisp(
        "######"+
                "#@ ###"+
                "@   ##"+
                "#@@###", test_height1, test_width1);
    }
    @Test
    void revealSurroundingsNWTest() {
        map.revealSurroundings(0, 0);
        assertEqualsMapDisp(
            "#@####" +
                    "@@####" +
                    "@* ###" +
                    "#@####", test_height1, test_width1);

    }
    @Test
    void revealSurroundingsNETest(){
        map.revealSurroundings(0, test_width1 -1);
        assertEqualsMapDisp(
            "####@#" +
                    "#@###@" +
                    "@* ###" +
                    "#@####", test_height1, test_width1);
    }
    @Test
    void revealSurroundingsSWTest(){
        map.revealSurroundings(test_height1 -1, 0);
        assertEqualsMapDisp(
                "######" +
                        "#@####" +
                        "@* ###" +
                        "#@####", test_height1, test_width1);
    }
    @Test
    void revealSurroundingsSETest(){
        map.revealSurroundings(test_height1 -1, test_width1 -1);
        assertEqualsMapDisp(
            "######" +
                    "#@####" +
                    "@* ##@" +
                    "#@##@#", test_height1, test_width1);
    }

    @Test
    void isWinTest() {
//        todo
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
/*
    @Test
    void printMovePlaceholderTest(){
        map.printMovePlaceholder();
    }
*/

//    TEST HELPER METHODS*****************************

    //    REQUIRES: expMapStr is same size as h x w
    //    EFFECT converts expectedMap string to char matrix of test height and width
    private char[][] strToTestCharMtrx(String expMapStr, int height, int width){
        char[][] expMatrix = new char[height][width];
        int i = 0;
        for (int j = 0; j < height; j++) {
            for(int k = 0; k < width; k++){
                expMatrix[j][k] = expMapStr.charAt(i);
                i++;
            }
        }
        return expMatrix;
    }
//    REQUIRES: expMapStr is same size as h x w
//    EFFECT: tests pass if expMapStr is equal to mapDisplay when mapDisplay is converted into String
    private void assertEqualsMapDisp(String expMapStr, int h, int w) {
        char[][] expMap = strToTestCharMtrx(expMapStr, h, w);
        char[][] actualMap = map.getMapDisplay();
        assertEqualsCharMtrx(expMap, actualMap, h, w);
    }
//    REQUIRES: a, b are matrices of size hxw
    // EFFECT: test passes if two char matrices are identical
    private void assertEqualsCharMtrx(char[][] a, char[][] b, int h, int w) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                assertEquals(a[i][j], b[i][j]);
            }
        }
    }
}
