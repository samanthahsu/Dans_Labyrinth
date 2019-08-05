package tests;

import model.Map;
import model.exceptions.EdgeOfMapException;
import model.exceptions.MismatchedMapSizeException;
import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.features.MossyGate;
import model.mapobjects.items.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTests extends TestMapDataAndMethods {

    private static final String AVA_NAME = Avatar.NAME;
    private Item rustyKey;
    private Item note;
    private Examinable mossyGate;
    private Item poptart;

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
        assertFalse(rustyKey.examine("hello"));
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

    @Test
    void testPopTart() {
        poptart = new Poptart();
        itemListC.add(poptart);
        try {
            mapCreature = new Map(TEST_HEIGHT_C, TEST_WIDTH_C, WIN_Y_C, WIN_X_C, TEST_START_Y_C, TEST_START_X_C,
                    itemListC, interListC, TEST_MAP_C);
        } catch (MismatchedMapSizeException | EdgeOfMapException e) {
            e.printStackTrace();
        }
        avaC = mapCreature.getAva();
        avaC.setSanity(1);
        assertFalse(poptart.use("Not here"));
        assertTrue(poptart.use("Dan"));
        assertEquals(2, avaC.getSanity());
    }
}
