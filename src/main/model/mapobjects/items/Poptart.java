package model.mapobjects.items;

import model.mapobjects.Avatar;

import java.util.regex.Pattern;

/*can be fed to ennui*/
public class Poptart extends Item {

    public static final String NAME = "poptart";

    public Poptart() {
        name = NAME;
        description = "a brick of sugar sprinkled with dust particles";
    }

    @Override
    /*effects: can raise sanity by one,
    be fed to ennui to cause sugar high
    adds nothing of value???*/
    public boolean use(String target) {
        if (Pattern.matches("Dan", target)) {
            danEats();
            return true;
        }
        return false;
    }

    private void danEats() {
        notifyObservers("Dan swiftly ends the shelf life of the sweet centenarian.");
        Avatar ava = map.getAva();
        int newStat = ava.getSanity() + 1;
        if (newStat <= 3) {
            ava.setSanity(newStat);
            notifyObservers("Dan feels his blood sugar levels spike.");
            ava.getCurrItems().remove(this.name);
        }
    }
}
