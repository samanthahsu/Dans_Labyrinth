package tests;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.exceptions.MismatchedMapSizeException;
import model.mapobjects.features.*;
import model.mapobjects.items.PizzaBox;
import model.mapobjects.items.RustyKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FeatureTests extends TestMapDataAndMethods {

    private static final int GATEY = 1;
    private static final int GATEX = 6;
    private static final int YBLOCK = 2;
    private static final int XBLOCK = 6;
    private MossyGate mossyGate;
    private FourStoneGate fourStoneGate;
    private BloodFish bloodFish;
    private Brick brick;
    private Pan pan;
    private LastGate lastGate;

    @BeforeEach
    void setup() throws MismatchedMapSizeException, EdgeOfMapException {
        initTestMaps();
        //        uses mapC
        mossyGate = new MossyGate(GATEY, GATEX, YBLOCK, XBLOCK);
        fourStoneGate = new FourStoneGate(1, 5, 1, 6);
        bloodFish = new BloodFish(1, 5);
        brick = new Brick(1, 4);
        pan = new Pan(1, 1);
        lastGate = new LastGate(TEST_START_Y_C, TEST_START_X_C, YBLOCK, XBLOCK);
        interListC.addAll(Arrays.asList(mossyGate, fourStoneGate, bloodFish, brick,
                pan, lastGate));
        itemListC.add(new RustyKey());
        mapCreature = new Map(TEST_HEIGHT_C, TEST_WIDTH_C, WIN_Y_C, WIN_X_C, TEST_START_Y_C, TEST_START_X_C,
                itemListC, interListC, TEST_MAP_C);
    }

    @Test
    void testMossyConstructor() {
        mossyGate.doPassiveActions();
        assertEquals(MossyGate.NAME, mossyGate.getName());
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
        assertFalse(bloodFish.examine("tadsfring"));
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
        fourStoneGate.doPassiveActions();
        assertFalse(fourStoneGate.examine("down"));
    }

    @Test
    void testFourStoneGateBadState() {
        fourStoneGate.setState(900);
        fourStoneGate.examine("hello");
    }

    @Test
    void testBrick() {
        assertTrue(brick.examine("press button"));
        assertFalse(brick.isSolved());
        assertFalse(brick.examine("halalalala"));
        assertFalse(brick.isSolved());
        assertFalse(brick.examine("10000"));
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

    @Test
    void testLastGate() {
        assertFalse(lastGate.examine("llaalalalal"));
        assertTrue(lastGate.examine("use " + PizzaBox.NAME));
        assertFalse(mapCreature.getAva().getCurrItems().containsKey(PizzaBox.NAME));
        assertTrue(lastGate.isOpened());
        assertFalse(lastGate.examine("use " + PizzaBox.NAME));
    }

    @Test
    void testLastGateNotOnTile() {
        mapCreature.getAva().moveAva("e");
        assertTrue(lastGate.examine("use " + PizzaBox.NAME));
        assertTrue(mapCreature.getAva().getCurrItems().containsKey(PizzaBox.NAME));
        assertFalse(lastGate.isOpened());
    }

    @Test
    void testMossyGateCallKey() {
        mossyGate = new MossyGate(TEST_START_Y_C, TEST_START_X_C, 3, 4);
        mapCreature.addExaminable(mossyGate, TEST_START_Y_C, TEST_START_X_C);
        assertTrue(mossyGate.examine("use " + RustyKey.NAME));
        assertTrue(mossyGate.isOpened());
        assertFalse(mossyGate.examine("use " + RustyKey.NAME));
    }

    @Test
    void testTakeBone() {
        for (int i = 0; i < 8; i++) {
            assertTrue(pan.examine("take bone"));
        }
        assertEquals(Pan.FIRST_NAME, pan.getName());
        assertTrue(pan.examine("examine uniform"));
        assertEquals(Pan.ACTUAL_NAME, pan.getName());
    }

    @Test
    void testInBrickPickUpPoptart() {
        assertTrue(brick.examine("take poptart"));
    }
}
