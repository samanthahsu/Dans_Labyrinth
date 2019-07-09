package model;

/*this class stores display character, room description, action logs*/
public class Tile {
    private String description;
    private char display;

    public Tile(String description, char display) {
        this.description = description;
        this.display = display;
    }

//    EFFECT prints description
    public void printDescription(){}
}
