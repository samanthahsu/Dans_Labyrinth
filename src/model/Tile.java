package model;

import model.creatures.Creature;
import model.features.Feature;
import model.items.Item;

import java.io.Serializable;
import java.util.ArrayList;

/*Holds information things on this step*/
public class Tile implements Serializable {

//    The map this tile belongs to
    Map parentMap;

//    The position on the map which the tile is situated
    int xpos;
    int ypos;

//    what character the tile will be represented as on the map
    char displayChar;

// true if character has seen the part of the map before
    boolean isRevealed;
//    some flavour text of what the senses pick up - usually default, can be
//    modified for providing clues
    String description;

//    lists of interactables on tile
    ArrayList<Creature> creatures;
    ArrayList<Item> items;
    ArrayList<Feature> features;

// EFFECTS: this makes a default tile with nothing in it
//    sets description to one of 3 random default descriptions of an empty tile
    public Tile(Map map) {
        parentMap = map;
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

    public String getDescription() {
        return description;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public ArrayList<Feature> getFeatures() {
        return features;
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

//    todo
    private boolean interactableListEqualsF(ArrayList<Feature> arrayList, ArrayList<Feature> b) {
        return false;
    }

//todo
    private boolean interactableListEqualsI(ArrayList<Item> a, ArrayList<Item> b) {
        return false;
    }

// todo
    private boolean interactableListEqualsC(ArrayList<Creature> arrayList, ArrayList<Creature> b) {
        return false;
    }


}
