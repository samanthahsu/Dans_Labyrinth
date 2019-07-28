package model.Interactables.creatures;

import model.Avatar;
import model.Interactables.Interactable;
import model.Interactables.Sound;
import model.Interactables.items.RustyKey;
import model.Map;
import model.Tile;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

/*the one that tries to run away*/
/*if within 2 tiles of dan give indication of faint sound
* if within 1 tile, give loud sound indication*/
public class Ennui extends Creature {

    public final static String SOUND_NAME = "ennui sound";
    final String SOUND_DESCRIPTION = "a patter of tiny footsteps";
    public static final String NAME = "ennui";

    boolean hasKey = true;
    /*effects: set coordinates, name, and description*/
    public Ennui(int y, int x) {
        super(y, x);
        name = NAME;
        description = "there is a flash of turquoise fuzz in the dark";
        examineDescription = "A small mole like animal with a star shaped nose" +
                "is flattened against the dirt floor.\nBarely concealed by it's paws is a flat " +
                "rusted piece of metal resembling a lollipop.";
    }

    /*effects: removes this from current tile, move to tile in calculated direction */
    @Override
    void move() {
        int oldY = currentY;
        int oldX = currentX;
        if (isAvaOnSameTile()) {
            return;
        }
        if (chooseDirAndMove()) {
            removeSounds(oldY, oldX);
            setSounds(currentY, currentX);
            // todo test stuffs VVVV
            map.getTileMatrix().get(oldY).get(oldX).setCurrChar(' ');
            map.getTileMatrix().get(currentY).get(currentX).setCurrChar('E');
            System.out.println("moved from" + oldY + " " + oldX + " to " + currentY +" "+ currentX);
        }
    }

    @Override
    void attack(Map map) {

    }


    @Override
    void speak() {

    }

    /*moves one tile in a random direction (that is FLOOR)
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
    public boolean interact(String target) {
        return false;
    }

    /*modifies: map
    effects: sets close sounds in max 4 FLOOR tiles of orthog radius 1
    * sets far sounds in max 8 FLOOR tiles of orthog radius 2, and diagonal radius 1*/
    private void setSounds(int currY, int currX){
        setOneSound(currY - 1, currX);
        setOneSound(currY + 1, currX);
        setOneSound(currY, currX - 1);
        setOneSound(currY, currX + 1);
    }

    private void setOneSound(int y, int x) {
        if (map.isIndexValid(y, x)) {
            map.getTileMatrix().get(y).get(x).getCurrInteractables()
                    .put(SOUND_NAME, new Sound(y, x, SOUND_NAME, SOUND_DESCRIPTION));
        }
    }

    /*modifies: map
    effects: removes all sounds within scope caused by this creature*/
    private void removeSounds(int oldy, int oldx) {
        removeOneSound(currentY - 1, currentX);
        removeOneSound(currentY + 1, currentX);
        removeOneSound(currentY, currentX - 1);
        removeOneSound(currentY, currentX + 1);
    }

    /*effects: if tile index is valid, iterate through interactables list in that tile, and remove
    * interactable named SOUND_NAME*/
    private void removeOneSound(int y, int x) {
        if (map.isIndexValid(y, x)) {
            Collection<Interactable> tempList = map.getTileMatrix().get(y).get(x).getCurrInteractables().values();
            for (Interactable i : tempList
                 ) {
                if(i.getName().equals(SOUND_NAME)) {
                    tempList.remove(i);
                    return;
                }
            }
        }
    }

    /* modifies: this
    * effects: todo
    * if ava is on the same tile, return false
    * moves N>S>E>W depending on which direction is available
    * walls block passage in 1 rad*/
    private boolean chooseDirAndMove() {
            if (canMove(currentY - 1, currentX)) {
                executeMove(currentY - 1, currentX);
                return true;
            } else if (canMove(currentY + 1, currentX)) {
                executeMove(currentY + 1, currentX);
                return true;
            } else if (canMove(currentY, currentX + 1)) {
                executeMove(currentY, currentX + 1);
                return true;
            } else if (canMove(currentY, currentX - 1)) {
                executeMove(currentY, currentX - 1);
                return true;
            }
            return false;
    }

    /*effects: returns true if avatar within one tile*/
    private boolean isAvaOnSameTile() {
        Avatar ava = map.getAva();
        if (ava.getCurrY() == currentY && ava.getCurrX() == currentX) {
            System.out.println("A basketball sized blue pompom is quivering on the FLOOR in front of Dan.");
            return true;
        }
        return false;
    }

    /*modifies: this, map
    effects: remove this from current tile, adds this to interactables in next tile
    * sets current coordinates to next coordinates*/
    private void executeMove(int nextY, int nextX) {
        List<List<Tile>> tileMatrix = map.getTileMatrix();
        tileMatrix.get(nextY).get(nextX).getCurrInteractables().put(name, this);
        tileMatrix.get(currentY).get(currentX).getCurrInteractables().remove(name);
        this.currentY = nextY;
        this.currentX = nextX;
    }

    /*effects: returns true if display char at tile of given index is not ava and walkable is true*/
    private boolean canMove(int y, int x) {
        Tile tile = map.getTileMatrix().get(y).get(x);
        char c = tile.getCurrChar();
        boolean walkable = tile.isWalkable();
        return walkable && c != '*';
    }

    /*requires: player is on the same tile
    * modifies: this
    * effects: starts instance where player can try and take key
    * returns true if action is valid, else return false*/
    @Override
    public boolean examine(String action) {
        if (!hasKey | Pattern.matches("(take|get) .*", action)) {
            System.out.println("Dan has no reason to disturb the terrified animal");
//            scampers away or something
        } else if (Pattern.matches("(take|get) (rusty |rusted )?key", action)) {
            takeKey();
            System.out.println("Dan pries the rusty key out of its little hands.");
            return true;
        }
        return false;
    }

    /*requires: ennui has key
    * effects: takes key from ennui, and adds rusty key to player items*/
    private void takeKey() {
        hasKey = false;
        map.getAva().getCurrItems().put(RustyKey.NAME, new RustyKey());
    }
}
