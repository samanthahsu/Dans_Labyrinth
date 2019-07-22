package model.Interactables.features;

import model.Interactables.Interactable;

/*An event or quirk of the environment that can be interacted with*/
public abstract class Feature extends Interactable {
    public Feature(int y, int x) {
        super(y, x);
        typeId = TYPE_FEATURE;
    }
}
