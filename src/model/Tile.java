package model;

import model.creatures.Creature;
import model.features.Feature;
import model.items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/*Holds information things on this step*/
public class Tile implements Serializable {

//    The position on the map which the tile is situated
    private int xpos;
    private int ypos;

//    what character the tile will be represented as on the map
    char displayChar;

// true if character has seen the part of the map before
    private boolean isRevealed;
//    true if ava can be present on tile
    private boolean isWalkable;
//    some flavour text of what the senses pick up - usually default, can be
//    modified for providing clues
    private String description;

//    lists of interactables on tile
    private HashSet<Interactable> interactables;
//    todo change all these lists into hash sets

/* constructor
 EFFECTS: this makes a default tile with nothing in it
    sets description to one of 3 random default descriptions of an empty tile
*/
    public Tile(int y, int x, char displayChar, HashSet<Interactable> interactables) {
        ypos = y;
        xpos = x;
        this.displayChar = displayChar;
        this.interactables = interactables;
    }

    public int getXpos() {
        return xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public char getDisplayChar() {
        return displayChar;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public boolean isWalkable() {
        return isWalkable;
    }

    public String getDescription() {
        return description;
    }

    /*
    * sets actual display char of this tile
    * used when moving the avatar icon about the map*/
    public void setDisplayChar(char displayChar) {
        this.displayChar = displayChar;
    }

    //    EFFECTS: returns true if tiles can be considered equal
    public boolean equals(Tile tile) {
        boolean xposEq = xpos == tile.getXpos();
        boolean yposEq = ypos == tile.getYpos();
        boolean displayCharEq = displayChar == tile.getDisplayChar();
        boolean isRevealedEq = isRevealed == tile.isRevealed();
        boolean descriptionEq = description.equals(tile.getDescription());
//        usually, checking x and y are enough to ensure they are the same
//        boolean creaturesEq = interactableListEqualsC(creatures, tile.getCreatures());
//        boolean itemsEq = interactableListEqualsI(items, tile.getItems());
//        boolean featuresEq = interactableListEqualsF(features, tile.getFeatures());

        return xposEq && yposEq && displayCharEq && isRevealedEq && descriptionEq /*&& creaturesEq && itemsEq && featuresEq*/;
    }

    //    REQUIRES: ypos and xpos are valid indexes in map
    //    MODIFIES: this
    //    EFFECTS: replaces character at index ypos,xpos with c in mapDisplay
    public void revealTile() {
        isRevealed = true;
    }

    //    REQUIRES: ypos and xpos are valid indexes in map
    //    MODIFIES: this
    //    EFFECTS: replaces character at index ypos,xpos with c in mapDisplay
    public void updateTileDisp() {
        isRevealed = true;
    }


    //    todo
    /*returns true if a and b are of the same size, and have the same elements in the same order*/
    private boolean interactableListEqualsF(ArrayList<Feature> a, ArrayList<Feature> b) {
        boolean sizeEq = a.size() == b.size();
        for (Feature f : a) {
            if (!a.equals(b));
        }
        return false;
    }

//todo
    /*returns true if a and b are of the same size, and have the same elements in the same order*/
    private boolean interactableListEqualsI(ArrayList<Item> a, ArrayList<Item> b) {
        return false;
    }

// todo
    /*returns true if a and b are of the same size, and have the same elements in the same order*/
    private boolean interactableListEqualsC(ArrayList<Creature> a, ArrayList<Creature> b) {
        return false;
    }


}
