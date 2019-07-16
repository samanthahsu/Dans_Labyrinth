package model;

// placeholder creature to initialize interactables in map object
public class NullCreature extends Interactable implements Creature {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getStatus() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public void move() {

    }

    @Override
    public void attack(Map map) {

    }

    @Override
    public void speak() {

    }
}
