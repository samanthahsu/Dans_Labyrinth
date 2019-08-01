package model.MapObjects.items;

/*note that is at the beginning in the same tile as ava*/
public class Note extends Item {

    public final static String NAME = "note";

    public Note(int y, int x) {
        super(y, x);
        name = NAME;
        description = "An address is scrawled on with neat handwriting";
        examineDescription = "'Uptown Reft Town, Colstorworth, CV3 8RN... \n1 lrg mushrm'";
    }

    /*effects: note can't be used on anything*/
    @Override
    public boolean use(String target) {
        return false;
    }
}
