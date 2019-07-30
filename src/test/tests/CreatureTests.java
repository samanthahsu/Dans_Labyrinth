package tests;

import model.MapObjects.Examinable;
import model.MapObjects.creatures.Ennui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        HashSet<Examinable> tempInter;
//        todo careful checking if index valid
        assertTrue(tileMatrixC.get(newY - 1).get(newX).getCurrInteractables().containsKey(Ennui.SOUND_NAME));
        assertTrue(tileMatrixC.get(newY + 1).get(newX).getCurrInteractables().containsKey(Ennui.SOUND_NAME));
        assertTrue(tileMatrixC.get(newY).get(newX - 1).getCurrInteractables().containsKey(Ennui.SOUND_NAME));
        assertTrue(tileMatrixC.get(newY).get(newX + 1).getCurrInteractables().containsKey(Ennui.SOUND_NAME));

        testEnnui.doPassiveActions();
        newY += 1;
        newX += 0;

        assertEquals(newY, testEnnui.getY());
        assertEquals(newX, testEnnui.getX());

        assertTrue(tileMatrixC.get(newY - 1).get(newX).getCurrInteractables().containsKey(Ennui.SOUND_NAME));
        assertTrue(tileMatrixC.get(newY + 1).get(newX).getCurrInteractables().containsKey(Ennui.SOUND_NAME));
        assertTrue(tileMatrixC.get(newY).get(newX - 1).getCurrInteractables().containsKey(Ennui.SOUND_NAME));
        assertTrue(tileMatrixC.get(newY).get(newX + 1).getCurrInteractables().containsKey(Ennui.SOUND_NAME));

    }
}
