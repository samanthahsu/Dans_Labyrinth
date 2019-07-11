// todo write tests for every goddamn function that may or may not exist

import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    private Map map;

    @BeforeEach
    void beforeEachTest(){
        map = new Map();
    }

    @Test
    void ConstructorTest(){}

    @Test
    void initAvatarTest(){}

    @Test
    void initMapDisplayTest(){}
    @Test
    void initMapTest(){}

    @Test
    void isTileFloorTest(){
        assertEquals(false,map.isTileFloor(6, 1));
        assertEquals(true,map.isTileFloor(7, 1));
    }

    @Test
    void updateDisplayTileTest(){
        map.updateDisplayTile(0, 0, 'G');
        char[][] expected =

        map.getMapDisplay();
    }

    @Test
    void revealTilesTest(){}

    @Test
    void isWinTest(){}
    @Test
    void printDisplayMapTest(){}

    @Test
    void printMovePlaceholderTest(){}


}
