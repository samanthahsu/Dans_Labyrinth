package tests;

import model.MapObjects.Examinable;
import model.MapObjects.Sound;
import model.MapObjects.creatures.Ennui;
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
        assertEquals(ENNUI_CAPTURE_START_Y, testEnnui.getY());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getX());
        assertEquals("ennui", testEnnui.getName());
        assertEquals(Examinable.TYPE_CREATURE, testEnnui.getTypeId());
    }

    @Test
    void testDoPassiveActionsOnceAvaClose() {
        testEnnui.doPassiveActions();
        assertEquals(ENNUI_CAPTURE_START_Y - 1, testEnnui.getY());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getX());

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

        assertEquals(newY, testEnnui.getY());
        assertEquals(newX, testEnnui.getX());

        assertTrue(tileMatrixC.get(newY - 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY + 1).get(newX).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX - 1).getTileSounds().contains(ennuiSound));
        assertTrue(tileMatrixC.get(newY).get(newX + 1).getTileSounds().contains(ennuiSound));
    }

    @Test
    void testStopMovingAvaSameTile() {
        testEnnui.setY(TEST_START_Y_C);
        testEnnui.setX(TEST_START_X_C);

        testEnnui.doPassiveActions();
        assertEquals(TEST_START_Y_C, testEnnui.getY());
        assertEquals(TEST_START_X_C, testEnnui.getX());
    }

    @Test
    void testExamineRightString() {
        assertFalse(testEnnui.examine("take thing"));
        assertTrue(testEnnui.examine("take rusty key"));
    }
}
