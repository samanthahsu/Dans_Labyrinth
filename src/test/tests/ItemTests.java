package tests;

import model.mapobjects.Avatar;
import model.mapobjects.items.PizzaBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTests extends TestMapDataAndMethods {

    public static final String AVA_NAME = Avatar.NAME;

    @BeforeEach
    void Setup(){
        initTestMaps();
    }

    @Test
    void testUsePizzaWhenFull() {
        assertEquals(3, ava1.getSanity());
        ava1.useItem(PizzaBox.NAME, AVA_NAME);
        assertEquals(3, ava1.getSanity());
    }

    @Test
    void testUsePizzaWhenHurt() {
        ava1.setSanity(2);
        assertEquals(2, ava1.getSanity());
        ava1.useItem(PizzaBox.NAME, "Dan");
        assertEquals(3, ava1.getSanity());
    }

    @Test
    void testUsePizzaRunOut() {
        for (int i = 0; i < 8; i++) {
            ava1.setSanity(2);
            ava1.useItem(PizzaBox.NAME, "Dan");
            assertEquals(3, ava1.getSanity());
        }
        ava1.setSanity(2);
        ava1.useItem(PizzaBox.NAME, "Dan");
        assertEquals(2, ava1.getSanity());
    }
}
