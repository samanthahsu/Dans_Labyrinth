package model.creatures;

import model.Interactable;
import model.Map;

/**An interactable creature that you can fight or talk to**/
abstract class Creature extends Interactable {
    int status;

    public Creature(int y, int x) {
        super(y, x);
        isCreature = true;
        isItem = false;
    }
    public int getStatus() {
        return status;
    }

    //    EFFECTS: moves creature to valid tile in creature specific pattern
    abstract void move(Map map);

//    EFFECTS: creature takes special action
    abstract void attack(Map map);

//    EFFECTS: prints creature speech
    abstract void speak();
}
