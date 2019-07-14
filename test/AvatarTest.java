import model.Avatar;
import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    Avatar a;

//    todo GET THIS SHIT WORKING SO WE CAN TEST THINGS MORE EASILY

    @BeforeEach
    void BeforeEach(){
        map1 = new Map(test_height1, test_width1, test_map1, test_startY1,
                test_startX1, winY1, winX1);
        a = new Avatar(test_startY1, test_startX1);
    }

    @Test
    void ConstructorTest(){
        assertEquals(test_startY1, a.getY());
        assertEquals(test_startX1, a.getX());

    }

    @Test
    void moveAvaTest(){
        a.moveAva("n", map1);
        assertEquals(test_startY1, a.getY());
        assertEquals(test_startX1, a.getX());

        a.moveAva("e", map1);
        assertEquals(test_startY1, a.getY());
        assertEquals(test_startX1+1, a.getX());

    }

}
