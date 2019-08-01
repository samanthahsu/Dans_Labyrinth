package model.MapObjects.features;

/*an oval with a triangle in at the end drawn with something darker and thicker than water*/
public class BloodFish extends Feature {

    public static final String NAME = "bloodfish";

    public BloodFish(int y, int x) {
        super(y, x);
        name = NAME;
        description = "a symbol is painted on the wall just above eye level";
        examineDescription = "an oval with a triangle in at the end drawn with something darker and thicker than water";
    }

    @Override
    public void doPassiveActions() {
    }

    @Override
    public boolean examine(String ui) {
        return false;
    }
}
