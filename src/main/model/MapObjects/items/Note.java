package model.MapObjects.items;

/*note that is at the beginning in the same tile as ava*/
public class Note extends Item {

    public final static String NAME = "note";

    public Note(int y, int x) {
        super(y, x);
        name = NAME;
        description = "An address is scrawled on with neat handwriting. Note to self:";
    }

    @Override
    public boolean use(String target) {
        return false;
    }
}
