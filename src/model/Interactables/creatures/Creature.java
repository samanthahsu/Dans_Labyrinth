package model.Interactables.creatures;

import model.Interactables.Doer;
import model.Map;

/**An interactable creature that you can fight or talk to**/
public abstract class Creature extends Doer {
    int health;

    Creature(int y, int x) {
        super(y, x);
        typeId = TYPE_CREATURE;
    }
    public int getHealth() {
        return health;
    }

    //    EFFECTS: moves creature to valid tile in creature specific pattern
    abstract void move();

//    EFFECTS: creature takes special action
    abstract void attack(Map map);

//    EFFECTS: prints creature speech
    abstract void speak();

}

