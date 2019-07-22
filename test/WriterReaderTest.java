import model.Map;
import model.WriterReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WriterReaderTest extends TestMapSuite {

    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private WriterReader writerReader;
    TestMapSuite testMapSuite = new TestMapSuite();

    @BeforeEach
    void Setup() {
        writerReader = new WriterReader();
    }

    @Test
    void ConstructorTest() {
        assertEquals(PROJECT_PATH + "\\saves\\", writerReader.getSavePath());

        String newSavePath = PROJECT_PATH + "\\test\\expectedSVL";
        writerReader = new WriterReader(newSavePath);
        assertEquals(newSavePath, writerReader.getSavePath());
    }

    @Test
    void readAndWriteTest() {
        initMaps();
        final String fileName1 = "writeMapTest";
        writerReader.writeMap(map1, fileName1);
        Map readMap1 = writerReader.readMap(fileName1);
        assertTrue(readMap1.equals(map1));

        writerReader.writeMap(map2, fileName1);
        Map readMap2 = writerReader.readMap(fileName1);
        assertTrue(readMap2.equals(map2));
    }

    @Test
    void testBuildDefaultMap() {
        Map testMap = writerReader.buildDefaultMap(); //todo
    }
}
