package model.items;

import model.Avatar;
import model.Map;

public class Apple extends Item {

//    EFFECTS: constructs apple as a held item
    public Apple() {
        super(0,0);
        name = "apple";
        description = "Slightly bruised as the best ones are.";
    }

    //    EFFECTS: construct apple with given coordinates
    public Apple(int y, int x) {
        super(y, x);
        name = "apple";
        description = "Slightly bruised as the best ones are.";
    }

// MODIFIES: map(avatar)
// EFFECTS: User health is recovered by one point and apple is removed from items
    @Override
    public boolean interact(Map map) {
        System.out.println("nom nom nom");
        Avatar ava = map.getAva();
        int newStat = ava.getStatus() + 1;
        if (newStat <= 3) {
            ava.setStatus(newStat);
            System.out.println("tastes wormy");
        }
        return true;
    }
}
