package tests;

import model.Map;
import model.MapObjects.items.Item;
import model.MapObjects.items.PizzaBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvatarTests extends TestMapDataAndMethods {

    PizzaBox pizzaBox;

    @BeforeEach
    void Setup(){
        initTestMaps();
    }

    @Test
    void ConstructorTest(){
        assertEquals(TEST_START_Y_1, ava1.getY());
        assertEquals(TEST_START_X_1, ava1.getX());
        HashMap<String, Item> expected = new HashMap<>();
        expected.put("pizza", new PizzaBox());
        assertTrue(ava1.getCurrItems().equals(expected));
//        todo add test with items
    }

    @Test
    void moveAvaTest(){
        int currentY = TEST_START_Y_1;
        int currentX = TEST_START_X_1;
        int prevY;
        int prevX;

        prevY = currentY;
        prevX = currentX;
        ava1.moveAva("n");
        currentY -= 1;
        assertEquals(currentY, ava1.getY());
        assertEquals(currentX, ava1.getX());
        assertEquals(Map.AVATAR, tileMatrix1.get(currentY).get(currentX).getCurrChar());
        assertEquals(Map.FLOOR, tileMatrix1.get(prevY).get(prevX).getCurrChar());

        ava1.moveAva("e");
        currentX += 1;
        assertEquals(currentY, ava1.getY());
        assertEquals(currentX, ava1.getX());

        ava1.moveAva("s");
        currentY += 1;
        assertEquals(currentY, ava1.getY());
        assertEquals(currentX, ava1.getX());

        ava1.moveAva("w");
        currentX -= 1;
        assertEquals(currentY, ava1.getY());
        assertEquals(currentX, ava1.getX());

    }

    @Test
    void pickUpItemTest(){
/*
        ava1.pickUpItem();
        ArrayList<Examinable> arrayExpected = new ArrayList<>();
        assertTrue(ava1.getCurrItems().equals(arrayExpected));

        ava1.moveAva("e");
        ava1.pickUpItem();
        arrayExpected.add(pizzaBox);
        assertTrue(arrayExpected.equals(ava1.getCurrItems()));
*/
    }



}
