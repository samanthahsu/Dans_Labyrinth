package model.mapobjects.items;

import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.features.LastGate;

import java.util.regex.Pattern;

/*starting box, found nowhere else in maze*/
/*in this edition of pizza fixes everything*/
public class PizzaBox extends Item {

    public static final String NAME = "pizzabox";
    //    number of slices left in the box
    static final int JUST_A_BOX = 0;
    static final String EMPTY_NAME = "pizzabox";
    public static final String EMPTY_DESCRIPTION = "A sad, empty, grease stained cardboard pizza box.";
    private int slices = 8;

    /*
    EFFECTS: constructs pizza as a held item
    */
    public PizzaBox() {
        name = NAME; // todo separate description from detailed examination description
        description = "A pristine cardboard box displaying the umber and mahogany original 'Danminos' "
                + "company logo\n"
                + slices + " slices of toasty deluxe pizza are nestled inside.";
    }

    @Override
    public String getDescription() {
        if (slices == JUST_A_BOX) {
            return EMPTY_DESCRIPTION;
        } else {
            return "A pristine cardboard box displaying the umber and mahogany original 'Danminos' "
                    + "company logo.\n"
                    + slices + " slices of toasty deluxe pizza are nestled inside.";
        }
    }

    // MODIFIES: map(avatar)
// EFFECTS: iff slices > 0, slice is taken from box
// user sanity is recovered by one point todo
    @Override
    public boolean use(String target) {
        if (Pattern.matches(Avatar.NAME, target)) {
            targetDan();
            return true;
        } else if (target.equals(LastGate.NAME)) {
            return targetLastGate();
        }
        return false;
    }

    private boolean targetLastGate() {
        Examinable examinable = map.tileFetchExaminable(map.getAva().getYc(),
                map.getAva().getXc(), LastGate.NAME);
        if (examinable != null) {
            LastGate lastGate = (LastGate) examinable;
            lastGate.open();
            getMap().getAva().getCurrItems().remove(NAME);
            notifyObservers("Dan places the box into the square inset. It fits perfectly.");
            notifyObservers("The gate rumbles open and light floods the caverns.");
            return true;
        }
        return false;
    }

    private void targetDan() {
        if (slices == JUST_A_BOX) {
            notifyObservers("Dan sadly remembers that there is no more pizza."
                    + " He resists eating the box.");
        } else {
            slices--;
            notifyObservers("Warm cheese melts in his mouth."
                    + "\nHe will have to do an extra half km to make up for it later");
            Avatar ava = getMap().getAva();
            int newStat = ava.getSanity() + 1;
            if (newStat <= 3) {
                ava.setSanity(newStat);
                notifyObservers("Dan feels his sanity return slightly.");
            }
            if (slices == 0) {
                map.getAva().atePizza();
            }
        }
    }
}
