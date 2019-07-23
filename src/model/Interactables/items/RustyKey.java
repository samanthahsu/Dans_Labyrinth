package model.Interactables.items;

import model.Map;

/*used to open mossy gate*/
public class RustyKey extends Item {

    final static String KEY_NAME = "rusty key";
    final static String KEY_DESCR = "a near disintegrating key of undiscernable colour";

    public RustyKey() {
        super(0,0);
        name = KEY_NAME;
        description = KEY_DESCR;
    }

    @Override
    public boolean interact(Map map) {
        return false;
    }
}
