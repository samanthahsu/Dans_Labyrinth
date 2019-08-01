package model.mapobjects.creatures;

/*damages sanity if stays in the same tile for too long: performing 5 actions
* */
public class Breather extends Creature {

    public static final int RESET = 0;
    public static final int START_HEARING = 2;
    public static final int DAMAGE = 4;
    public static final String NAME = "breather";
    private int status;

    Breather(int y, int x) {
        super(y, x);
        this.name = NAME;
        description = "";
        examineDescription = "";
    }

    @Override
    void move() {
    }

    @Override
    public void doPassiveActions() {
    }

    @Override
    public boolean examine(String ui) {
        return false;
    }
}
