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
    public int getStartY() {
        return 0;
    }

    @Override
    public int getStartX() {
        return 0;
    }

    @Override
    public void move() {

    }

    @Override
    public void attack() {

    }

    @Override
    public void speak() {

    }
}
