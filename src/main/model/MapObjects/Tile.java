package model.MapObjects;

import model.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/*one tile in the map*/
public class Tile extends Locatable implements Serializable {

//    what character the tile will be represented as on the map
    private char currChar;
// true if character has seen the part of the map before
    private boolean isRevealed = false;
//    true if ava can be present on tile
    private boolean isWalkable = true;
//    some flavour text of what the senses pick up - usually default, can be
//    modified for providing clues
    private String description = "";
//    lists of currInteractables on tile
    private HashMap<String, Examinable> currInteractables;
    private List<Sound> tileSounds;

/* constructor
 EFFECTS: this makes a default tile with nothing in it
    sets description to one of 3 random default descriptions of an empty tile
*/
    public Tile(Map map, int y, int x, char displayChar, List<Examinable> examinables) {
        super(map, y, x);
        this.currChar = displayChar;
        initInteractables(examinables);
        tileSounds = new ArrayList<>();

        if (displayChar == Map.WALL) {
            isWalkable = false;
        }
    }

    private void initInteractables(List<Examinable> examinables) {
        HashMap<String, Examinable> hashMap = new HashMap<>();
        for (Examinable i : examinables) {
            hashMap.put(i.getName(), i);
        }
        currInteractables = hashMap;

    }

    public char getCurrChar() {
        return currChar;
    }

    public boolean isRevealed() {
        return isRevealed;
    } // todo exists for tests

    public boolean isWalkable() {
        return isWalkable;
    }

    public String getDescription() {
        return description;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public HashMap<String, Examinable> getCurrInteractables() {
        return currInteractables;
    }

    public void setCurrChar(char currChar) {
        this.currChar = currChar;
    }

    public List<Sound> getTileSounds() {
        return tileSounds;
    }

    public void addSound(Sound sound) {
        tileSounds.add(sound);
    }

    public void removeSound(String soundSource) {
        tileSounds.remove(new Sound(null, -1,-1, soundSource, ""));
    }

    //    effects: if tile is revealed return current char, else return fog char
    public char getDisplayChar() {
        if (isRevealed) {
            return currChar;
        }
        return Map.FOG;
    }

    //    MODIFIES: this
    //    EFFECTS: reveals this tile irreversibly
    public void revealTile() {
        isRevealed = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return getX() == tile.getX() &&
                getY() == tile.getY() &&
                currChar == tile.currChar &&
                isRevealed == tile.isRevealed &&
                isWalkable == tile.isWalkable &&
                Objects.equals(description, tile.description) &&
                Objects.equals(currInteractables, tile.currInteractables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), currChar, isRevealed, isWalkable, description, currInteractables);
    }

}
