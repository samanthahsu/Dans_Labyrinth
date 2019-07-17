package model;

/**An interactable creature that you can fight or talk to**/
public interface Creature {

    String getName();

    String getDescription();

    int getStatus();

    int getYpos();

    int getXpos();

//    EFFECTS: moves creature to valid tile in creature specific pattern
    void move();

//    EFFECTS: creature takes special action
    void attack(Map map);

//    EFFECTS: prints creature speech
    void speak();
}
