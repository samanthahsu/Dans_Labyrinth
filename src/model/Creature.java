package model;

public interface Creature {

    String getName();

    String getDescription();

    int getStatus();

    int getY();

    int getX();

//    EFFECTS: moves creature to valid tile in creature specific pattern
    void move();

//    EFFECTS: creature takes special action
    void attack(Map map);

//    EFFECTS: prints creature speech
    void speak();

}
