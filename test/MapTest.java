// todo write tests for every goddamn function that may or may not exist

import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertFalse(map.isTileFloor(6, 1));
        assertTrue(map.isTileFloor(7, 1));
    }

    @Test
    void updateDisplayTileTest(){
        map.updateDisplayTile(0, 0, 'G');
        char[][] expected =

        map.getMapDisplay();
    }

    @Test
    void revealTilesMiddleTest(){
        map.revealTiles(5,4);
        assertEquals(,map.getMapDisplay());
    }

    @Test
    void revealTilesNWTest() {
        map.revealTiles(0,0);
    }
    @Test
    void revealTilesNETest(){
        map.revealTiles(20,0);

    }
    @Test
    void revealTilesSWTest(){
        map.revealTiles(0,9);

    }
    @Test
    void revealTilesSETest(){
        map.revealTiles(20,9);

    }


        @Test
    void printDisplayMapTest(){}

    @Test
    void printMovePlaceholderTest(){}


}
