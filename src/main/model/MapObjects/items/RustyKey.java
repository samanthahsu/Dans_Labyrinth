package model.MapObjects.items;

import model.Map;
import model.MapObjects.features.MossyGate;

/*used to open mossy gate*/
public class RustyKey extends Item {

    public final static String NAME = "rusty key";
    private final static String KEY_DESCR = "a near disintegrating key of indiscernible colour";

    public RustyKey(Map map) {
        super(map);
        name = NAME;
        description = KEY_DESCR;
    }

    @Override
    public boolean use(String target) {
        return false;
    }

    //todo what is going on
    public boolean interact(String target) {
        if (target.equals(MossyGate.NAME)) {
            MossyGate mossyGate = (MossyGate) getMap().getInteractables().get(MossyGate.NAME);
            mossyGate.open();
            getMap().getAva().getCurrItems().remove(NAME);
            System.out.println("the rusty key disintegrated in Dan's hand as he tried to pull it from the keyhole.");
                return true;
            }
        return false;
    }

}
