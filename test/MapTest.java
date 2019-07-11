// todo write tests for every goddamn function that may or may not exist

import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    private Map map;
    private static final String test_map =
            "@@@ @@"+
            "@@ @ @"+
            "@   @@"+
            "@@@@@@";
    private static final int test_height = 4;
    private static final int test_width = 6;
    private static final int test_startX = 1;
    private static final int test_startY = 2;
    private static final int winX = 3;
    private static final int winY = 0;


    @BeforeEach
    void beforeEachTest(){
        map = new Map(test_height, test_width, test_map, test_startX, test_startY);
    }

    @Test
    void ConstructorTest(){
        assertEqualsMapDisplay(
        "######" +
                "######" +
                "######" +
                "######");
        String expMapStr =
                "@@@ @@"+
                "@@ @ @"+
                "@   @@"+
                "@@@@@@";
        char[][] expMap = strToTestCharMtrx(expMapStr);
        assertEqualsCharMatrix(expMap, map.getMap());
//        todo make tests for height and weight field instant
    }

    @Test
    void isIndexValidTest(){
        assertFalse(map.isIndexValid(-1, 0));
        assertFalse(map.isIndexValid(test_height, 0));
        assertFalse(map.isIndexValid(0, -1));
        assertFalse(map.isIndexValid(0, test_width));

        assertTrue(map.isIndexValid(2, 3));
    }

    @Test
    void isTileFloorTest(){
        assertFalse(map.isTileFloor(0, 1));
        assertTrue(map.isTileFloor(2, 2));
    }
    @Test
    void updateDisplayTileOneTest(){
        map.updateDisplayTile(0, 0, 'G');
        assertEqualsMapDisplay(
        "G#####" +
                "######" +
                "######" +
                "######");
    }
    @Test
    void updateDisplayTileTwoSameTest(){
        map.updateDisplayTile(0, 0, 'G');
        map.updateDisplayTile(0, 0, ' ');
        assertEqualsMapDisplay(
                " #####" +
                        "######" +
                        "######" +
                        "######");
    }
    @Test
    void updateDisplayTileTwoDiffTest(){
        map.updateDisplayTile(0, 1, 'G');
        map.updateDisplayTile(3, 2, 'X');
        assertEqualsMapDisplay(
                "######" +
                        "G#####" +
                        "###X##" +
                        "######");
    }

    @Test
    void revealTilesMidTest(){
        map.revealSurroundings(2,2);
        assertEqualsMapDisplay(
        "######"+
                "## ###"+
                "# # ##"+
                "##@###");
    }
    @Test
    void revealTilesNWTest() {
        map.revealSurroundings(0,0);
        assertEqualsMapDisplay(
            "#@####" +
                    "@#####" +
                    "######" +
                    "######");

    }
    @Test
    void revealTilesNETest(){
        map.revealSurroundings(test_width-1,0);
        assertEqualsMapDisplay(
            "####@#" +
                    "#####@" +
                    "######" +
                    "######");
    }
    @Test
    void revealTilesSWTest(){
        map.revealSurroundings(0,test_height-1);
        assertEqualsMapDisplay(
                "######" +
                        "######" +
                        "@#####" +
                        "#@####");
    }
    @Test
    void revealTilesSETest(){
        map.revealSurroundings(test_width-1,test_height-1);
        assertEqualsMapDisplay(
            "######" +
                    "######" +
                    "#####@" +
                    "####@#");
    }

    @Test
    void printDisplayMapTest(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setErr(new PrintStream(err));

        map.printDisplayMap();
        assertEquals("######\r\n" +
                    "######\r\n" +
                    "######\r\n" +
                    "######\r\n", output.toString());
    }
    @Test
    void printDisplayMapUpdatedTest(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setErr(new PrintStream(err));

        map.revealSurroundings(3, 2);
        map.printDisplayMap();
        assertEquals("######\r\n" +
                    "###@##\r\n" +
                    "## #@#\r\n" +
                    "###@##\r\n", output.toString());
    }

/*
    @Test
    void printMovePlaceholderTest(){
        map.printMovePlaceholder();
    }
*/

//    TEST HELPER METHODS*****************************

//    EFFECT converts expectedMap string to char matrix of test height and width
    private char[][] strToTestCharMtrx(String expMapStr){
        char[][] expMatrix = new char[test_height][test_width];
        int i = 0;
        for (int j = 0; j < test_height; j++) {
            for(int k = 0; k<test_width; k++){
                expMatrix[j][k] = expMapStr.charAt(i);
                i++;
            }
        }
        return expMatrix;
    }

//    REQUIRE expMapStr is same size as test_height x test_width
//    EFFECT: tests pass if expMapStr is equal to mapDisplay when mapDisplay is converted into String
    private void assertEqualsMapDisplay(String expMapStr) {
        char[][] expMap = strToTestCharMtrx(expMapStr);
        char[][] actualMap = map.getMapDisplay();
        assertEqualsCharMatrix(expMap, actualMap);
    }

// EFFECT: passes if two char matrices are identical
    private void assertEqualsCharMatrix(char[][] a, char[][] b) {
        for (int i = 0; i < test_height; i++) {
            for (int j = 0; j < test_width; j++) {
                assertEquals(a[i][j], b[i][j]);
            }
        }
    }

}