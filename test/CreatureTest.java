//import model.Interactable;
//import model.Map;
//import model.creatures.Exo;
//import model.items.Item;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class CreatureTest extends TestHelper {
//
//    private static final ArrayList<Item> items = new ArrayList<>();
//
//    @BeforeEach
//    void Setup(){
//        interY1 = 2;
//        interX1 = 1;
//
//        exo1 = new Exo(interY1, interX1);
//        interactableArrayList = new ArrayList<>();
//        interactableArrayList.add(exo1);
//        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, WIN_Y_1, WIN_X_1, TEST_START_Y_1,
//                TEST_START_X_1, new ArrayList<Interactable>(), );
//        ava1 = map1.getAva();
//
//    }
//
//    @Test
//    void ConstructorTest(){
//        assertEquals("Exo", exo1.getName());
//        assertEquals(interY1, exo1.getYpos());
//        assertEquals(interX1, exo1.getXpos());
//        assertEquals(1, exo1.getHealth());
//    }
//
//    @Test
//    void attackTest() {
//        ArrayList<ArrayList<Interactable>> expected;
//        expected = makeInteractableMatrix(interactableArrayList, TEST_HEIGHT_1, TEST_WIDTH_1);
//        assertEqualsInteractableMtrx(expected, map1.getInteractables(), TEST_HEIGHT_1, TEST_WIDTH_1);
//
//        exo1.attack(map1);
//        assertEquals(2 , ava1.getStatus());
//        expected = makeInteractableMatrix(new ArrayList<Interactable>(), TEST_HEIGHT_1, TEST_WIDTH_1);
//        assertEqualsInteractableMtrx(expected, map1.getInteractables(), TEST_HEIGHT_1, TEST_WIDTH_1);
//    }
//}
