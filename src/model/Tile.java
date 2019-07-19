package model;

import java.util.List;

/*Holds information things on this step*/
public class Tile {

//    The map this tile belongs to
    Map parentMap;

//    The position on the map which the tile is situated
    int xpos;
    int ypos;

//    what character the tile will be represented as on the map
    char displayChar; // todo no longer need map and display map as this handles both those functions

// true if character has seen the part of the map before
    boolean isRevealed;
//    some flavour text of what the senses pick up - usually default, can be
//    modified for providing clues
    String description;

//    list of interactable objects currently on tile (creatures, items, features etc.)
    List<Interactable> interactables;

// EFFECTS: this makes a default tile with nothing in it
//    sets description to one of 3 random default descriptions of an empty tile
    public Tile() {
    }


}
