package model.MapObjects.items;

import model.Map;

/*note that is at the beginning in the same tile as ava*/
public class Note extends Item {

    public Note(Map map, int y, int x) {
        super(map, y, x);
        name = "note";
        description = "An address is scrawled on with neat handwriting. Note to self:";
    }
}
