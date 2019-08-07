package model.mapobjects.creatures;

import model.Map;
import model.mapobjects.Avatar;
import model.mapobjects.SoundManager;
import model.mapobjects.Tile;
import model.mapobjects.items.RustyKey;

import java.util.List;
import java.util.regex.Pattern;

/*the one that tries to run away*/
/*if within 2 tiles of dan give indication of faint sound
* if within 1 tile, give loud sound indication*/
public class Ennui extends Creature {

    public static final String SOUND_NAME = "ennui sound";
    public static final String DESCRIPTION = "A basketball sized blue pompom is "
            + "quivering on the floor in front of Dan.";
    public static final String EXAMINE_DESCRIPTION =
            "A small mole like animal with a star shaped nose is flattened against the dirt floor."
            + "\nBarely concealed by it's paws is a flat, rusted piece of metal resembling a lollipop.";
    final String soundDescription = "a patter of tiny footsteps";
    public static final String NAME = "ennui";

    private boolean hasKey = true;
    private SoundManager soundManager;

    /*effects: set coordinates, name, and description*/
    public Ennui(int y, int x) {
        super(y, x);
        name = NAME;
        description = DESCRIPTION;
        examineDescription = EXAMINE_DESCRIPTION;
    }

    /*effects: removes this from current tile, move to tile in calculated direction */
    @Override
    void move() {
        int oldY = yc;
        int oldX = xc;
        if (isAvaOnSameTile()) {
            return;
        }
        if (chooseDirAndMove()) {
            removeSounds(oldY, oldX);
            setSounds(yc, xc);
            getMap().getTileMatrix().get(oldY).get(oldX).setCurrChar(' ');
        }
    }

//    todo temp bandaid fix init inside constructor
    @Override
    public void setMap(Map map) {
        super.setMap(map);
        soundManager = new SoundManager(getMap());
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
    * runs away (if anywhere to go)
    * gets fed drops key onto tile
    *
    * OR if ennui doesn't have key (aka is fed)
    * chatters about more friendly*/

    /*modifies: map
    effects: sets close sounds in max 4 FLOOR tiles of orthog radius 1
    * sets far sounds in max 8 FLOOR tiles of orthog radius 2, and diagonal radius 1*/
    private void setSounds(int currY, int currX) {
        soundManager.setSurroundSound(currY, currX, name, soundDescription);
    }

    /*modifies: map
    effects: removes all sounds within scope caused by this creature*/
    private void removeSounds(int oldy, int oldx) {
        soundManager.removeAllSounds(oldy, oldx, yc, xc, name);
    }

    /* modifies: this
    * effects: todo
    * if ava is on the same tile, return false
    * moves N>S>E>W depending on which direction is available
    * walls block passage in 1 rad*/
    private boolean chooseDirAndMove() {
        if (canMove(yc - 1, xc)) {
            executeMove(yc - 1, xc);
            return true;
        } else if (canMove(yc + 1, xc)) {
            executeMove(yc + 1, xc);
            return true;
        } else if (canMove(yc, xc + 1)) {
            executeMove(yc, xc + 1);
            return true;
        } else if (canMove(yc, xc - 1)) {
            executeMove(yc, xc - 1);
            return true;
        }
        return false;
    }

    /*effects: returns true if avatar within one tile*/
    private boolean isAvaOnSameTile() {
        Avatar ava = getMap().getAva();
        return ava.getYc() == yc && ava.getXc() == xc;
    }

    /*modifies: this, map
    effects: remove this from current tile, adds this to interactables in next tile
    * sets current coordinates to next coordinates*/
    private void executeMove(int nextY, int nextX) {
        List<List<Tile>> tileMatrix = getMap().getTileMatrix();
        tileMatrix.get(nextY).get(nextX).getCurrExaminables().put(name, this);
        tileMatrix.get(yc).get(yc).getCurrExaminables().remove(name);
        setYc(nextY);
        setXc(nextX);
    }

    /*effects: returns true if display char at tile of given index is not ava and walkable is true*/
    private boolean canMove(int y, int x) {
        Tile tile = getMap().getTileMatrix().get(y).get(x);
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
        if (Pattern.matches("(take|get) (rusty |rusted )?key", action)) {
            takeKey();
            notifyObservers("Dan pries the " + RustyKey.NAME + " out of its little hands.");
        } else if (!hasKey | Pattern.matches("(take|get) .*", action)) {
            notifyObservers("Dan has no reason to disturb the terrified animal");
//            scampers away or something
        } else {
            return false;
        }
        return true;
    }

    /*requires: ennui has key
    * effects: takes key from ennui, and adds rusty key to player items*/
    private void takeKey() {
        hasKey = false;
        RustyKey key = new RustyKey();
        key.setMap(map);
        getMap().getAva().getCurrItems().put(RustyKey.NAME, key);
    }
}
