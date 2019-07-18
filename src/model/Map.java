package model;

import model.features.AbsolutelyNothing;

import java.util.ArrayList;
import java.util.Random;

/*manages the map portion of this adventure*/
public class Map {
//todo override equals() for this one*******
    private static final char wall = '@';
    private static final char floor = ' ';
    private static final char c = '*';
    private static final char fog = '#';

    private char[][] map; //    stores the 2D matrix of the full map (not modified after)
    private char[][] mapDisplay; // stores what the user can see of the map (character, fog, walls)
    private int height;
    private int width;
    private int winY;
    private int winX;
    private Avatar ava;
    private ArrayList<ArrayList<Interactable>> interactables = new ArrayList<>();


//    EFFECTS: sets height, width, winY, and winX
//          fills map with mapString
//          fills mapDisplay with savedMap
//          places ava at ypos xpos
    public Map(int h, int w, String cleanMap, int startY, int startX, int winY,
               int winX, ArrayList<Interactable> inter, ArrayList<Interactable> items) {
        height = h;
        width = w;
        this.winY = winY;
        this.winX = winX;
        initMap(cleanMap);
        initMapDisplay();
        initAvatar(startY, startX, items);
        initInteractables(inter);
    }


//    EFFECTS: sets height, width, winY, and winX
//          fills map with mapString
//          fills mapDisplay with savedMap
//          places ava at ypos xpos
    public Map(int h, int w, String cleanMap, String savedMap, int startY, int startX, int winY,
               int winX, ArrayList<Interactable> inter, ArrayList<Interactable> items) {
        height = h;
        width = w;
        this.winY = winY;
        this.winX = winX;
        initMap(cleanMap);
        initMapDisplay(savedMap);
        initAvatar(startY, startX, items);
        initInteractables(inter);
    }

// EFFECTS: fills Array list full of null to correct size
// if inter is not empty, places interactables in interactables list matrix at specified indexes.
    private void initInteractables(ArrayList<Interactable> inter) {
        Interactable nothing = new AbsolutelyNothing();
        ArrayList<Interactable> widthList = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            widthList.add(nothing);
        }
        for (int i = 0; i < height; i++) {
            interactables.add(widthList);
        }
        if (inter != null) {
            for (Interactable c : inter) {
                if (c != null) {
                    interactables.get(c.getYpos()).set(c.getXpos(), c);
                }
            }
        }
    }

    //    MODIFIES: this
    //    EFFECTS: store mapString into map
    private void initMap(String mapString) {
        map = new char[height][width];
        int strIndex = 0;
        for (int i = 0; i < height; i++)  {
            for (int j = 0; j < width; j++) {
                map[i][j] = mapString.charAt(strIndex);
                strIndex++;
            }
        }
    }

    //    MODIFIES: this todo combine this and initMap for efficiency (eventually)
    //    EFFECTS: fills mapDisplay with fog tiles
    private void initMapDisplay() {
        mapDisplay = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mapDisplay[i][j] = fog;
            }
        }
    }

//    MODIFIES: this
//    REQUIRES: given string is fitting of given height and width
//    EFFECTS: fills mapDisplay according to state
    private void initMapDisplay(String state) {
        mapDisplay = new char[height][width];
        int strIndex = 0;
        for (int i = 0; i < height; i++)  {
            for (int j = 0; j < width; j++) {
                mapDisplay[i][j] = state.charAt(strIndex);
                strIndex++;
            }
        }
    }

    //    MODIFIES: mapDisplay
    //    EFFECTS: places avatar at ypos, xpos revealing adjacent tiles
    private void initAvatar(int startY, int startX, ArrayList<Interactable> items) {
        ava = new Avatar(startY, startX, items);
        updateTileDisp(startY, startX, c);
        revealSurroundings(startY, startX);
    }

    //    **GETTERS**
    public char[][] getMap() {
        return map;
    }

    public char[][] getMapDisplay() {
        return mapDisplay;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getWinY() {
        return winY;
    }

    public int getWinX() {
        return winX;
    }

    public Avatar getAva() {
        return ava;
    }

    //    REQUIRES: interactables is not null
//    EFFECTS: if creature exists at given index, returns Interactable
    public Interactable getInteractable(int y, int x) {
        return interactables.get(y).get(x);
    }

    public void removeInteractable(int y, int x) {
        interactables.get(y).set(x, new AbsolutelyNothing());
    }

    public ArrayList<ArrayList<Interactable>> getInteractables() {
        return interactables;
    }

    //    EFFECTS: return true if index within bounds of map
    public boolean isIndexValid(int y, int x) {
        return y >= 0 && x >= 0 && y < height && x < width;
    }

    //    REQUIRES: ypos and xpos are valid indexes in map
    //    EFFECTS: returns true if tile of requested index is floor in map, else false
    public boolean isTileFloor(int y, int x) {
        return map[y][x] == ' ';
    }

    //    REQUIRES: ypos and xpos are valid indexes in map
    //    MODIFIES: this
    //    EFFECTS: replaces character at index ypos,xpos with c in mapDisplay
    public void updateTileDisp(int y, int x, char c) {
        mapDisplay[y][x] = c;
    }

    //    MODIFIES: this
    //    EFFECTS: if given index is valid, replaces character at index ypos,xpos with c in mapDisplay
    //        otherwise does nothing
    private void checkAndUpdateTileDisp(int y, int x) {
        if (isIndexValid(y, x)) {
            updateTileDisp(y, x, map[y][x]);
        }
    }

    //    MODIFIES: this
    //    EFFECTS: replace any fog tiles with corresponding tiles on map orthogonally about index ypos,xpos
    //      on mapDisplay (no diagonals), if tile index not valid, do nothing
    public void revealSurroundings(int y, int x) {
        checkAndUpdateTileDisp(y, x - 1);
        checkAndUpdateTileDisp(y, x + 1);
        checkAndUpdateTileDisp(y - 1, x);
        checkAndUpdateTileDisp(y + 1, x);
    }

    //    EFFECTS: returns true if ava is on the winning tile
    public boolean isWin() {
        return ava.getYpos() == winY && ava.getXpos() == winX;
    }

    //    *************PRINTING*************

    //    REQUIRES: mapDisplay is not null
    //    EFFECTS: prints mapDisplay to screen
    public void printDisplayMap() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(mapDisplay[i][j]);
            }
            System.out.println();
        }
    }

    //    EFFECTS: prints silly statements when you try to move to a wall tile
    public void printMovePlaceholder(String dir) {
        Random ran = new Random();
        switch (ran.nextInt(5)) {
            case 0:
                System.out.println("You smack hilariously against the " + dir + " wall.");
                break;
            case 1:
                System.out.println("Your toe is painfully stubbed on the " + dir + " wall.");
                break;
            case 2:
                System.out.println("You flop desperately against the " + dir + " wall.");
                break;
            case 3:
                System.out.println("You sit and ponder how your life has culminated in this moment.");
                break;
            case 4:
                System.out.println("You flop against the " + dir + " wall for fun.");
                break;
            default:
        }
    }


}
