import model.Interactables.Interactable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        printDisplayMap(mapCreature);
        testEnnui.doPassiveActions();
        assertEquals(ENNUI_CAPTURE_START_Y - 1, testEnnui.getYpos());
        assertEquals(ENNUI_CAPTURE_START_X, testEnnui.getXpos());

        int newY = ENNUI_CAPTURE_START_Y - 1;
        int newX = ENNUI_CAPTURE_START_X;
//        tileMatrix1.get(newY).get(newX).getInteractables().contains(new Sound(low sound));
/*todo
        check loud sounds on 1 tile rad floors,
        check soft sounds on 2 tile rad floors
*/

    }
}
