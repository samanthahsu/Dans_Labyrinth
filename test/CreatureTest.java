import model.Avatar;
import model.Creature;
import model.Exo;
import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CreatureTest {

    Creature exo;
    Avatar a;

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
    private static final ArrayList<Creature> cs = new ArrayList<>();
    private static final int ItemY1 = 2;
    private static final int ItemX1 = 2;


    @BeforeEach
    public void BeforeEach(){
        exo = new Exo(ItemY1, ItemX1);
    }

    @Test
    public void ConstructorTest(){

    }
}
