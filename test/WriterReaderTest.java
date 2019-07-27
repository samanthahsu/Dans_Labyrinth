import exceptions.mapException;
import model.Map;
import model.WriterReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WriterReaderTest extends TestMapDataAndMethods {

    private static final String PROJECT_PATH = System.getProperty("user.dir");
    final static String FILE_NAME_1 = "writeMapTest";
    private WriterReader writerReader;
    TestMapDataAndMethods testMapDataAndMethods = new TestMapDataAndMethods();


    @BeforeEach
    void Setup() {
        writerReader = new WriterReader();
        initTestMaps();
    }

    @Test
    void testConstructor() {
        assertEquals(PROJECT_PATH + "\\saves\\", writerReader.getSavePath());

        String newSavePath = PROJECT_PATH + "\\test\\expectedSVL";
        writerReader = new WriterReader(newSavePath);
        assertEquals(newSavePath, writerReader.getSavePath());
    }

    @Test
    void testWriteAndReadMap() {
        Map readMap1 = null;

        try {
            writerReader.writeMap(map1, FILE_NAME_1);
            readMap1 = writerReader.readMap(FILE_NAME_1);
        } catch (IOException | ClassNotFoundException e) {
            fail("threw e");
        }
        assertTrue(readMap1.equals(map1));
    }

    @Test
    void testOverwriteAndRead() {
        Map readMap2 = null;
        try {
            writerReader.writeMap(map2, FILE_NAME_1);
            readMap2 = writerReader.readMap(FILE_NAME_1);
        } catch (IOException | ClassNotFoundException e) {
            fail("threw e");
        }
        assertTrue(readMap2.equals(map2));
    }

    @Test
    void testReadNonExistentFile() {
        try {
            writerReader.readMap("No File");
            fail("no e");
        } catch (IOException e) {
//            expected
        } catch (ClassNotFoundException e) {
            fail("wrong e");
        }
    }

    @Test
    void testWriteToNonExistentPath () {
        writerReader = new WriterReader("I'll stop wearing black when " +
                "they make a darker color - wilson\n");
        try {
            writerReader.writeMap(map1, "i shouldn't exist");
            fail("no e");
        } catch (IOException e) {
//            expected
        }
    }

    @Test
    void testBuildDefaultMapException() {
//        todo not worth testing result because defaultmap not finalized
        try {
            Map testMap = writerReader.buildDefaultMap();
        } catch (mapException e) {
            fail("threw e");
        }
    }
}
