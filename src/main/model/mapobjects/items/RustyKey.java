package model.mapobjects.items;

import model.mapobjects.Examinable;
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
            Examinable examinable = map.tileFetchExaminable(map.getAva().getYc(),
                    map.getAva().getXc(), MossyGate.NAME);
            if (examinable != null) {
                MossyGate mossyGate = (MossyGate) examinable;
                mossyGate.open();
                getMap().getAva().getCurrItems().remove(NAME);
                notifyObservers("The rusty key disintegrated in Dan's hand as he tried to pull it from the keyhole.");
                return true;
            }
        }
        return false;
    }

}
