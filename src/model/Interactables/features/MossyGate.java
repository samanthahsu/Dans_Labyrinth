package model.Interactables.features;

import model.Map;

/*opened with rusty key*/
public class MossyGate extends Feature {

    private boolean isOpened = false;
    int yblock;
    int xblock;

    /*sets usual stuff
    * sets tile with index yblock, xblock to not walkable*/
    public MossyGate(int y, int x, int yblock, int xblock) {
        super(y, x);
        name = "mossy gate";
        description = "a gate covered in so much lichen it's original material is indiscernible.";
        this.yblock = yblock;
        this.xblock = xblock;
    }

    /*if rusty key is used, open door (make yblock, xblock walkable)*/
    @Override
    public boolean interact(Map map) {
        return false;
    }

    /*checks gate to make sure coordinate blocked is blocked always*/
    @Override
    public void doPassiveActions() {
        if (isOpened) {
            map.getTileMatrix().get(yblock).get(xblock).setWalkable(true);
        } else {
            map.getTileMatrix().get(yblock).get(xblock).setWalkable(false);
        }
    }
}
