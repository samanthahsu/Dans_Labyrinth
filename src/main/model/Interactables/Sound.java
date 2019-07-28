package model.Interactables;

import model.Locatable;
import model.Map;

import java.util.Objects;

/*sounds made by surrounding interactables, varying in intensity, and fades or
strengthens based on distance*/
public class Sound extends Locatable {

    private String soundStr;
    private String sourceName;

    public Sound(Map map, int y, int x, String sourceName, String soundStr) {
        super(map, y, x);
        this.sourceName = sourceName;
        this.soundStr = soundStr;
    }

    public String getSoundStr() {
        return soundStr;
    }

    public String getSourceName() {
        return sourceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sound)) return false;
        Sound sound = (Sound) o;
        return Objects.equals(sourceName, sound.sourceName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sourceName);
    }
}
