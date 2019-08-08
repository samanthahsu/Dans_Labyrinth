package tests;

import model.mapobjects.Examinable;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.items.Bones;
import model.mapobjects.items.PizzaBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExaminableTest {
    private Examinable testExaminable;

    @BeforeEach
    void setup() {
         testExaminable = new Ennui(-1, -1);
    }

    @Test
    void testGetDescription() {
        assertEquals(Ennui.DESCRIPTION, testExaminable.getDescription());
        assertEquals(Ennui.EXAMINE_DESCRIPTION, testExaminable.getExamineDescription());
    }

    @Test
    void testEquals() {
        assertFalse(testExaminable.equals(0));
        testExaminable.hashCode();
        Examinable examinable = new Bones();
        assertFalse(testExaminable.equals(examinable));
        testExaminable = new PizzaBox();
        assertFalse(testExaminable.equals(examinable));
        testExaminable = new Bones();
        assertTrue(testExaminable.equals(examinable));
    }

}
