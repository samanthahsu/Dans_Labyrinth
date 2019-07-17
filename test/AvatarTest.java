import model.Exo;
import model.Item;
import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvatarTest extends TestHelper {


    @BeforeEach
    void BeforeEach(){
        interactableArrayList = new ArrayList<>();
        interY1 = 2;
        interX1 = 2;
        exo1 = new Exo(interY1, interX1);
        interactableArrayList.add(exo1);

        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, TEST_START_Y_1,
                TEST_START_X_1, WIN_Y_1, WIN_X_1, interactableArrayList, new ArrayList<Item>());
        ava1 = map1.getAva();
    }

    @Test
    void ConstructorTest(){
        assertEquals(TEST_START_Y_1, ava1.getYpos());
        assertEquals(TEST_START_X_1, ava1.getXpos());

    }

    @Test
    void moveAvaTest(){
        ava1.moveAva("n", map1);
        assertEquals(TEST_START_Y_1, ava1.getYpos());
        assertEquals(TEST_START_X_1, ava1.getXpos());

        ava1.moveAva("e", map1);
        assertEquals(TEST_START_Y_1, ava1.getYpos());
        assertEquals(TEST_START_X_1 +1, ava1.getXpos());

        ava1.moveAva("s", map1);
        assertEquals(TEST_START_Y_1, ava1.getYpos());
        assertEquals(TEST_START_X_1 +1, ava1.getXpos());

        ava1.moveAva("w", map1);
        assertEquals(TEST_START_Y_1, ava1.getYpos());
        assertEquals(TEST_START_X_1, ava1.getXpos());

    }

    @Test
    void pickUpItemTest(){
        ava1.pickUpItem(map1);
        ArrayList<Item> arrayExpected = new ArrayList<>();
        assertTrue(ava1.getItems().equals(arrayExpected));

        ava1.moveAva("e", map1);
        ava1.pickUpItem(map1);
        arrayExpected.add(exo1);
        assertTrue(arrayExpected.equals(ava1.getItems()));
    }



}
