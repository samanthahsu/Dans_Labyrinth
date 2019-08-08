package tests;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.exceptions.MapException;
import model.exceptions.MismatchedMapSizeException;
import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.features.MossyGate;
import model.mapobjects.items.Item;
import model.mapobjects.items.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.PrintObserver;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MapTests extends TestMapDataAndMethods {


    @BeforeEach
    void Setup(){
        initTestMaps();
    }

    @Test
    void testConstructor(){
        assertEquals(TEST_HEIGHT_1, map1.getHeight());
        assertEquals(TEST_WIDTH_1, map1.getWidth());
        assertEquals(WIN_Y_1, map1.getWinY());
        assertEquals(WIN_X_1, map1.getWinX());
        assertTrue(ava1.equals(new Avatar(TEST_START_Y_1,
                TEST_START_X_1, itemList1, map1)));
        assertTrue(map1.getAllExaminables().isEmpty());
    }

    @Test
    void testConstructorException(){
        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, interList1, TEST_MAP_1);
        } catch (MapException e) {
            fail("e thrown");
        }

        try {
            map1 = new Map(TEST_HEIGHT_1 + 1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, new ArrayList<Examinable>(), TEST_MAP_1);
            fail("no e thrown");
        } catch (MismatchedMapSizeException e) {
//            expected
        } catch (EdgeOfMapException e) {
            fail("wrong e thrown");
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
    void isTileWalkableTest(){
        try {
            assertFalse(map1.isTileWalkable(0, 1));
        } catch (EdgeOfMapException e) {
            fail("e thrown");
        }

        try {
            assertTrue(map1.isTileWalkable(2, 2));
        } catch (EdgeOfMapException e) {
            fail("e thrown");
        }

        try {
            assertTrue(map1.isTileWalkable(-1, 3));
            fail("no e thrown");
        } catch (EdgeOfMapException e) {
//            expected
        }
    }

    @Test
    void updateTileDispOneTest(){
        try {
            map1.updateTileDisplay(0, 0, 'G');
        } catch (EdgeOfMapException e) {
            fail("e thrown");
        }
        assertEquals('G', tileMatrix1.get(0).get(0).getCurrChar());
    }

    @Test
    void updateTileDispTwoSameTest(){
        try {
            map1.updateTileDisplay(0, 0, 'G');
            map1.updateTileDisplay(0, 0, ' ');
        } catch (EdgeOfMapException e) {
            System.out.println("e thrown");
        }
        assertEquals(' ', tileMatrix1.get(0).get(0).getCurrChar());
    }

    @Test
    void updateTileDispTwoDiffTest(){
        try {
            map1.updateTileDisplay(1, 0, 'G');
            map1.updateTileDisplay(2, 3, 'X');
        } catch (EdgeOfMapException e) {
            System.out.println("e thrown");
        }
        assertEquals('G', tileMatrix1.get(1).get(0).getCurrChar());
        assertEquals('X', tileMatrix1.get(2).get(3).getCurrChar());
    }

    @Test
    void testUpdateTileDisplayException() {
        try {
            map1.updateTileDisplay(TEST_HEIGHT_1 / 2, TEST_WIDTH_1, 'I');
            fail("no e thrown");
        } catch (EdgeOfMapException e) {
//            expected
        }
        try {
            map1.updateTileDisplay(TEST_HEIGHT_1, TEST_WIDTH_1 / 2, 'I');
            fail("no e thrown");
        } catch (EdgeOfMapException e) {
//            expected
        }
        try {
            map1.updateTileDisplay(TEST_HEIGHT_1 / 2, -1, 'I');
            fail("no e thrown");
        } catch (EdgeOfMapException e) {
//            expected
        }
        try {
            map1.updateTileDisplay(-1, TEST_WIDTH_1 / 2, 'I');
            fail("no e thrown");
        } catch (EdgeOfMapException e) {
//            expected
        }
    }

    @Test
    void revealSurroundingsMidTest(){
        int y = 2;
        int x = 2;
        map1.revealSurroundings(y, x);
        assertTrue(tileMatrix1.get(y).get(x).isRevealed()
        && tileMatrix1.get(y - 1).get(x).isRevealed()
        && tileMatrix1.get(y + 1).get(x).isRevealed()
        && tileMatrix1.get(y).get(x - 1).isRevealed()
        && tileMatrix1.get(y).get(x + 1).isRevealed());
    }
    @Test
    void revealSurroundingsNWTest() {
        int y = 0;
        int x = 0;
        map1.revealSurroundings(0, 0);
        assertTrue(tileMatrix1.get(y).get(x).isRevealed()
                && tileMatrix1.get(y + 1).get(x).isRevealed()
                && tileMatrix1.get(y).get(x + 1).isRevealed());

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
        try {
            map1 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, WIN_Y_2 - 1, WIN_X_2,
                    new ArrayList<Item>(), new ArrayList<Examinable>(), TEST_MAP_2);
            assertFalse(map1.isWin());

            map1 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, WIN_Y_2, WIN_X_2 + 1,
                    new ArrayList<Item>(), new ArrayList<Examinable>(), TEST_MAP_2);
            assertFalse(map1.isWin());

        } catch (MismatchedMapSizeException | EdgeOfMapException e) {
            fail("threw e");
        }

    }

    @Test
    void EqualsTest() {
        assertFalse(map1.equals(map3));

        map1 = map3;
        assertTrue(map1.equals(map3));

        try {
            map1 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                    new ArrayList<Item>(), new ArrayList<Examinable>(), TEST_MAP_2);
            assertTrue(map1.equals(map2));
            assertFalse(map1.equals(0));

            map1 = new Map(TEST_HEIGHT_2 - 1, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                    new ArrayList<Item>(), new ArrayList<Examinable>(), "@@@@@@");
            assertFalse(map1.equals(map2));

            map1 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2 - 1, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                    new ArrayList<Item>(), new ArrayList<Examinable>(), "@@@@@@");
            assertFalse(map1.equals(map2));

            map1 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                    itemList1, new ArrayList<Examinable>(), TEST_MAP_2);
            assertFalse(map1.equals(map2));

            map1 = new Map(TEST_HEIGHT_2, TEST_WIDTH_2, WIN_Y_2, WIN_X_2, TEST_START_Y_2, TEST_START_X_2,
                    new ArrayList<Item>(), interListC, TEST_MAP_2);
            assertFalse(map1.equals(map2));

        } catch (MapException e) {
            fail("threw MapException");
        }
    }

    @Test
    void testObservers() {
        interList1 = new ArrayList<>(
                Arrays.asList(new Ennui(1, 1))
        );
        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, interList1, TEST_MAP_1);
        } catch (MismatchedMapSizeException | EdgeOfMapException e) {
            fail("threw e");
        }
        map1.addObservers(new PrintObserver() {
            @Override
            public void update(String message) {
            }
        });
    }

    @Test
    void testmoveExaminableOnTiles() {
        Examinable ennui = new Ennui(-1, -1);
        map1.moveExaminableOnTiles(ennui, 3, 3);
        map1.moveExaminableOnTiles(ennui, 2, 3);
        map1.moveExaminableOnTiles(ennui, 2, 2);
        map1.moveExaminableOnTiles(ennui, 2, 2);
    }

    @Test
    void addExaminableBadIndex() {
        map1.addExaminable(new Note(-1, -1), -1, -1);
    }

    @Test
    void nextStateItemsInList() {
        interList1 = new ArrayList<>(
                Arrays.asList(new Note(1, 1), new MossyGate(1, 1, 1, 2),
                        new Ennui(2, 2))
        );
        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, interList1, TEST_MAP_1);
        } catch (MismatchedMapSizeException | EdgeOfMapException e) {
            fail("threw e");
        }
        map1.nextState();

    }

}
