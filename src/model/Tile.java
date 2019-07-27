package model;

import model.Interactables.Interactable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/*Holds information things on this step*/
public class Tile implements Serializable {

//    the map the tile is in
    private Map map;
//    The position on the map which the tile is situated
    private int xpos;
    private int ypos;
//    what character the tile will be represented as on the map
    private char currChar;
// true if character has seen the part of the map before
    private boolean isRevealed;
//    true if ava can be present on tile
    private boolean isWalkable = true;
//    some flavour text of what the senses pick up - usually default, can be
//    modified for providing clues
    private String description = "";
//    lists of currInteractables on tile
    private HashMap<String, Interactable> currInteractables;

/* constructor
 EFFECTS: this makes a default tile with nothing in it
    sets description to one of 3 random default descriptions of an empty tile
*/
    public Tile(Map map, int y, int x, char displayChar, List<Interactable> interactables) {
        this.map = map;
        ypos = y;
        xpos = x;
        this.currChar = displayChar;
        initInteractables(interactables);

        if (displayChar == Map.WALL) {
            isWalkable = false;
        }
    }

    private void initInteractables(List<Interactable> interactables) {
        HashMap<String, Interactable> hashMap = new HashMap<>();
        for (Interactable i : interactables) {
            hashMap.put(i.getName(), i);
        }
        currInteractables = hashMap;

    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
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

    public void setMap(Map map) {
        this.map = map;
    }

    public void setWalkable(boolean walkable) {
        isWalkable = walkable;
    }

    public HashMap<String, Interactable> getCurrInteractables() {
        return currInteractables;
    }

    public Map getMap() {
        return map;
    }
    
    //    effects: if tile is revealed return current char, else return fog char
    public char getDisplayChar() {
        if (isRevealed) {
            return currChar;
        }
        return Map.FOG;
    }

    /* sets actual display char of this tile
    * used when moving the avatar icon about the map*/
    public void setCurrChar(char currChar) {
        this.currChar = currChar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;
        Tile tile = (Tile) o;
        return xpos == tile.xpos &&
                ypos == tile.ypos &&
                currChar == tile.currChar &&
                isRevealed == tile.isRevealed &&
                isWalkable == tile.isWalkable &&
                Objects.equals(description, tile.description) &&
                Objects.equals(currInteractables, tile.currInteractables);
    }

    @Override
    public int hashCode() {

        return Objects.hash(xpos, ypos, currChar, isRevealed, isWalkable, description, currInteractables);
    }

    //    REQUIRES: ypos and xpos are valid indexes in map
    //    MODIFIES: this
    //    EFFECTS: replaces character at index ypos,xpos with AVATAR in mapDisplay
    public void revealTile() {
        isRevealed = true;
    }
}
