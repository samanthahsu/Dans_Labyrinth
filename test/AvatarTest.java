import model.Interactable;
import model.Map;
import model.items.Apple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvatarTest extends TestHelper {

    Apple apple;

    @BeforeEach
    void BeforeEach(){
        interactableArrayList = new ArrayList<>();
        interY1 = 2;
        interX1 = 2;
        apple = new Apple(interY1, interX1);
        interactableArrayList.add(apple);

        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, TEST_START_Y_1,
                TEST_START_X_1, WIN_Y_1, WIN_X_1, interactableArrayList, new ArrayList<Interactable>());
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
        ArrayList<Interactable> arrayExpected = new ArrayList<>();
        assertTrue(ava1.getItems().equals(arrayExpected));

        ava1.moveAva("e", map1);
        ava1.pickUpItem(map1);
        arrayExpected.add(apple);
        assertTrue(arrayExpected.equals(ava1.getItems()));
    }



}
