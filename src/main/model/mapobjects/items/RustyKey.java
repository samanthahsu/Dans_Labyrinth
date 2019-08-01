package model.mapobjects.items;

import model.mapobjects.features.MossyGate;

/*used to open mossy gate*/
public class RustyKey extends Item {

    public static final String NAME = "rusty key";
    private static final String KEY_DESCR = "a near disintegrating key of indiscernible colour";

    public RustyKey() {
        name = NAME;
        description = KEY_DESCR;
    }

    //todo what is going on
    @Override
    public boolean use(String target) {
        if (target.equals(MossyGate.NAME)) {
            MossyGate mossyGate = (MossyGate) getMap().getAllExaminables().get(MossyGate.NAME);
            mossyGate.open();
            getMap().getAva().getCurrItems().remove(NAME);
            System.out.println("the rusty key disintegrated in Dan's hand as he tried to pull it from the keyhole.");
            return true;
        }
        return false;
    }

}
