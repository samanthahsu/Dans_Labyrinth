package model.mapobjects.features;

import java.util.regex.Pattern;

public class Pan extends Feature {


    public static final String NAME = "bones";
    private int bones = 7;
    private String boneDescription;
    private String uniformDescription = "The nametag on the orange uniform's breast says 'Pan'";

    public Pan(int y, int x) {
        super(y, x);
        name = NAME;
        description = "A pile of bones";
        examineDescription = "A ribcage, and skull and other bones sit half-buried in the dirt "
                + "beside a neatly folded uniform.";
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
            return true;
        }
        return false;
    }
}
