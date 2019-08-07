package model.mapobjects.features;

import model.mapobjects.items.RustyKey;

/*opened with rusty key*/
public class MossyGate extends Gate {

    public static final String NAME = "mossy-gate";
//    static final String openDescription = "the gate is now open";

    /*sets usual stuff
    * sets tile with index yBlock, xBlock to not walkable*/
    public MossyGate(int y, int x, int yblock, int xblock) {
        super(y, x, yblock, xblock);
        name = NAME;
        description = "a gate covered in so much lichen it's original material is indiscernible.";
        examineDescription = "A small keyhole is barely visible on the gate.";
    }

    //    effects: interaction with to open belongs to the key
    @Override
    public boolean examine(String ui) { //todo
        if (!isOpened() && ui.equals("use " + RustyKey.NAME)) {
            map.getAva().useItem(RustyKey.NAME, NAME);
            return true;
        }
        return false;
    }

    /* requires: gate indexes are valid on map
    effects: makes sure gate is blocked if gate isn't open*/
    @Override
    public void doPassiveActions() {
        super.doPassiveActions();
    }

}
