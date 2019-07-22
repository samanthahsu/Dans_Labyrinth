package model;

import model.items.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

interface DefaultMapInfo {

    int height = 11;
    int width = 21;
    int winY = 0;
    int winX = 9;
    int startY = 6;
    int startX = 3;

    String mapString = "@@@@@@@@@ @@@@@@@@@@@"
    + "@     @ @ @       @ @"
    + "@ @ @ @ @   @@@ @ @ @"
    + "@@@ @ @ @@@@@ @ @ @ @"
    + "@   @   @   @   @ @ @"
    + "@ @@@@@@@ @ @@@@@   @"
    + "@ @ @     @   @     @"
    + "@ @   @  @@ @ @ @@@@@"
    + "@ @@@@@@  @ @ @ @   @"
    + "@         @@@       @"
    + "@@@@@@@@@@@@@@@@@@@@@";

    ArrayList<Interactable> avaItems = new ArrayList <Interactable> (
            Arrays.asList(new Item("apple")));

    HashSet<Interactable> allInteractables = new HashSet<>();

}
