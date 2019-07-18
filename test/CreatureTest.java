import model.Interactable;
import model.Map;
import model.creatures.Exo;
import model.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreatureTest extends TestHelper {

    private static final ArrayList<Item> items = new ArrayList<>();

    @BeforeEach
    void BeforeEach(){
        interY1 = 2;
        interX1 = 1;

        exo1 = new Exo(interY1, interX1);
        interactableArrayList.add(exo1);
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, TEST_START_Y_1,
                TEST_START_X_1, WIN_Y_1, WIN_X_1, interactableArrayList, new ArrayList<Interactable>());
        ava1 = map1.getAva();

    }

    @Test
    void ConstructorTest(){
    }

    @Test // todo temp, rework interactable system
    void attackTest() {
        exo1.attack(map1);
        assertEquals(1 , ava1.getStatus());
        exo1.attack(map1);
        assertEquals(2 , ava1.getStatus());
        exo1.attack(map1);
        assertEquals(2, ava1.getStatus());
    }
}
