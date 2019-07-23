import exceptions.mapException;
import model.Map;
import model.WriterReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

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
        initTestMaps();
        final String fileName1 = "writeMapTest";
        Map readMap1 = null;
        try {
            writerReader.writeMap(map1, fileName1);
            readMap1 = writerReader.readMap(fileName1);

        } catch (IOException | ClassNotFoundException e) {
            fail("threw e");
        }
        assertTrue(readMap1.equals(map1));

        Map readMap2 = null;
        try {
        writerReader.writeMap(map2, fileName1);
        readMap2 = writerReader.readMap(fileName1);
        } catch (IOException | ClassNotFoundException e) {
            fail("threw e");
        }
        assertTrue(readMap2.equals(map2));
    }

    @Test //todo
    void testReadAndWriteException() {
        try {
            writerReader.readMap("No File");
            fail("no e");
        } catch (IOException e) {
//            expected
        } catch (ClassNotFoundException e) {
            fail("wrong e");
        }
//        todo find a thing that will make writer fail
    }

    @Test //todo is testing failure necessary??? (only one default ever)
    void testBuildDefaultMapException() {
        try {
            Map testMap = writerReader.buildDefaultMap();
        } catch (mapException e) {
            fail("threw e");
        }
    }
}
