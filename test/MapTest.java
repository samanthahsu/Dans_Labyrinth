// todo write tests for every goddamn function that may or may not exist

import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapTest {
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
                "#@####" +
                "@* ###" +
                "#@####");
        String expMapStr =
                "@@@ @@"+
                "@@ @ @"+
                "@   @@"+
                "@@@@@@";
//        char[][] expMap = makeExpectedMap(expMapStr);
//        for (:
//             ) {
//
//        }
//        assertArrayEquals(expMap, map.getMap());
    }
    @Test
    void initAvatarTest(){}
    @Test
    void initMapDisplayTest(){}
    @Test
    void initMapTest(){}

    @Test
    void isTileFloorTest(){
        assertFalse(map.isTileFloor(0, 1));
        assertTrue(map.isTileFloor(2, 2));
    }
    @Test
    void updateDisplayTileTest(){
        map.updateDisplayTile(0, 0, 'G');
        assertEqualsMapDisplay(
        "G#####" +
                "#@####" +
                "@* ###" +
                "#@####");
    }
    @Test
    void revealTilesMidTest(){
        map.revealTiles(2,2);
        assertEqualsMapDisplay(
        "######"+
                "#@ ###"+
                "@   ##"+
                "#@@###");
    }
    @Test
    void revealTilesNWTest() {
        map.revealTiles(0,0);
        assertEqualsMapDisplay(
            "#@####" +
                    "@@####" +
                    "@* ###" +
                    "#@####");

    }
    @Test
    void revealTilesNETest(){
        map.revealTiles(test_width-1,0);
        assertEqualsMapDisplay(
                "####@#" +
                        "#@###@" +
                        "@* ###" +
                        "#@####");
    }
    @Test
    void revealTilesSWTest(){
        map.revealTiles(0,test_height-1);
        assertEqualsMapDisplay(
                "######" +
                        "#@####" +
                        "@* ###" +
                        "#@####");

    }
    @Test
    void revealTilesSETest(){
        map.revealTiles(test_width-1,test_height-1);
        assertEqualsMapDisplay(
                "######" +
                        "#@####" +
                        "@* ##@" +
                        "#@##@#");
    }

    @Test
    void printDisplayMapTest(){}

    @Test
    void printMovePlaceholderTest(){}

//    helper methods

//    EFFECT converts expectedMap string to char matrix of test height and width
    private char[][] makeExpectedMap(String expMapStr){
        char[][] expMatrix = new char[test_height][test_width];
        int i = 0;
        for (char[] tileset: expMatrix) {
            for(char tile: tileset){
                tile = expMapStr.charAt(i);
                i++;
            }
        }
        return expMatrix;
    }

    private void assertEqualsMapDisplay(String expMapStr) {
        char[][] expMap = makeExpectedMap(expMapStr);
        assertEqualsCharMatrix(expMap, map.getMapDisplay());
    }

    private void assertEqualsCharMatrix(char[][] a, char[][] b) {
        for (char[] aChars: a) {
            for (char[] bChars: b) {
                assertArrayEquals(aChars, bChars);
            }
        }
    }
}
