import model.Interactable;
import model.Map;
import model.SaveAndLoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaveAndLoadTest extends TestHelper {

    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String TEST_FILE_NAME = "SaveAndLoadTest";

    private SaveAndLoad svl;

    @BeforeEach
    void BeforeEach() {
        svl = new SaveAndLoad();
        interactableArrayList = new ArrayList<>();
        map1 = new Map(TEST_HEIGHT_1, TEST_WIDTH_1, TEST_MAP_1, TEST_START_Y_1,
                TEST_START_X_1, WIN_Y_1, WIN_X_1, interactableArrayList, new ArrayList<Interactable>());
        map3 = new Map(TEST_HEIGHT_3, TEST_WIDTH_3, TEST_MAP_3, TEST_START_Y_3, TEST_START_X_3, WIN_Y_3, WIN_X_3,
                interactableArrayList, new ArrayList<Interactable>());
    }

    @Test
    void saveGameTest() {
        svl.saveGame(TEST_FILE_NAME, map1, map1.getAva());
        assertEqualsFile(PROJECT_PATH + "\\test\\expectedSVL\\svlExpected1",
                PROJECT_PATH + "\\saves\\" + TEST_FILE_NAME);

        svl.saveGame(TEST_FILE_NAME, map3, map3.getAva());
        assertEqualsFile(PROJECT_PATH + "\\test\\expectedSVL\\svlExpected2",
                PROJECT_PATH + "\\saves\\" + TEST_FILE_NAME);
    }

    @Test
    void loadFileTest() {
        assertEquals(null, svl.loadFile("thisOneDoesn'tExist"));

        svl.saveGame("SaveAndLoadTest", map3, map3.getAva());
        Map newMap = svl.loadFile("SaveAndLoadTest");
        assertEqualsMap(map3, newMap);
    }
}
