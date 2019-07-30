package tests;

import model.Map;
import model.MapObjects.Examinable;
import model.MapObjects.Tile;
import model.MapObjects.creatures.Ennui;
import model.MapObjects.items.Note;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileTests extends TestMapDataAndMethods {
    private static final int T_Y_1 = 0;
    private static final int T_X_1 = 0;
    private static final int T_Y_2 = 3;
    private static final int T_X_2 = 4;
    private Tile tile;

    @BeforeEach
    void setup() {
        initTestMaps();
        tile = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, new ArrayList<Examinable>());
    }

    @Test
    void makeEmptyFloorTile() {
        tile = new Tile(map1, T_Y_1, T_X_1, Map.FLOOR, new ArrayList<Examinable>());
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_1, tile.getY());
        assertEquals(T_X_1, tile.getX());
        assertEquals(Map.FLOOR, tile.getCurrChar());
        assertTrue(new HashMap<String, Examinable>().equals(tile.getCurrInteractables()));
        assertTrue(tile.isWalkable());
    }

    @Test
    void makeWallTile() {
        tile = new Tile(map1, T_Y_1, T_X_1, Map.WALL, new ArrayList<Examinable>());
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_1, tile.getY());
        assertEquals(T_X_1, tile.getX());
        assertEquals(Map.WALL, tile.getCurrChar());
        assertTrue(new HashMap<String, Examinable>().equals(tile.getCurrInteractables()));
        assertFalse(tile.isWalkable());
    }

    @Test
    void makeFloorWithInteractable() {
        List<Examinable> tempInterList = new ArrayList<Examinable>(
                Arrays.asList(new Note(1, 0), new Ennui(1, 1))
        );
        tile = new Tile(map1, T_Y_2, T_X_2, Map.FLOOR, tempInterList);
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_2, tile.getY());
        assertEquals(T_X_2, tile.getX());
        assertEquals(Map.FLOOR, tile.getCurrChar());
        assertTrue(tempInterList.containsAll(tile.getCurrInteractables().values()));
        assertTrue(tile.isWalkable());
    }

    @Test
    void testGetDisplayCharAndRevealed() {
        assertEquals(Map.FOG, tile.getDisplayChar());
        tile.revealTile();
        assertEquals(Map.FLOOR, tile.getDisplayChar());
    }

}
