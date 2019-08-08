package tests;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.exceptions.MismatchedMapSizeException;
import model.mapobjects.Examinable;
import model.mapobjects.Sound;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.features.MossyGate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CreatureTests extends TestMapDataAndMethods {

    @BeforeEach
    void Setup(){
        initTestMaps();
    }

    @Test
    void ConstructorTest(){
        assertEquals(ENNUI_CAPTURE_START_Y, testEnnui.getYc());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getXc());
        assertEquals(Ennui.NAME, testEnnui.getName());
        assertEquals(Examinable.TYPE_CREATURE, testEnnui.getTypeId());
    }

    @Test
    void testDoPassiveActionsOnceAvaClose() {
        testEnnui.doPassiveActions();
        assertEquals(ENNUI_CAPTURE_START_Y - 1, testEnnui.getYc());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getXc());

        int newY = ENNUI_CAPTURE_START_Y - 1; //1
        int newX = ENNUI_CAPTURE_START_X; //1

//        todo careful checking if index valid
        Sound ennuiSound = new Sound(map1, -1, -1, Ennui.NAME, "");
        assertTrue(tileMatrixC.get(newY - 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY + 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX - 1).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX + 1).getTileSounds().contains(ennuiSound));

        testEnnui.doPassiveActions();
        newY += 1;
        newX += 0;

        assertEquals(newY, testEnnui.getYc());
        assertEquals(newX, testEnnui.getXc());

        assertTrue(tileMatrixC.get(newY - 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY + 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX - 1).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX + 1).getTileSounds().contains(ennuiSound));

        testEnnui.setYc(3);
        testEnnui.setXc(7);
        testEnnui.doPassiveActions();
        newY = 3;
        newX = 8;

        assertEquals(newY, testEnnui.getYc());
        assertEquals(newX, testEnnui.getXc());
        assertTrue(tileMatrixC.get(newY - 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY + 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX - 1).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX + 1).getTileSounds().contains(ennuiSound));
    }

    @Test
    void testMoveWest() {
        interListC = new ArrayList<>();
        Ennui e1 = new Ennui(3, 4);
        interListC.add(e1);
        try {
            mapCreature = new Map(TEST_HEIGHT_C, TEST_WIDTH_C, WIN_Y_C, WIN_X_C, TEST_START_Y_C, TEST_START_X_C,
                    itemListC, interListC, TEST_MAP_C);
        } catch (MismatchedMapSizeException | EdgeOfMapException e) {
            e.printStackTrace();
        }
        tileMatrixC = mapCreature.getTileMatrix();
        e1.doPassiveActions();
        int newY = 3;
        int newX = 3;
        assertEquals(newY, e1.getYc());
        assertEquals(newX, e1.getXc());
        Sound ennuiSound = new Sound(mapCreature, -1, -1, Ennui.NAME, "");
        assertTrue(tileMatrixC.get(newY - 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY + 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX - 1).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX + 1).getTileSounds().contains(ennuiSound));
    }

    @Test
    void testStopMovingAvaSameTile() {
        testEnnui.setYc(TEST_START_Y_C);
        testEnnui.setXc(TEST_START_X_C);

        testEnnui.doPassiveActions();
        assertEquals(TEST_START_Y_C, testEnnui.getYc());
        assertEquals(TEST_START_X_C, testEnnui.getXc());
    }

    @Test
    void moveAvoidAva() {
        avaC.moveAva("e");
        testEnnui.setYc(3);
        testEnnui.setXc(3);
        testEnnui.doPassiveActions();
        assertEquals(3, testEnnui.getYc());
        assertEquals(4, testEnnui.getXc());
    }

    @Test
    void testMoveWalledIn() {
        Ennui ennui = new Ennui(1, 4);
        MossyGate mossyGate = new MossyGate(1, 1, 2, 1);
        interList1 = new ArrayList<>(
                Arrays.asList(ennui, mossyGate)
        );
        try {
            map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1, TEST_START_X_1,
                    itemList1, interList1, TEST_MAP_1);
        } catch (MismatchedMapSizeException | EdgeOfMapException e) {
            e.printStackTrace();
        }

        map1.nextState();
        assertEquals(1, ennui.getYc());
        assertEquals(4, ennui.getXc());

    }
    @Test
    void testExamineStrings() {
        assertFalse(testEnnui.examine("not valid action"));
        assertTrue(testEnnui.examine("take thing"));
        assertTrue(testEnnui.examine("take rusty key"));
        assertTrue(testEnnui.examine("take key"));
        assertTrue(testEnnui.examine("some other action"));
    }
}
