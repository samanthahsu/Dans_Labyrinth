package tests;

import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.items.Item;
import model.mapobjects.items.PizzaBox;
import model.mapobjects.items.RustyKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ItemTests extends TestMapDataAndMethods {

    private static final String AVA_NAME = Avatar.NAME;
    private Item rustyKey;
    private Item note;
    private Examinable mossyGate;

    @BeforeEach
    void Setup(){
        initTestMaps();
        rustyKey = new RustyKey();
//        mossyGate = new MossyGate();
        map1.addExaminable(rustyKey, 2, 3);
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

    @Test
    void testUseRustyKey() {
        assertFalse(rustyKey.use("not a gate"));
//        assertTrue()
    }

    @Test
    void testNote() {
    }
}
