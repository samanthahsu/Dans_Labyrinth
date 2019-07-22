import model.Avatar;
import model.Interactable;
import model.Map;
import model.Tile;
import model.creatures.Exo;
import model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MapTest extends TestMapSuite {

    private static final int C_Y_1 = 1;
    private static final int C_X_1 = 3;

    private static final int C_Y_2 = 1;
    private static final int C_X_2 = 1;


    @BeforeEach
    void Setup(){
        initMaps();
/*
        interactableArrayList = new ArrayList<>();
        interactableArrayList.add(new Exo(C_Y_1, C_X_1));
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                new ArrayList<Interactable>(), );
*/
    }

    @Test
    void testConstructorEmptyLists(){
        assertEquals(TEST_HEIGHT_1, map1.getHeight());
        assertEquals(TEST_WIDTH_1, map1.getWidth());
        assertEquals(WIN_Y_1, map1.getWinY());
        assertEquals(WIN_X_1, map1.getWinX());
        assertTrue(ava1.equals(new Avatar(TEST_START_Y_1,
                TEST_START_X_1,
                new ArrayList<Item>(),map1)));
        assertTrue(tileMatrix1.equals(new ArrayList<ArrayList<Tile>>())); //todo
    }
    @Test
    void testConstructorNonEmptyList(){
        interactableArrayList.remove(0);
        interactableArrayList.add(new Exo(C_Y_2, C_X_2));
        map1 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                new ArrayList<Interactable>(), );
        assertEquals(TEST_HEIGHT_2, map1.getHeight());
        assertEquals(TEST_WIDTH_2, map1.getWidth());

        assertEqualsStrCharMtrx(
            "#@#" +
                     "@*@" +
                     "# #", map1,
                TEST_HEIGHT_2, TEST_WIDTH_2);
        String expMapStr =
                "@@@"+
                "@ @"+
                "@ @";
        char[][] expMap = strToTestCharMtrx(expMapStr, TEST_HEIGHT_2, TEST_WIDTH_2);
        assertEqualsCharMtrx(expMap, map1.getMap(), TEST_HEIGHT_2, TEST_WIDTH_2);
    }

    @Test
    void testConstructorException(){}

    @Test
    void isIndexValidTest(){
        assertFalse(map1.isIndexValid(-1, 0));
        assertFalse(map1.isIndexValid(TEST_HEIGHT_1, 0));
        assertFalse(map1.isIndexValid(0, -1));
        assertFalse(map1.isIndexValid(0, TEST_WIDTH_1));

        assertTrue(map1.isIndexValid(2, 3));
    }

    @Test
    void isTileFloorTest(){
        assertFalse(map1.isTileWalkable(0, 1));
        assertTrue(map1.isTileWalkable(2, 2));
    }

    @Test
    void updateTileDispOneTest(){
        map1.updateTileDisp(0, 0, 'G');
        assertEqualsStrCharMtrx(
        "G#####" +
                "#@####" +
                "@* ###" +
                "#@####", map1, TEST_HEIGHT_1, TEST_WIDTH_1);
    }

    @Test
    void updateTileDispTwoSameTest(){
        map1.updateTileDisp(0, 0, 'G');
        map1.updateTileDisp(0, 0, ' ');
        assertEqualsStrCharMtrx(
                " #####" +
                        "#@####" +
                        "@* ###" +
                        "#@####", map1, TEST_HEIGHT_1, TEST_WIDTH_1);
    }

    @Test
    void updateTileDispTwoDiffTest(){
        map1.updateTileDisp(1, 0, 'G');
        map1.updateTileDisp(2, 3, 'X');
        assertEqualsStrCharMtrx(
                "######" +
                        "G@####" +
                        "@* X##" +
                        "#@####", map1, TEST_HEIGHT_1, TEST_WIDTH_1);
    }



    @Test
    void revealSurroundingsMidTest(){
        map1.revealSurroundings(2, 2);
        assertEqualsStrCharMtrx(
        "######"+
                "#@ ###"+
                "@   ##"+
                "#@@###", map1, TEST_HEIGHT_1, TEST_WIDTH_1);
    }
    @Test
    void revealSurroundingsNWTest() {
        map1.revealSurroundings(0, 0);
        assertEqualsStrCharMtrx(
            "#@####" +
                    "@@####" +
                    "@* ###" +
                    "#@####", map1, TEST_HEIGHT_1, TEST_WIDTH_1);

    }
    @Test
    void revealSurroundingsNETest(){
        map1.revealSurroundings(0, TEST_WIDTH_1 -1);
        assertEqualsStrCharMtrx(
            "####@#" +
                    "#@###@" +
                    "@* ###" +
                    "#@####", map1, TEST_HEIGHT_1, TEST_WIDTH_1);
    }
    @Test
    void revealSurroundingsSWTest(){
        map1.revealSurroundings(TEST_HEIGHT_1 -1, 0);
        assertEqualsStrCharMtrx(
                "######" +
                        "#@####" +
                        "@* ###" +
                        "#@####", map1, TEST_HEIGHT_1, TEST_WIDTH_1);
    }
    @Test
    void revealSurroundingsSETest(){
        map1.revealSurroundings(TEST_HEIGHT_1 -1, TEST_WIDTH_1 -1);
        assertEqualsStrCharMtrx(
            "######" +
                    "#@####" +
                    "@* ##@" +
                    "#@##@#", map1, TEST_HEIGHT_1, TEST_WIDTH_1);
    }

    @Test
    void isWinTest() {
        assertFalse(map1.isWin());
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, WIN_Y_1, WIN_X_1,
                new ArrayList<Interactable>(), );
        assertTrue(map1.isWin());
    }

    @Test
    void printDisplayMapTest(){
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        System.setErr(new PrintStream(err));

        map1.printDisplayMap();
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

        map1.revealSurroundings(2, 3);
        map1.printDisplayMap();
        assertEquals("######\r\n" +
                            "#@#@##\r\n" +
                            "@* #@#\r\n" +
                            "#@#@##\r\n", output.toString());
    }

    @Test
    void EqualsTest() {
        assertFalse(map1.equals(map3));
        map1 = map3;
        assertTrue(map1.equals(map3));
//        todo also make one where they point to actual different objects, but same params

    }
}
