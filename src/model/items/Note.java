package model.items;

import model.Map;

/*note that is at the beginning in the same tile as ava*/
public class Note extends Item {



    @Override
    public boolean interact(Map map) {
        return false;
    }
}
