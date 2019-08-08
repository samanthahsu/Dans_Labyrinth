package model.mapobjects.items;

import java.util.regex.Pattern;

/*note that is at the beginning in the same tile as ava*/
public class Note extends Item {

    public static final String NAME = "note";
    private static final String READ_BACK = "\"LURD\" is scrawled onto the back in... ink.";

    public Note(int y, int x) {
        super(y, x);
        name = NAME;
        description = "A scrap of white paper with something written on it.";
        examineDescription = "Delivery information is neatly penciled in Dan's own handwriting on the front.";
    }

    @Override
    public String getDescription() {
        if (map.getAva().getCurrItems().containsKey(NAME)) {
            return examineDescription + "\n" + READ_BACK;
        } else {
            return examineDescription;
        }
    }

    /*effects: note can't be used on anything*/
    @Override
    public boolean use(String target) {
        if (Pattern.matches("(D|d)an", target)) {
            notifyObservers(READ_BACK);
            return true;
        }
        return false;
    }

    @Override
    public boolean examine(String ui) {
        if (Pattern.matches("(((flip|turn) over)|(read|examine|view|look at)) back( of (the)?note)?", ui)) {
            map.getAva().pickUpItem(NAME);
            notifyObservers(READ_BACK);
            return true;
        }
        return false;
    }
}
