package model.MapObjects.features;

import model.MapObjects.Doer;

/*An event or quirk of the environment that can be interacted with*/
public abstract class Feature extends Doer {
    public Feature(int y, int x) {
        super(y, x);
        typeId = TYPE_FEATURE;
    }
}
