import model.Avatar;
import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvatarTest {

    private Map map;
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


    @BeforeEach
    void BeforeEach(){
        a = new Avatar(test_startY1, test_startX1);
    }

    @Test
    void ConstructorTest(){
    }



}
