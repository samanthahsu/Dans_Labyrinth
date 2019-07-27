package model.Interactables.items;

import model.Interactables.features.MossyGate;

/*used to open mossy gate*/
public class RustyKey extends Item {

    public final static String KEY_NAME = "rusty key";
    private final static String KEY_DESCR = "a near disintegrating key of indiscernible colour";

    public RustyKey() {
        super(0,0);
        name = KEY_NAME;
        description = KEY_DESCR;
    }

    @Override
    public boolean interact(String target) {
        if (target.equals(MossyGate.NAME)) {
            MossyGate mossyGate = (MossyGate) map.getInteractables().get(MossyGate.NAME);
            mossyGate.open();
            map.getAva().getCurrItems().remove(KEY_NAME);
            System.out.println("the rusty key disintegrated in Dan's hand as he tried to pull it from the keyhole.");
                return true;
            }
        return false;
    }
}
