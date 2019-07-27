import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest extends TestMapDataAndMethods {

    @BeforeEach
    void Setup(){
        initTestMaps();
    }

    @Test
    void testUsePizzaWhenFull() {
        assertEquals(3, ava1.getStatus());
        ava1.useItem("pizza", "Dan");
        assertEquals(3, ava1.getStatus());
    }

    @Test
    void testUsePizzaWhenHurt() {
        ava1.setStatus(2);
        assertEquals(2, ava1.getStatus());
        ava1.useItem("pizza", "Dan");
        assertEquals(3, ava1.getStatus());
    }

    @Test
    void testUsePizzaRunOut() {
        for (int i = 0; i < 8; i++) {
            ava1.setStatus(2);
            ava1.useItem("pizza", "Dan");
            assertEquals(3, ava1.getStatus());
        }
        ava1.setStatus(2);
        ava1.useItem("pizza", "Dan");
        assertEquals(2, ava1.getStatus());

    }
}
