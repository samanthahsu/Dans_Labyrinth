package tests;

import model.Map;
import model.mapobjects.Examinable;
import model.mapobjects.Sound;
import model.mapobjects.Tile;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.items.Note;
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
    public static final String SOURCE_NAME = "test source";
    private Tile tile;
    Sound sound;

    @BeforeEach
    void setup() {
        initTestMaps();
        tile = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, new ArrayList<Examinable>());
        sound = new Sound(map1, T_Y_1, T_X_1, SOURCE_NAME, "");
    }

    @Test
    void makeEmptyFloorTile() {
        tile = new Tile(map1, T_Y_1, T_X_1, Map.FLOOR, new ArrayList<Examinable>());
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_1, tile.getYc());
        assertEquals(T_X_1, tile.getXc());
        assertEquals(Map.FLOOR, tile.getCurrChar());
        assertTrue(new HashMap<String, Examinable>().equals(tile.getCurrExaminables()));
        assertTrue(tile.isWalkable());
    }

    @Test
    void makeWallTile() {
        tile = new Tile(map1, T_Y_1, T_X_1, Map.WALL, new ArrayList<Examinable>());
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_1, tile.getYc());
        assertEquals(T_X_1, tile.getXc());
        assertEquals(Map.WALL, tile.getCurrChar());
        assertTrue(new HashMap<String, Examinable>().equals(tile.getCurrExaminables()));
        assertFalse(tile.isWalkable());
    }

    @Test
    void makeFloorWithInteractable() {
        List<Examinable> tempInterList = new ArrayList<Examinable>(
                Arrays.asList(new Note(1, 0), new Ennui(1, 1))
        );
        tile = new Tile(map1, T_Y_2, T_X_2, Map.FLOOR, tempInterList);
        assertTrue(map1.equals(tile.getMap()));
        assertEquals(T_Y_2, tile.getYc());
        assertEquals(T_X_2, tile.getXc());
        assertEquals(Map.FLOOR, tile.getCurrChar());
        assertTrue(tempInterList.containsAll(tile.getCurrExaminables().values()));
        assertTrue(tile.isWalkable());
    }

    @Test
    void testGetDisplayCharAndRevealed() {
        assertEquals(Map.FOG, tile.getDisplayChar());
        tile.revealTile();
        assertEquals(Map.FLOOR, tile.getDisplayChar());
    }

    @Test
    void testAddAndRemoveSounds() {
        Tile tile1 = tileMatrix1.get(T_Y_1).get(T_X_1);
        tile1.addSound(sound);
        assertTrue(tile1.getTileSounds().contains(sound));
        tile1.removeSound(SOURCE_NAME);
        assertFalse(tile1.getTileSounds().contains(sound));
    }

    @Test
    void removeSoundIsntThere() {
        tile = new Tile(map1, 0, 0, '@', new ArrayList<Examinable>());
        tile.removeSound(sound.getSourceName());
        assertTrue(tile.getTileSounds().isEmpty());

        Sound ridikulus = new Sound(null, -1, -1, "ridikulus", "");
        tile.addSound(ridikulus);
        tile.removeSound(sound.getSourceName());
        assertTrue(tile.getTileSounds().contains(ridikulus));
    }

    @Test
    void testEquals() {
        assertTrue(tile.equals(tile));
        assertFalse(tile.equals(0));
        tile = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, new ArrayList<Examinable>());

        Tile tile2;
        tile2 = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, new ArrayList<Examinable>());
        assertTrue(tile2.equals(tile));

        tile2 = new Tile(map2, -1, T_X_2, Map.FLOOR, new ArrayList<Examinable>());
        assertFalse(tile2.equals(tile));
        tile2 = new Tile(map2, T_Y_2, -1, Map.FLOOR, new ArrayList<Examinable>());
        assertFalse(tile2.equals(tile));
        tile2 = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, interListC);
        assertFalse(tile2.equals(tile));
        tile2 = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, new ArrayList<Examinable>());
        tile2.setWalkable(false);
        assertFalse(tile2.equals(tile));
        tile2 = new Tile(map2, T_Y_2, T_X_2, Map.WALL, new ArrayList<Examinable>());
        assertFalse(tile2.equals(tile));
        tile2 = new Tile(map2, T_Y_2, T_X_2, Map.FLOOR, new ArrayList<Examinable>());
        tile2.setRevealed(true);
        assertFalse(tile2.equals(tile));
    }

}
