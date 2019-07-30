package tests;

import model.MapObjects.Sound;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SoundTests extends TestMapDataAndMethods {

    public static final String SOURCE_NAME_1 = "my cupboard";
    public static final String SOUND_STR_1 = "HELLOHellohello...";
    Sound sound;

    @BeforeEach
    void setup() {
        initTestMaps();
        sound = new Sound(map1, TEST_START_Y_1, TEST_START_X_1, SOURCE_NAME_1,
                SOUND_STR_1);
    }

    @Test
    void testConstructor() {
        assertEquals(SOUND_STR_1, sound.getSoundStr());
        assertEquals(SOURCE_NAME_1, sound.getSourceName());
    }
}
