package model.items;

import model.Avatar;
import model.Map;

/*starting box, found nowhere else in maze*/
/*in this edition of pizza fixes everything*/
public class PizzaBox extends Item {

//    number of slices left in the box
    int slices = 8;
/*
    EFFECTS: constructs pizza as a held item
*/
    public PizzaBox() {
        super();
        name = "pizzabox";
        description = "A proudly pristine cardboard box with the yellow and mahogany original 'Danminos' company logo on the sides with "
                + slices + " toasty deluxe pizza slices nestled inside.";
    }

// MODIFIES: map(avatar)
// EFFECTS: User health is recovered by one point and apple is removed from items
    @Override
    public boolean interact(Map map) {
        System.out.println("Warm cheese melts in Dan's mouth.");
        Avatar ava = map.getAva();
        int newStat = ava.getStatus() + 1;
        if (newStat <= 3) {
            ava.setStatus(newStat);
            System.out.println("Dan feels his sanity return slightly. Dan feels a slight pang of guilt, "
                    + "he will have to do an extra half km to make up for it later");
        }
        return true;
    }
}
