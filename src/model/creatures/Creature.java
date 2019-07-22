package model.creatures;

import model.Interactable;
import model.Map;

/**An interactable creature that you can fight or talk to**/
public abstract class Creature extends Interactable /*implements List<Creature> todo find out what this does*/ {
    int health;

    Creature(int y, int x) {
        super(y, x);
        typeId = TYPE_CREATURE;
    }
    public int getHealth() {
        return health;
    }

    //    EFFECTS: moves creature to valid tile in creature specific pattern
    abstract void move(Map map);

//    EFFECTS: creature takes special action
    abstract void attack(Map map);

//    EFFECTS: prints creature speech
    abstract void speak();

//    EFFECTS: does default moves, like move around
    public abstract void doPassiveActions();
}

