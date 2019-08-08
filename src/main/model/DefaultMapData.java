package model;

import model.mapobjects.Examinable;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.features.*;
import model.mapobjects.items.Item;
import model.mapobjects.items.Note;
import model.mapobjects.items.PizzaBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*data used to load default map*/
interface DefaultMapData {

    int DEFAULT_HEIGHT = 11;
    int DEFAULT_WIDTH = 21;
    int DEFAULT_WIN_Y = 0;
    int DEFAULT_WIN_X = 9;
    int DEFAULT_START_Y = 6; //6
    int DEFAULT_START_X = 3; //3

    String DEFAULT_MAP_STRING = "@@@@@@@@@ @@@@@@@@@@@"
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

    List<Item> DEFAULT_AVA_ITEMS_LIST = new ArrayList<Item>(
            Arrays.<Item>asList(new PizzaBox()));

    List<Examinable> DEFAULT_INTERACTABLES_LIST = new ArrayList<Examinable>(
            Arrays.asList(new Note(7, 4),
                    new Ennui(6, 6),
                    new MossyGate(5, 9, 4, 9),
                    new BloodFish(2, 1),
                    new FourStoneGate(7, 15, 6, 15),
                    new Pan(1, 7),
                    new Brick(9, 9),
                    new LastGate(1, 9, 0, 9)
            )
    );
}
