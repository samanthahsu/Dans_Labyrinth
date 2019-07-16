import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTest {

    Interactable exo1;
    Avatar a;

//    todo organize all these static final stuff into another class
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
    private static final ArrayList<Item> items = new ArrayList<>();
    private static final int ItemY1 = 2;
    private static final int ItemX1 = 1;


    @BeforeEach
    void BeforeEach(){
        exo1 = new Exo(ItemY1, ItemX1);
        inter.add(exo1);
        map1 = new Map(test_height1, test_width1, test_map1, test_startY1,
                test_startX1, winY1, winX1, inter, new ArrayList<Item>());
        a = map1.getAva();

    }

    @Test
    void ConstructorTest(){
    }

    @Test // todo temp, rework interactable system
    void attackTest() {
        ((Creature) exo1).attack(map1);
        assertEquals(1 ,a.getStatus());
        ((Creature) exo1).attack(map1);
        assertEquals(2 ,a.getStatus());
        ((Creature) exo1).attack(map1);
        assertEquals(2,a.getStatus());
    }
}
