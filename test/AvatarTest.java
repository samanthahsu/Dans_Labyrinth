import model.Interactables.items.Item;
import model.Interactables.items.PizzaBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvatarTest extends TestMapSuite {

    PizzaBox pizzaBox;

    @BeforeEach
    void Setup(){
        initTestMaps();
/*
        interactableArrayList = new ArrayList<>();
        interY1 = 2;
        interX1 = 2;
        pizzaBox = new PizzaBox(interY1, interX1);
        interactableArrayList.add(pizzaBox);

        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1,
                TEST_START_X_1, new ArrayList<Interactable>(), );
        ava1 = map1.getAva();
*/
    }

    @Test
    void ConstructorTest(){
        assertEquals(TEST_START_Y_1, ava1.getYpos());
        assertEquals(TEST_START_X_1, ava1.getXpos());
        assertTrue(itemListEquals(ava1.getItemList(), new ArrayList<Item>(
                Arrays.asList(new PizzaBox())
        )));
//        todo add test with items
    }

    @Test
    void moveAvaTest(){
        int currentY = TEST_START_Y_1;
        int currentX = TEST_START_X_1;

        ava1.moveAva("n");
        currentY -= 1;
        assertEquals(currentY, ava1.getYpos());
        assertEquals(currentX, ava1.getXpos());

        ava1.moveAva("e");
        currentX += 1;
        assertEquals(currentY, ava1.getYpos());
        assertEquals(currentX, ava1.getXpos());

        ava1.moveAva("s");
        currentY += 1;
        assertEquals(currentY, ava1.getYpos());
        assertEquals(currentX, ava1.getXpos());

        ava1.moveAva("w");
        currentX -= 1;
        assertEquals(currentY, ava1.getYpos());
        assertEquals(currentX, ava1.getXpos());

    }

    @Test
    void pickUpItemTest(){
/*
        ava1.pickUpItem();
        ArrayList<Interactable> arrayExpected = new ArrayList<>();
        assertTrue(ava1.getItemList().equals(arrayExpected));

        ava1.moveAva("e");
        ava1.pickUpItem();
        arrayExpected.add(pizzaBox);
        assertTrue(arrayExpected.equals(ava1.getItemList()));
*/
    }



}
