package model;

public interface Creature {

    String getName();

    String getDescription();

    int getStatus();

    int getStartY();

    int getStartX();

    void move();

    void attack();

    void speak();

}
