import exceptions.mismatchedMapSizeException;
import model.Avatar;
import model.Map;
import model.Tile;
import model.items.Item;
import model.items.PizzaBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MapTest extends TestMapSuite {

    private static final int C_Y_1 = 1;
    private static final int C_X_1 = 3;

    private static final int C_Y_2 = 1;
    private static final int C_X_2 = 1;


    @BeforeEach
    void Setup(){
        initTestMaps();
/*
        interactableArrayList = new ArrayList<>();
        interactableArrayList.add(new Exo(C_Y_1, C_X_1));
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                new ArrayList<Interactable>(), );
*/
    }

    @Test
    void testConstructor(){
        assertEquals(TEST_HEIGHT_1, map1.getHeight());
        assertEquals(TEST_WIDTH_1, map1.getWidth());
        assertEquals(WIN_Y_1, map1.getWinY());
        assertEquals(WIN_X_1, map1.getWinX());
        assertTrue(ava1.equals(new Avatar(TEST_START_Y_1,
                TEST_START_X_1,
                new ArrayList<Item>(
                        Arrays.asList(new PizzaBox())
                ), map1)));
        ArrayList<Tile> expTileList = buildTileArray(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, interList1);
        ArrayList<ArrayList<Tile>> expTileMatrix = initTileMatrix(expTileList,
                TEST_HEIGHT_1, TEST_WIDTH_1);
        assertTrue(map1.tileMatrixEquals(tileMatrix1, tileMatrix1, TEST_HEIGHT_1, TEST_WIDTH_1)); //todo;
    }

    @Test
    void testConstructorException(){
        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, tileList1);
        } catch (mismatchedMapSizeException e) {
            fail("mismatchedMapSizeException thrown");
        }

        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, new ArrayList<Tile>());
            fail("no e thrown");
        } catch (mismatchedMapSizeException e) {
        }

    }

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
        map1.updateTileDisplay(0, 0, 'G');
        assertEquals('G', tileMatrix1.get(0).get(0).getDisplayChar());
    }

    @Test
    void updateTileDispTwoSameTest(){
        map1.updateTileDisplay(0, 0, 'G');
        map1.updateTileDisplay(0, 0, ' ');
        assertEquals(' ', tileMatrix1.get(0).get(0).getDisplayChar());
    }

    @Test
    void updateTileDispTwoDiffTest(){
        map1.updateTileDisplay(1, 0, 'G');
        map1.updateTileDisplay(2, 3, 'X');
        assertEquals('G', tileMatrix1.get(1).get(0).getDisplayChar());
        assertEquals('X', tileMatrix1.get(2).get(3).getDisplayChar());
    }

    @Test
    void revealSurroundingsMidTest(){
        int y = 2;
        int x = 2;
        map1.revealSurroundings(y, x);
        assertTrue(tileMatrix1.get(y).get(x).isRevealed()
        && tileMatrix1.get(y-1).get(x).isRevealed()
        && tileMatrix1.get(y+1).get(x).isRevealed()
        && tileMatrix1.get(y).get(x-1).isRevealed()
        && tileMatrix1.get(y).get(x+1).isRevealed());
    }
    @Test
    void revealSurroundingsNWTest() {
        int y = 0;
        int x = 0;
        map1.revealSurroundings(0, 0);
        assertTrue(tileMatrix1.get(y).get(x).isRevealed()
                && tileMatrix1.get(y+1).get(x).isRevealed()
                && tileMatrix1.get(y).get(x+1).isRevealed());

    }
    @Test
    void revealSurroundingsNETest(){
        int y = 0;
        int x = TEST_WIDTH_1 -1;
        map1.revealSurroundings(y, x);
        assertTrue(tileMatrix1.get(y).get(x).isRevealed()
                && tileMatrix1.get(y+1).get(x).isRevealed()
                && tileMatrix1.get(y).get(x-1).isRevealed());
    }
    @Test
    void revealSurroundingsSWTest(){
        int y = TEST_HEIGHT_1 - 1;
        int x = 0;

        map1.revealSurroundings(y, x);
        assertTrue(tileMatrix1.get(y).get(x).isRevealed()
                && tileMatrix1.get(y-1).get(x).isRevealed()
                && tileMatrix1.get(y).get(x+1).isRevealed());
    }
    @Test
    void revealSurroundingsSETest(){
        int y = TEST_HEIGHT_1 - 1;
        int x = TEST_WIDTH_1 - 1;

        map1.revealSurroundings(y , x);
        assertTrue(tileMatrix1.get(y).get(x).isRevealed()
                && tileMatrix1.get(y-1).get(x).isRevealed()
                && tileMatrix1.get(y).get(x-1).isRevealed());
    }

    @Test
    void isWinTest() {
        assertFalse(map1.isWin());
        map1 = map3;
        assertTrue(map1.isWin());
    }


    @Test
    void EqualsTest() {
        assertFalse(map1.equals(map3));
        map1 = map3;
        assertTrue(map1.equals(map3));
//        todo also make one where they point to actual different objects, but same params

    }
}
