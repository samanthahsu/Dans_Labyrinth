package tests;

import model.Map;
import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.items.Item;
import model.mapobjects.items.Note;
import model.mapobjects.items.PizzaBox;
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
        assertEquals(TEST_START_Y_1, ava1.getYc());
        assertEquals(TEST_START_X_1, ava1.getXc());
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
        assertEquals(currentY, ava1.getYc());
        assertEquals(currentX, ava1.getXc());
        assertEquals(Map.AVATAR, tileMatrix1.get(currentY).get(currentX).getCurrChar());
        assertEquals(Map.FLOOR, tileMatrix1.get(prevY).get(prevX).getCurrChar());

        ava1.moveAva("e");
        currentX += 1;
        assertEquals(currentY, ava1.getYc());
        assertEquals(currentX, ava1.getXc());

        ava1.moveAva("s");
        currentY += 1;
        assertEquals(currentY, ava1.getYc());
        assertEquals(currentX, ava1.getXc());

        ava1.moveAva("w");
        currentX -= 1;
        assertEquals(currentY, ava1.getYc());
        assertEquals(currentX, ava1.getXc());

    }

    @Test
    void testWalkOfEdgeDoNothing() {
        ava1.setYc(WIN_Y_1);
        ava1.setXc(WIN_X_1);
        ava1.moveAva("n");
        assertEquals(WIN_Y_1, ava1.getYc());
        assertEquals(WIN_X_1, ava1.getXc());
    }

    @Test
    void testWalkIntoWall() {
        ava1.moveAva("w");
        assertEquals(TEST_START_Y_1, ava1.getYc());
        assertEquals(TEST_START_X_1, ava1.getXc());
    }

    @Test
    void testWalkIntoExaminable() {
        Examinable examinable = new Note(TEST_START_Y_1, TEST_START_X_1 + 1);
        examinable.setMap(map1);
        ava1.moveAva("e");
        assertEquals(TEST_START_Y_1, ava1.getYc());
        assertEquals(TEST_START_X_1 + 1, ava1.getXc());
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
    void moveAvaToPopulatedTile() {
        map1.addExaminable(new Ennui(1, 1), 1, 1);
       ava1.moveAva("n");
    }

    @Test
    void dropItemTestWhichExists() {
        Item droppedItem = ava1.getCurrItems().get(PizzaBox.NAME);
        droppedItem.setMap(map1);
        ava1.dropItem(PizzaBox.NAME);
        assertTrue(ava1.getCurrItems().isEmpty());
        assertTrue(map1.getTileMatrix().get(TEST_START_Y_1).get(TEST_START_X_1)
                .getCurrExaminables().containsKey(PizzaBox.NAME));
        assertEquals(TEST_START_Y_1, droppedItem.getYc());
        assertEquals(TEST_START_X_1, droppedItem.getXc());
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
        assertEquals(TEST_START_Y_1, abusedItem.getYc());
        assertEquals(TEST_START_X_1, abusedItem.getXc());
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

    @Test
    void testEquals() {
        assertTrue(ava1.equals(ava1));
        assertFalse(ava1.equals(0));

    }
}
