import model.*;
import model.Interactable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvatarTest {

    private Map map1;
    private static final String test_map1 =
            "@@@ @@"+
            "@@ @ @"+
            "@   @@"+
            "@@@@@@";
    private static final int test_height1 = 4;
    private static final int test_width1 = 6;
    private static final int test_startY1 = 2;
    private static final int test_startX1 = 1;
    private static final int winY1 = 0;
    private static final int winX1 = 3;
    private static final ArrayList<Interactable> inter = new ArrayList<>();
    private static final int ItemY1 = 2;
    private static final int ItemX1 = 2;

    Avatar a;
    Exo exo;

    @BeforeEach
    void BeforeEach(){
        exo = new Exo(ItemY1, ItemX1);
        inter.add(exo);
        map1 = new Map(test_height1, test_width1, test_map1, test_startY1,
                test_startX1, winY1, winX1, inter, new ArrayList<Item>());
        a = new Avatar(test_startY1, test_startX1, new ArrayList<Item>());
    }

    @Test
    void ConstructorTest(){
        assertEquals(test_startY1, a.getYpos());
        assertEquals(test_startX1, a.getXpos());

    }

    @Test
    void moveAvaTest(){
        a.moveAva("n", map1);
        assertEquals(test_startY1, a.getYpos());
        assertEquals(test_startX1, a.getXpos());

        a.moveAva("e", map1);
        assertEquals(test_startY1, a.getYpos());
        assertEquals(test_startX1+1, a.getXpos());

        a.moveAva("s", map1);
        assertEquals(test_startY1, a.getYpos());
        assertEquals(test_startX1+1, a.getXpos());

        a.moveAva("w", map1);
        assertEquals(test_startY1, a.getYpos());
        assertEquals(test_startX1, a.getXpos());

    }

    @Test
    void pickUpItemTest(){
        a.pickUpItem(map1);
        ArrayList<Item> arrayExpected = new ArrayList<Item>();
        assertTrue(a.getItems().equals(arrayExpected));

        a.moveAva("e", map1);
        a.pickUpItem(map1);
        arrayExpected.add(exo);
        assertTrue(a.getItems().equals(arrayExpected));
    }



}
