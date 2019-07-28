package model.Interactables.features;

import java.util.regex.Pattern;

/*opened with rusty key*/
public class MossyGate extends Feature {

    public final static String NAME = "mossy gate";
    final static String openDescription = "the gate is now open";
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

    /*effects: opens the gate setting the blocked index to walkable*/
    public void open() {
        isOpened = true;
        map.getTileMatrix().get(yBlock).get(xBlock).setWalkable(true);
    }

    /*if rusty key is used, open door (make yBlock, xBlock walkable)*/
    @Override
    public boolean interact(String target) {
        return false;
    }

    @Override
    public boolean examine(String ui) {
        if (!isOpened && Pattern.matches("(open with|use) rusty key", ui)) {
            open();
            return true;
        }
        return false;
    }

    /* requires: gate indexes are valid on map
    effects: makes sure gate is blocked if gate isn't open*/
    @Override
    public void doPassiveActions() {
        if (!isOpened) {
            map.getTileMatrix().get(yBlock).get(xBlock).setWalkable(false);
        }
    }

}
