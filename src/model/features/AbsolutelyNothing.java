package model.features;

import model.Map;

/*this class represents unfilled spaces of the interactable matrix held in map*/
public class AbsolutelyNothing extends Feature {

//    EFFECTS: constructs AbsolutelyNothing with 0,0 as coordinates
    public AbsolutelyNothing() {
        super(0, 0);
    }

//    EFFECTS: prints "There is nothing here..."
    @Override
    public void interact(Map map) {
        System.out.println("There is nothing here...");
    }
}
