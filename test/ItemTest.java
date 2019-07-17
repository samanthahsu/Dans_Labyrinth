import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest extends TestHelper {

    private static final ArrayList<Item> items = new ArrayList<>();
    private static final int ITEM_Y_1 = 2;
    private static final int ITEM_X_1 = 1;

    @BeforeEach
    void BeforeEach(){
        exo1 = new Exo(ITEM_Y_1, ITEM_X_1);
        items.add(exo1);
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, TEST_START_Y_1,
                TEST_START_X_1, WIN_Y_1, WIN_X_1, interactableArrayList, items);
        ava1 = map1.getAva();
    }

    @Test
    void useItemTest() {
        ava1.useItem("Exo", map1);
        assertEquals(1, ava1.getStatus());
    }
}
