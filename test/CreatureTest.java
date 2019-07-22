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
    void attackTest() {
    }
}
