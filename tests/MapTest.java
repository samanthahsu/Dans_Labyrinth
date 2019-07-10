
// todo write tests for every goddamn function that may or may not exist

import model.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTest {
    Map map;

    @BeforeEach
    public void newMap(){
        map = new Map();
    }

    @Test
    public void ConstructorTest(){}

    @Test
    public void isTileFloorTest(){
        assertEquals(false,map.isTileFloor(6, 1));
        assertEquals(true,map.isTileFloor(7, 1));
    }

    @Test
    public void updateDisplayTileTest(){
        map.updateDisplayTile(0, 0, 'G');
        char[][] expected =

        map.getMapDisplay();
    }

    @Test
    public void revealTilesTest(){}

}
