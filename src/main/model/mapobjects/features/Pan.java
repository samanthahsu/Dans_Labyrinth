package model.mapobjects.features;

import model.mapobjects.items.Bones;

import java.util.regex.Pattern;

public class Pan extends Feature {

    public static final String ACTUAL_NAME = "pan";
    public static final String FIRST_NAME = "bones";
    public static String NAME;

    private int bones = 7;

    public Pan(int y, int x) {
        super(y, x);
        NAME = FIRST_NAME;
        name = NAME;
        description = "A pile of bones";
        examineDescription = "A ribcage, and skull and other bones sit half-buried in the dirt "
                + "beside a neatly folded \"uniform\".";
    }


    @Override
    /*requires:
     * modifies
     * effects prints out detailed descriptions, you can take a bone for no reason
     * (until you take whole skele and bring him
     * out for proper funeral)*/
    public boolean examine(String ui) {
        if (Pattern.matches("(examine |look at )uniform", ui)) {
            notifyObservers("A torn umber and mahogany shirt with the nametag \"Pan\" sewn onto the breast.");
            NAME = ACTUAL_NAME;
            name = NAME;
            return true;
        } else if (Pattern.matches("(take | pickup |pick up)(bone(s)?)", ui)) {
            takeBone();
            return true;
        }
        return false;
    }

    private void takeBone() {
        if (bones == 0) {
            notifyObservers("That's it, you've robbed this helpless corpse blind.");
        } else if (bones == 7) {
            bones--;
            notifyObservers("Dan takes a bone from the pile... don't ask why.");
            Bones bones = new Bones();
            bones.setMap(map);
            getMap().getAva().getCurrItems().put(Bones.NAME, bones);
        } else if (bones > 1) {
            bones--;
            ((Bones) getMap().getAva().getCurrItems().get(Bones.NAME)).addBone();
            notifyObservers("Dan takes a bone from the pile... don't ask why.");
        } else { // bones == 1
            bones = 0;
            ((Bones) getMap().getAva().getCurrItems().get(Bones.NAME)).addBone();
            description = "A neatly folded \"uniform\"";
            getMap().getAva().hasPan();
        }
    }
}
