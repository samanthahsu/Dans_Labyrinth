package model.mapobjects.features;

import model.mapobjects.Doer;
import model.mapobjects.Examinable;

/*An event or quirk of the environment that can be interacted with*/
public abstract class Feature extends Examinable implements Doer {
    public Feature(int y, int x) {
        super(y, x);
        typeId = TYPE_FEATURE;
    }

    @Override
    public void doPassiveActions() {
    }
}
