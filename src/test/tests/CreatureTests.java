package tests;

import model.mapobjects.Examinable;
import model.mapobjects.Sound;
import model.mapobjects.creatures.Ennui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals("ennui", testEnnui.getName());
        assertEquals(Examinable.TYPE_CREATURE, testEnnui.getTypeId());
    }

    @Test
    void testDoPassiveActionsOnceAvaClose() {
        testEnnui.doPassiveActions();
        assertEquals(ENNUI_CAPTURE_START_Y - 1, testEnnui.getYc());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getXc());

        int newY = ENNUI_CAPTURE_START_Y - 1; //1
        int newX = ENNUI_CAPTURE_START_X; //1

//        todo careful checking if index valid\
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
    void testStopMovingAvaSameTile() {
        testEnnui.setYc(TEST_START_Y_C);
        testEnnui.setXc(TEST_START_X_C);

        testEnnui.doPassiveActions();
        assertEquals(TEST_START_Y_C, testEnnui.getYc());
        assertEquals(TEST_START_X_C, testEnnui.getXc());
    }

    @Test
    void testExamineStrings() {
        assertFalse(testEnnui.examine("take thing"));
        assertTrue(testEnnui.examine("take rusty key"));
        assertTrue(testEnnui.examine("take key"));
    }
}
