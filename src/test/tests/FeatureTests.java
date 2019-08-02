package tests;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.exceptions.MismatchedMapSizeException;
import model.mapobjects.features.BloodFish;
import model.mapobjects.features.FourStoneGate;
import model.mapobjects.features.MossyGate;
import model.mapobjects.items.RustyKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeatureTests extends TestMapDataAndMethods {
    public static final int YBLOCK = 2;
    public static final int XBLOCK = 6;
    private MossyGate mgC;
    private FourStoneGate fourStoneGate;
    private BloodFish bloodFish;

    @BeforeEach
    void setup() throws MismatchedMapSizeException, EdgeOfMapException {
        initTestMaps();
        //        uses mapC
        mgC = new MossyGate(1, 6, YBLOCK, XBLOCK);
        interListC.add(mgC);
//        tileListC = buildTileArray(TEST_HEIGHT_C, TEST_WIDTH_C, TEST_MAP_C, interListC);
        itemListC.add(new RustyKey());
        mapCreature = new Map(TEST_HEIGHT_C, TEST_WIDTH_C, WIN_Y_C, WIN_X_C, TEST_START_Y_C, TEST_START_X_C,
                itemListC, interListC, TEST_MAP_C);
//        wr.setMapsInTiles(mapCreature);

    }

    @Test
    void testMossyConstructor() {
        mgC.doPassiveActions();
        assertEquals("mossy gate", mgC.getName());
        assertEquals(1, mgC.getYc());
        assertEquals(6, mgC.getXc());
        assertFalse(mapCreature.getTileMatrix().get(2).get(6).isWalkable());
    }

    @Test
    void testOpen() {
        mgC.open();
        assertTrue(mgC.isOpened());
        assertTrue(tileMatrixC.get(YBLOCK).get(XBLOCK).isWalkable());
    }

    @Test
    void testExamine() {
        mgC.examine("hello");
    }

    @Test
    void testBloodFishTODO() {
    }
}
