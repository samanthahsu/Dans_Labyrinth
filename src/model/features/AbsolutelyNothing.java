package model.features;

import model.Map;

/*this class represents unfilled spaces of the interactable matrix held in map*/
public class AbsolutelyNothing extends Feature {

    public AbsolutelyNothing() {
        super(0, 0);
    }

    @Override
    public void interact(Map map) {
        System.out.println("There is nothing here...");
    }
}
