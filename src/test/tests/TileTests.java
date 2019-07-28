package tests;

import model.Interactables.Interactable;
import model.Interactables.creatures.Ennui;
import model.Interactables.items.Note;
import model.Map;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileTests extends TestMapDataAndMethods {
    static final int T_Y_1 = 0;
    static final int T_X_1 = 0;
    static final int T_Y_2 = 3;
    static final int T_X_2 = 4;
    Tile tile;

    @BeforeEach
    void setup() {
        initTestMaps();
        tile = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, new ArrayList<Interactable>());
    }

    @Test
    void makeEmptyFloorTile() {
        tile = new Tile(map1, T_Y_1, T_X_1, Map.FLOOR, new ArrayList<Interactable>());
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_1, tile.getYpos());
        assertEquals(T_X_1, tile.getXpos());
        assertEquals(Map.FLOOR, tile.getCurrChar());
        assertTrue(new HashMap<String, Interactable>().equals(tile.getCurrInteractables()));
        assertTrue(tile.isWalkable());
    }

    @Test
    void makeWallTile() {
        tile = new Tile(map1, T_Y_1, T_X_1, Map.WALL, new ArrayList<Interactable>());
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_1, tile.getYpos());
        assertEquals(T_X_1, tile.getXpos());
        assertEquals(Map.WALL, tile.getCurrChar());
        assertTrue(new HashMap<String, Interactable>().equals(tile.getCurrInteractables()));
        assertFalse(tile.isWalkable());
    }

    @Test
    void makeFloorWithInteractable() {
        List<Interactable> tempInterList = new ArrayList<Interactable>(
                Arrays.asList(new Note(1, 0), new Ennui(1, 1))
        );
        tile = new Tile(map1, T_Y_2, T_X_2, Map.FLOOR, tempInterList);
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_2, tile.getYpos());
        assertEquals(T_X_2, tile.getXpos());
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
