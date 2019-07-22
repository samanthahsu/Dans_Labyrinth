package model.Interactables;

import model.Map;

/*sounds made by surrounding interactables, varying in intensity, and fades or strengthens based on distance*/
public class Sound extends Interactable {


    public Sound(int y, int x, String name, String description) {
        super(y, x);
        typeId = TYPE_SOUND;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean interact(Map map) {
        return false;
    }
}
