package model.items;

import model.Map;

/*note that is at the beginning in the same tile as ava*/
public class Note extends Item {

    public Note(int y, int x) {
        super(y, x);
        name = "note";
        description = "An address is scrawled on with neat handwriting. Note to self:";
    }

    @Override
    public boolean interact(Map map) {
        return false;
    }
}
