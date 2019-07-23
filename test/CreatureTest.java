import model.Interactables.Interactable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreatureTest extends TestMapSuite {

    @BeforeEach
    void Setup(){
        initTestMaps();
    }

    @Test
    void ConstructorTest(){
        assertEquals(ENNUI_CAPTURE_START_Y, testEnnui.getYpos());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getXpos());
        assertEquals("ennui", testEnnui.getName());
        assertEquals(Interactable.TYPE_CREATURE, testEnnui.getTypeId());
    }

    @Test
    void testDoPassiveActionsOnceAvaClose() {
        testEnnui.doPassiveActions();
        assertEquals(ENNUI_CAPTURE_START_Y - 1, testEnnui.getYpos());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getXpos());

        int newY = ENNUI_CAPTURE_START_Y - 1; //1
        int newX = ENNUI_CAPTURE_START_X; //1

        HashSet<Interactable> tempInter;
//        todo careful checking if index valid
        assertTrue(interactablesContains(tileMatrixC.get(newY - 1).get(newX)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY + 1).get(newX)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY).get(newX - 1)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY).get(newX + 1)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY).get(newX)
                .getInteractables(), "ennui"));


        testEnnui.doPassiveActions();
        newY += 1;
        newX += 0;

        assertEquals(newY, testEnnui.getYpos());
        assertEquals(newX, testEnnui.getXpos());

        assertTrue(interactablesContains(tileMatrixC.get(newY - 1).get(newX)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY + 1).get(newX)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY).get(newX - 1)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY).get(newX + 1)
                .getInteractables(), "ennui sound"));
        assertTrue(interactablesContains(tileMatrixC.get(newY).get(newX)
                .getInteractables(), "ennui"));

    }
}
