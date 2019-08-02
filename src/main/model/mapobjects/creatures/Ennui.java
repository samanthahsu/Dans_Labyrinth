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
    public static final String DESCRIPTION = "there is a flash of turquoise fuzz in the dark";
    public static final String EXAMINE_DESCRIPTION = "A small mole like animal with a star shaped nose"
            + "is flattened against the dirt floor.\nBarely concealed by it's paws is a flat "
            + "rusted piece of metal resembling a lollipop.";
    final String soundDescription = "a patter of tiny footsteps";
    public static final String NAME = "ennui";

    boolean hasKey = true;
    SoundManager soundManager;

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
        int oldY = getYc();
        int oldX = getXc();
        if (isAvaOnSameTile()) {
            return;
        }
        if (chooseDirAndMove()) {
            removeSounds(oldY, oldX);
            setSounds(getYc(), getXc());
            // todo test stuffs VVVV
            getMap().getTileMatrix().get(oldY).get(oldX).setCurrChar(' ');
            getMap().getTileMatrix().get(getYc()).get(getXc()).setCurrChar('E');
            System.out.println("moved from" + oldY + " " + oldX + " to " + getYc() + " " + getXc());
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
    * runs away (if anywehre to go)
    * gets fed drops key onto tile
    *
    * OR if ennui doesn't have key (aka is fed)
    * chatters about more friendly*/

    /*modifies: map
    effects: sets close sounds in max 4 FLOOR tiles of orthog radius 1
    * sets far sounds in max 8 FLOOR tiles of orthog radius 2, and diagonal radius 1*/
    private void setSounds(int currY, int currX) {
        soundManager.setSurroundSound(currY, currX, name, description);
//        addSound(currY - 1, currX);
//        addSound(currY + 1, currX);
//        addSound(currY, currX - 1);
//        addSound(currY, currX + 1);
    }

//    private void addSound(int y, int x) {
//        if (getMap().isIndexValid(y, x)) {
//            getMap().getTileMatrix().get(y).get(x)
//                    .addSound(new Sound(getMap(), y, x, NAME, soundDescription));
//        }
//    }

    /*modifies: map
    effects: removes all sounds within scope caused by this creature*/
    private void removeSounds(int oldy, int oldx) {
        soundManager.removeAllSounds(oldy, oldx, getYc(), getXc(), name);
//        int currentY = getYc();
//        int currentX = getXc();
//        removeOneSoundAtPos(currentY - 1, currentX);
//        removeOneSoundAtPos(currentY + 1, currentX);
//        removeOneSoundAtPos(currentY, currentX - 1);
//        removeOneSoundAtPos(currentY, currentX + 1);
    }

//    /*effects: if tile index is valid, iterate through interactables list in that tile, and remove
//    * interactable named SOUND_NAME*/
//    private void removeOneSoundAtPos(int y, int x) {
//        if (getMap().isIndexValid(y, x)) {
//            getMap().getTileMatrix().get(y).get(x).removeSound(name);
////            Collection<Sound> soundCollection = getMap().getTileMatrix().get(y).get(x).getTileSounds();
////            for (Sound sound : soundCollection
////                 ) {
////                if(sound.getSourceName().equals(NAME)) {
////                    soundCollection.remove(sound);
////                    return;
////                }
////            }
//        }
//    }

    /* modifies: this
    * effects: todo
    * if ava is on the same tile, return false
    * moves N>S>E>W depending on which direction is available
    * walls block passage in 1 rad*/
    private boolean chooseDirAndMove() {
        int currentY = getYc();
        int currentX = getXc();
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
        Avatar ava = getMap().getAva();
        if (ava.getYc() == getYc() && ava.getXc() == getXc()) {
            System.out.println("A basketball sized blue pompom is quivering on the FLOOR in front of Dan.");
            return true;
        }
        return false;
    }

    /*modifies: this, map
    effects: remove this from current tile, adds this to interactables in next tile
    * sets current coordinates to next coordinates*/
    private void executeMove(int nextY, int nextX) {
        List<List<Tile>> tileMatrix = getMap().getTileMatrix();
        tileMatrix.get(nextY).get(nextX).getCurrExaminables().put(name, this);
        tileMatrix.get(getYc()).get(getYc()).getCurrExaminables().remove(name);
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
            System.out.println("Dan pries the rusty key out of its little hands.");
            return true;
        } else if (!hasKey | Pattern.matches("(take|get) .*", action)) {
            System.out.println("Dan has no reason to disturb the terrified animal");
//            scampers away or something
        }
        return false;
    }

    /*requires: ennui has key
    * effects: takes key from ennui, and adds rusty key to player items*/
    private void takeKey() {
        hasKey = false;
        getMap().getAva().getCurrItems().put(RustyKey.NAME, new RustyKey());
    }
}