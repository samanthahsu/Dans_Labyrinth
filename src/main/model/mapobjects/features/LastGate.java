package model.mapobjects.features;

import model.mapobjects.items.PizzaBox;

/*the one which you open with the box*/
public class LastGate extends Gate {

    public static final String NAME = "square gate";

    public LastGate(int y, int x, int yblock, int xblock) {
        super(y, x, yblock, xblock);
        name = NAME;
        description = "A wall of rough corrugated material.";
        examineDescription = "There is a familiar shallow square inset in the center of the gate.\n"
            + "\"Requires a sacrifice of great knowledge\"\nBelow were drawings of figures holding stone tablets"
            + "with dense texts of inscrutable language on them.";
    }

    @Override
/*effects: returns true if accepted input is entered*/
    public boolean examine(String ui) {
        if (!isOpened() && ui.equals("use " + PizzaBox.NAME)) {
            map.getAva().useItem(PizzaBox.NAME, NAME);
            return true;
        }
        return false;
    }
}
