package model;

import model.Interactables.Interactable;
import model.Interactables.creatures.Ennui;
import model.Interactables.features.MossyGate;
import model.Interactables.items.Item;
import model.Interactables.items.PizzaBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*data used to load default map*/
interface DefaultMapData {

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

    List<Interactable> allInteractables = new ArrayList<>(
            Arrays.asList(new Ennui(6, 6), new MossyGate(5, 9, 4, 9))
    );

}
