package tests;

import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.features.MossyGate;
import model.mapobjects.items.Item;
import model.mapobjects.items.Note;
import model.mapobjects.items.PizzaBox;
import model.mapobjects.items.RustyKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTests extends TestMapDataAndMethods {

    private static final String AVA_NAME = Avatar.NAME;
    private Item rustyKey;
    private Item note;
    private Examinable mossyGate;

    @BeforeEach
    void Setup(){
        initTestMaps();
        rustyKey = new RustyKey();
        note = new Note(TEST_START_Y_C, TEST_START_X_C);
        map1.addExaminable(rustyKey, TEST_START_Y_1, TEST_START_X_1);
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
        ava1.useItem(PizzaBox.NAME, Avatar.NAME);
        assertEquals(3, ava1.getSanity());
    }

    @Test
    void testUsePizzaRunOut() {
        for (int i = 0; i < 8; i++) {
            ava1.setSanity(2);
            ava1.useItem(PizzaBox.NAME, Avatar.NAME);
            assertEquals(3, ava1.getSanity());
        }
        ava1.setSanity(2);
        ava1.useItem(PizzaBox.NAME, Avatar.NAME);
        assertEquals(2, ava1.getSanity());
    }

    @Test
    void testItemHeldExamine() {
        assertTrue(rustyKey.isHeld());
        assertTrue(rustyKey.examine("hello"));
    }

    @Test
    void testUseRustyKey() {
        assertFalse(rustyKey.use("not a gate"));
        assertFalse(rustyKey.use(MossyGate.NAME));

        mossyGate = new MossyGate(TEST_START_Y_1, TEST_START_X_1, 1, 3);
        map1.addExaminable(mossyGate, TEST_START_Y_1, TEST_START_X_1);
        assertTrue(rustyKey.use(MossyGate.NAME));
    }

    @Test
    void testNote() {
        avaC.pickUpItem(Note.NAME);
        assertTrue(note.use("Dan"));
        assertTrue(note.use("dan"));
        assertFalse(note.use("not Dan"));
    }
}
