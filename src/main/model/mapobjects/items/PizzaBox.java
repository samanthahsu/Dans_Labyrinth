package model.mapobjects.items;

import model.mapobjects.Avatar;

/*starting box, found nowhere else in maze*/
/*in this edition of pizza fixes everything*/
public class PizzaBox extends Item {

    public static final String NAME = "pizzabox";
    //    number of slices left in the box
    static final int JUST_A_BOX = 0;
    static final String EMPTY_NAME = "pizzabox";
    static final String EMPTY_DESCRIPTION = "A sad, empty, grease stained cardboard pizza box.";
    int slices = 8;

    /*
    EFFECTS: constructs pizza as a held item
    */
    public PizzaBox() {
        name = NAME; // todo separate description from detailed examination description
        description = "A pristine cardboard box with the umber and mahogany original 'Danminos' "
                + "company logo on the sides with "
                + slices + " toasty deluxe pizza slices nestled inside.";
    }

// MODIFIES: map(avatar)
// EFFECTS: iff slices > 0, slice is taken from box
// user sanity is recovered by one point todo
    @Override
    public boolean use(String target) {
        if (slices == JUST_A_BOX) {
            System.out.println("Dan sadly remembers that there is no more pizza."
                    + " He resists eating the box");
        } else {
            slices--;
            System.out.println("Warm cheese melts in Dan's mouth.");
            Avatar ava = getMap().getAva();
            int newStat = ava.getSanity() + 1;
            if (newStat <= 3) {
                ava.setSanity(newStat);
                System.out.println("Dan feels his sanity return slightly. Dan feels a slight pang of guilt, "
                        + "he will have to do an extra half km to make up for it later");
            }
            return true;
        }
        return false;
    }
}
