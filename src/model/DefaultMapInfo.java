package model;

import model.Interactables.Interactable;
import model.Interactables.items.Item;
import model.Interactables.items.PizzaBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*data used to load default map*/
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

    ArrayList<Item> avaItems = new ArrayList <Item> (
            Arrays.asList(new PizzaBox()));

    HashSet<Interactable> allInteractables = new HashSet<Interactable>();

}
