package model.Interactables.creatures;

import model.Interactables.Interactable;
import model.Interactables.Sound;
import model.Map;

import java.util.ArrayList;

/*the one that tries to run away*/
/*if within 2 tiles of dan give indication of faint sound
* if within 1 tile, give loud sound indication*/
public class Ennui extends Creature {

    final String SOUND_NAME = "ennui sound";
    final String FAR_SOUND = "a faint scuffling in the dirt";
    final String CLOSE_SOUND = "a louder patter of tiny footsteps";

    boolean hasKey = true;
    /*effects: set coordinates, name, and description*/
    public Ennui(int y, int x) {
        super(y, x);
        name = "ennui";
        description = "a flash of turquoise fuzz in the dark";
    }

    /*effects: removes this from current tile, move to tile in calculated direction */
    @Override
    void move() {
        int oldy = currY;
        int oldx = currX;
        if (chooseDirAndMove()) {
            removeSounds(oldy, oldx);
            setSounds(currY, currX);
        }


    }

    @Override
    void attack(Map map) {

    }


    @Override
    void speak() {

    }

    /*moves one tile in a random direction (that is floor)
    if ava is in a 2 block radius of this, go in direction away from ava
    if there are no directions left to go, don't move
    emits sound in a 2 block radius of varying noise degrees and direction*/
    @Override
    public void doPassiveActions() {
        move();
    }

    /*enters instance where if ennui has key either:
    * attacks (diminishes hp of ava by one)
    * speaks chitters nervously while squishing into corner hiding the object its holding
    * runs away (if anywehre to go)
    * gets fed drops key onto tile
    *
    * OR if ennui doesn't have key (aka is fed)
    * chatters about more friendly*/
    @Override
    public boolean interact(Map map) {
        return false;
    }

    /*modifies: map
    effects: sets close sounds in max 4 floor tiles of orthog radius 1
    * sets far sounds in max 8 floor tiles of orthog radius 2, and diagonal radius 1*/
    private void setSounds(int currY, int currX){
        setOneSound(currY - 1, currX);
        setOneSound(currY + 1, currX);
        setOneSound(currY, currX - 1);
        setOneSound(currY, currX + 1);
    }

    private void setOneSound(int y, int x) {
        if (map.isIndexValid(y, x)) {
            map.getTileMatrix().get(y).get(x).getInteractables()
                    .add(new Sound(y, x, SOUND_NAME, CLOSE_SOUND));
        }
    }

    /*modifies: map
    effects: removes all sounds within scope caused by this creature*/
    private void removeSounds(int oldy, int oldx) {
        removeOneSound(currY - 1, currX);
        removeOneSound(currY + 1, currX);
        removeOneSound(currY, currX - 1);
        removeOneSound(currY, currX + 1);
    }

    /*if tile index is valid, iterate through interactables list in that tile, and remove
    * interactable named SOUND_NAME*/
    private void removeOneSound(int y, int x) {
        if (map.isIndexValid(y, x)) {
            ArrayList<Interactable> tempList = map.getTileMatrix().get(y).get(x).getInteractables();
            for (Interactable i : tempList
                 ) {
                if(i.getName().equals(SOUND_NAME)) {
                    tempList.remove(i);
                    return;
                }
            }
        }
    }

    /*modifies: this
    * effects: sets coords of this based on which directions are available
    * walls block passage
    * ava presence blocks passage*/
    private boolean chooseDirAndMove() {
        if(canMove(currY - 1, currX)) {
            executeMove(currY - 1, currX);
            return true;
        } else if (canMove(currY + 1, currX)) {
            executeMove(currY + 1, currX);
            return true;
        } else if (canMove(currY, currX + 1)) {
            executeMove(currY, currX + 1);
            return true;
        } else if (canMove(currY, currX - 1)) {
            executeMove(currY, currX - 1);
            return true;
        }
        return false;
    }

    /*remove this from current tile and add to given tile oo-ordinates*/
    private void executeMove(int nexty, int nextx) {
        map.getTileMatrix().get(currY).get(currX).getInteractables().remove(this);
        map.getTileMatrix().get(nexty).get(nextx).getInteractables().add(this);
        this.currY = nexty;
        this.currX = nextx;
    }

    /*returns true if displaychar at tile of given index is not ava or wall*/
    private boolean canMove(int y, int x) {
        char c = map.getTileMatrix().get(y).get(x).getDisplayChar();
        return c != '@' && c != '*';
    }
}
