package model.Interactables.items;

import model.Avatar;

/*starting box, found nowhere else in maze*/
/*in this edition of pizza fixes everything*/
public class PizzaBox extends Item {

//    number of slices left in the box
    final int JUST_A_BOX = 0;
    final String EMPTY_NAME = "pizzabox";
    final String EMPTY_DESCRIPTION = "A sad, empty, grease stained cardboard pizza box.";
    int slices = 8;
/*
    EFFECTS: constructs pizza as a held item
*/
    public PizzaBox() {
        super();
        name = "pizza"; // todo separate description from detailed examination description
        description = "A pristine cardboard box with the umber and mahogany original 'Danminos' company logo on the sides with "
                + slices + " toasty deluxe pizza slices nestled inside.";
    }

// MODIFIES: map(avatar)
// EFFECTS: iff slices > 0, slice is taken from box
// user sanity is recovered by one point
    @Override
    public boolean interact(String target) {
        if (slices == JUST_A_BOX) {
            System.out.println("Dan sadly remembers that there is no more pizza."
                    + " He resists eating the box");
        } else {
            slices--;
            System.out.println("Warm cheese melts in Dan's mouth.");
            Avatar ava = map.getAva();
            int newStat = ava.getStatus() + 1;
            if (newStat <= 3) {
                ava.setStatus(newStat);
                System.out.println("Dan feels his sanity return slightly. Dan feels a slight pang of guilt, "
                        + "he will have to do an extra half km to make up for it later");
            }
            return false;
        }
        return true;
    }
}
