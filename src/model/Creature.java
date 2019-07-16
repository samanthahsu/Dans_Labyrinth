package model;

public interface Creature {

    String getName();

    String getDescription();

    int getStatus();

    int getStartY();

    int getStartX();

//    EFFECTS: moves creature to valid tile in creature specific pattern
    void move();

//    EFFECTS: creature takes special action
    void attack();

//    EFFECTS: prints creature speech
    void speak();

}
