package tests;

import model.Map;
import model.MapObjects.Avatar;
import model.MapObjects.Examinable;
import model.MapObjects.items.Item;
import model.MapObjects.items.Note;
import model.MapObjects.items.PizzaBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class AvatarTests extends TestMapDataAndMethods {

    private static final String DUMMY_ITEM_NAME = "my sanity";
    private static final String DUMMY_TARGET = "oxygen";
    PizzaBox pizzaBox;
    private static HashMap<String, Examinable> expectedItems = new HashMap<>();

    @BeforeEach
    void Setup(){
        initTestMaps();
    }

    @Test
    void ConstructorTest(){
        assertEquals(TEST_START_Y_1, ava1.getY());
        assertEquals(TEST_START_X_1, ava1.getX());
        HashMap<String, Item> expected = new HashMap<>();
        expected.put(PizzaBox.NAME, new PizzaBox());
        assertTrue(ava1.getCurrItems().equals(expected));
        expectedItems = new HashMap<>();
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
    void testWalkOfEdgeDoNothing() {
        ava1.setY(WIN_Y_1);
        ava1.setX(WIN_X_1);
        ava1.moveAva("n");
        assertEquals(WIN_Y_1, ava1.getY());
        assertEquals(WIN_X_1, ava1.getX());
    }

    @Test
    void testWalkIntoWall() {
        ava1.moveAva("w");
        assertEquals(TEST_START_Y_1, ava1.getY());
        assertEquals(TEST_START_X_1, ava1.getX());
    }

    @Test
    void testWalkIntoExaminable() {
        Examinable examinable = new Note(TEST_START_Y_1, TEST_START_X_1 + 1);
        examinable.setMap(map1);
        ava1.moveAva("e");
        assertEquals(TEST_START_Y_1, ava1.getY());
        assertEquals(TEST_START_X_1 + 1, ava1.getX());
    }

    @Test
    void pickUpItemTest(){
        Note newExaminable = new Note(TEST_START_Y_1, TEST_START_X_1);
        newExaminable.setMap(map1);
        map1.addExaminable(newExaminable, TEST_START_Y_1,
                TEST_START_X_1);
        ava1.pickUpItem(Note.NAME);
        HashMap<String, Examinable> hashMap = new HashMap<>();
        hashMap.put(PizzaBox.NAME, new PizzaBox());
        hashMap.put(newExaminable.getName(), newExaminable);
        assertTrue(ava1.getCurrItems().equals(hashMap));

        ava1.pickUpItem(Note.NAME);
        assertTrue(ava1.getCurrItems().equals(hashMap));
    }

    @Test
    void dropItemTestWhichExists() {
        Item droppedItem = ava1.getCurrItems().get(PizzaBox.NAME);
        droppedItem.setMap(map1);
        ava1.dropItem(PizzaBox.NAME);
        assertTrue(ava1.getCurrItems().isEmpty());
        assertTrue(map1.getTileMatrix().get(TEST_START_Y_1).get(TEST_START_X_1)
                .getCurrExaminables().containsKey(PizzaBox.NAME));
        assertEquals(TEST_START_Y_1, droppedItem.getY());
        assertEquals(TEST_START_X_1, droppedItem.getX());
    }

    @Test
    void dropItemWhichDoesntExist() {
        HashMap<String, Examinable> expectedItems = new HashMap<>();
        expectedItems.put(PizzaBox.NAME, new PizzaBox());

        assertEquals(expectedItems, ava1.getCurrItems());
        ava1.dropItem(DUMMY_ITEM_NAME);
        assertEquals(expectedItems, ava1.getCurrItems());
        assertFalse(map1.getTileMatrix().get(TEST_START_Y_1).get(TEST_START_X_1)
                .getCurrExaminables().containsKey(DUMMY_ITEM_NAME));
    }

    @Test
    void testPickupAndDrop() {
        Note abusedItem = new Note(TEST_START_Y_1, TEST_START_X_1);
        abusedItem.setMap(map1);
        map1.addExaminable(abusedItem, TEST_START_Y_1,
                TEST_START_X_1);
        ava1.pickUpItem(Note.NAME);
        expectedItems.put(PizzaBox.NAME, new PizzaBox());
        expectedItems.put(abusedItem.getName(), abusedItem);
        assertTrue(ava1.getCurrItems().equals(expectedItems));

        ava1.dropItem(Note.NAME);
        expectedItems.remove(Note.NAME);
        assertEquals(expectedItems, ava1.getCurrItems());
        assertTrue(map1.getTileMatrix().get(TEST_START_Y_1).get(TEST_START_X_1)
                .getCurrExaminables().containsKey(Note.NAME));
        assertEquals(TEST_START_Y_1, abusedItem.getY());
        assertEquals(TEST_START_X_1, abusedItem.getX());
    }

    @Test
    void useNullItem() {
        expectedItems.put(PizzaBox.NAME, new PizzaBox());
        assertEquals(expectedItems, ava1.getCurrItems());
        ava1.useItem(DUMMY_ITEM_NAME, Avatar.NAME);
        assertEquals(expectedItems, ava1.getCurrItems());
    }

    @Test
    void testUseItemOnNullTarget() {
        expectedItems.put(PizzaBox.NAME, new PizzaBox());
        assertEquals(expectedItems, ava1.getCurrItems());
        ava1.useItem(PizzaBox.NAME, DUMMY_TARGET);
        assertEquals(expectedItems, ava1.getCurrItems());
    }
}
