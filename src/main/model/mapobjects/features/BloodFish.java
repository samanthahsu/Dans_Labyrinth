package model.mapobjects.features;

import java.util.regex.Pattern;

/*an oval with a triangle in at the end drawn with something darker and thicker than water*/
public class BloodFish extends Feature {

    public static final String NAME = "bloodfish";
    private boolean hasNodded = false;

    public BloodFish(int y, int x) {
        super(y, x);
        name = NAME;
        description = "a symbol is painted on the wall just above eye level";
        examineDescription = "an oval with a triangle in at the end drawn with something darker and thicker than water";
    }

    @Override
    /*effects: if user typed "this is a red herring" or something related for the
    * first time, print out acknowledgement and do nothing afterwards*/
    public boolean examine(String ui) {
        if (!hasNodded && Pattern.matches("(this is |its |it's )?(a )?red herring", ui)) {
            hasNodded = true;
            notifyObservers("The fish seems to nod in agreement.");
            return true;
        }
        return false;
    }
}
