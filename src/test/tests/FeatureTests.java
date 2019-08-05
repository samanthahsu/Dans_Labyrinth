package tests;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.exceptions.MismatchedMapSizeException;
import model.mapobjects.features.*;
import model.mapobjects.items.RustyKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FeatureTests extends TestMapDataAndMethods {
    public static final int YBLOCK = 2;
    public static final int XBLOCK = 6;
    private MossyGate mossyGate;
    private FourStoneGate fourStoneGate;
    private BloodFish bloodFish;
    private Brick brick;
    private Pan pan;

    @BeforeEach
    void setup() throws MismatchedMapSizeException, EdgeOfMapException {
        initTestMaps();
        //        uses mapC
        mossyGate = new MossyGate(1, 6, YBLOCK, XBLOCK);
        fourStoneGate = new FourStoneGate(1, 5, 1, 6);
        bloodFish = new BloodFish(1, 5);
        brick = new Brick(1, 4);
        pan = new Pan(1, 1);
        interListC.addAll(Arrays.asList(mossyGate, fourStoneGate, bloodFish, brick,
                pan));
        itemListC.add(new RustyKey());
        mapCreature = new Map(TEST_HEIGHT_C, TEST_WIDTH_C, WIN_Y_C, WIN_X_C, TEST_START_Y_C, TEST_START_X_C,
                itemListC, interListC, TEST_MAP_C);
    }

    @Test
    void testMossyConstructor() {
        mossyGate.doPassiveActions();
        assertEquals("mossy gate", mossyGate.getName());
        assertEquals(1, mossyGate.getYc());
        assertEquals(6, mossyGate.getXc());
        assertFalse(mapCreature.getTileMatrix().get(2).get(6).isWalkable());
    }

    @Test
    void testOpen() {
        mossyGate.open();
        assertTrue(mossyGate.isOpened());
        assertTrue(tileMatrixC.get(YBLOCK).get(XBLOCK).isWalkable());
    }

    @Test
    void testExamine() {
        mossyGate.examine("hello");
    }

    @Test
    void testBloodFish() {
        assertTrue(bloodFish.examine("this is a red herring"));
        assertFalse(bloodFish.examine("this is a red herring"));
    }

    @Test
    void testFourStoneGateExamineFail() {
        fourStoneGate.examine("right");
        fourStoneGate.examine("left");
        fourStoneGate.examine("up");
        fourStoneGate.examine("right");
        assertFalse(fourStoneGate.examine("wrong input"));
        assertFalse(fourStoneGate.isOpened());
    }

    @Test
    void testFourStoneGateExaminePass() {
        fourStoneGate.examine("left");
        fourStoneGate.examine("up");
        fourStoneGate.examine("right");
        fourStoneGate.examine("down");
        assertTrue(fourStoneGate.isOpened());
    }

    @Test
    void testBrick() {
        assertTrue(brick.examine("press button"));
        assertFalse(brick.isSolved());
        assertFalse(brick.examine("halalalala"));
        assertFalse(brick.isSolved());
        assertTrue(brick.examine("enter 42"));
        assertTrue(brick.isSolved());
        assertTrue(brick.examine("enter 42"));
    }

    @Test
    void testBones() {
        assertTrue(pan.examine("examine uniform"));
        assertFalse(pan.examine("not a statement"));
    }

    @Test
    void testFeaturePassiveAction() {
        Feature b1 = new Brick(1, 1);
        b1.doPassiveActions();
    }
}
