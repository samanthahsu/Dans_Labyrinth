package model.Interactables.features;

/*opened with rusty key*/
public class MossyGate extends Feature {

    public final static String NAME = "mossy gate";
    private boolean isOpened = false;
    private int yBlock;
    private int xBlock;

    /*sets usual stuff
    * sets tile with index yBlock, xBlock to not walkable*/
    public MossyGate(int y, int x, int yblock, int xblock) {
        super(y, x);
        name = NAME;
        description = "a gate covered in so much lichen it's original material is indiscernible.";
        this.yBlock = yblock;
        this.xBlock = xblock;
    }

    public void open() {
        isOpened = true;
    }

    /*if rusty key is used, open door (make yBlock, xBlock walkable)*/
    @Override
    public boolean interact(String target) {
        return false;
    }

    /*checks gate to make sure coordinate blocked is blocked always*/
    @Override
    public void doPassiveActions() {
        if (isOpened) {
            map.getTileMatrix().get(yBlock).get(xBlock).setWalkable(true);
        } else {
            map.getTileMatrix().get(yBlock).get(xBlock).setWalkable(false);
        }
    }

}
