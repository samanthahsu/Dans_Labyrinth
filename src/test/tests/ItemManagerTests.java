package tests;

import model.mapobjects.ItemManager;
import model.mapobjects.items.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ItemManagerTests extends TestMapDataAndMethods{

    ItemManager itemManager;

    @BeforeEach
    void setup() {
        initTestMaps();
        itemManager = new ItemManager(map1, new ArrayList<Item>());
    }

    @Test
    void equals() {
        assertTrue(itemManager.equals(itemManager));
        assertFalse(itemManager.equals(0));
    }

}
