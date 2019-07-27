import model.Interactables.Interactable;
import model.Map;
import model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TileTests extends TestMapDataAndMethods {
    static final int T_Y_1 = 0;
    static final int T_X_1 = 0;
    static final int T_Y_2 = 3;
    static final int T_X_2 = 4;
    Tile tile;

    @BeforeEach
    void setup() {
        initTestMaps();
        tile = null;
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



}
