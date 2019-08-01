package tests;

import model.MapObjects.Examinable;
import model.MapObjects.creatures.Ennui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExaminableTest {
    Examinable testExaminable;

    @BeforeEach
    void setup() {
         testExaminable = new Ennui(-1, -1);
    }

    @Test
    void testGetDescription() {
        assertEquals(Ennui.DESCRIPTION, testExaminable.getDescription());
        assertEquals(Ennui.EXAMINE_DESCRIPTION, testExaminable.getExamineDescription());
    }

}
