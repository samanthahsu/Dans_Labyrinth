package model.features;

import model.Interactable;

/*An event or quirk of the environment that can be interacted with*/
abstract class Feature extends Interactable {
    public Feature(int y, int x) {
        super(y, x);
        isItem = false;
        isCreature = false;
    }
}
