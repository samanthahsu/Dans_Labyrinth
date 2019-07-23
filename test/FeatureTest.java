import exceptions.edgeOfMapException;
import exceptions.mapIsNullException;
import exceptions.mismatchedMapSizeException;
import model.Interactables.features.MossyGate;
import model.Interactables.items.RustyKey;
import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FeatureTest extends TestMapSuite {
    MossyGate mgC;

    @BeforeEach
    void setup() throws mismatchedMapSizeException, edgeOfMapException, mapIsNullException {
        initTestMaps();
        //        uses mapC
        mgC = new MossyGate(1, 6, 2, 6);
        interListC.add(mgC);
        tileListC = buildTileArray(TEST_HEIGHT_C, TEST_WIDTH_C, TEST_MAP_C, interListC);
        itemListC.add(new RustyKey());
        mapCreature = new Map(TEST_HEIGHT_C, TEST_WIDTH_C, WIN_Y_C, WIN_X_C, TEST_START_Y_C, TEST_START_X_C,
                itemListC, tileListC);
        wr.setMapsInTiles(mapCreature);

    }

    @Test //todo build into game
    void testMossyConstructor() {
        mgC.passiveAction();
        assertEquals("mossy gate", mgC.getName());
        assertEquals(1, mgC.getYpos());
        assertEquals(6, mgC.getXpos());
        assertFalse(mapCreature.getTileMatrix().get(2).get(6).isWalkable());
    }
}
