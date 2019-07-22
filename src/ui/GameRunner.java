package ui;

import model.Map;
import model.Tile;
import model.WriterReader;
import model.items.Item;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*Main hub which manages all game processes*/
public class GameRunner {

//    todo add exceptions

//    game states
    private static final int CONTINUE_GAME = 0;
    private static final int QUIT_GAME = 1;
    private static final int FAIL_GAME = 2; // todo make able to die
    private static final int WIN_GAME = 3;

    private Integer gameState;
    private Map map;
    private Scanner scnr;
    private WriterReader writerReader;
    private String ui;
//    todo make input string class wide

/*
    starts real part of game
    effects: initializes gameState, map, scnr, writerREader, and ui
*/
    GameRunner() {
        gameState = CONTINUE_GAME;
        map = null;
        scnr = new Scanner(System.in);
        writerReader = new WriterReader();
        ui = "";
    }

/*
        EFFECTS: while map is uninitialized, prints welcome text, and handles homeScreen commands
        returns true if gameState is QUIT_GAME, else runs the game and returns false
*/
    public boolean runHomeScreen() {
        System.out.println("=============DAN'S LABYRINTH=============");
        while (map == null && gameState != QUIT_GAME) {
            System.out.println("new: new game\n"
                    + "load: load a saved game\n"
                    + "quit: exit");
            ui = scnr.nextLine();
            homeExecuteUi();
        }
        if (gameState == QUIT_GAME) {
            return true;
        }
        runGame();
        return false;
    }

/*
    MODIFIES: this
    EFFECTS: handles available commands from the home screen, which are:
      start new game: default map is loaded, and calls runGame
      load saved game: selected mazeGame file is loaded, calls runGame
    then sets gameState to appropriate value
*/
    private void homeExecuteUi() {
        switch (ui) {
            case "new":
                System.out.println("Starting new game...");
                map = writerReader.buildDefaultMap();
                break;
            case "load":
                System.out.println("Enter name of saved file: ");
                ui = scnr.nextLine();
                map = writerReader.readMap(ui);
                break;
            case "quit":
                gameState = QUIT_GAME;
                System.out.println("Thanks for playing!");
                break;
            default:
                System.out.println("Command not available on home screen.");
                break;
        }
    }

/*
modifies: this, map
      EFFECTS: runs the main body of the game
*/
    public void runGame() {
        boolean isValidMove;

        while (gameState == CONTINUE_GAME) { //todo print out description of current tile

            ui = scnr.next();
            isValidMove = execute(ui);
            scnr.skip(".*");

            if (isValidMove) { // each move is one tick of game clock
                map.nextState();
//                todo make it so you can't save the game after you step on the winning tile
                if (map.isWin()) {
                    System.out.println("CONGRATS, YOU WON!");
                    gameState = WIN_GAME;
                }
            }
        }
        printEndText();
        map = null;
    }

    // EFFECTS: handles which Map functions to call. returns gameState.
    private boolean execute(String input) { // todo make scanner take in one word at a time
        String item;
        switch (input) {
            case "n":
            case "s":
            case "e":
            case "w":
                map.getAva().moveAva(input);
                break;
            case "look":
                System.out.println("hello darkness.");
                break;
            case "map":
                printDisplayMap();
                break;
            case "me":
                System.out.println("Health: " + map.getAva().getStatus()
                + "/3");
                printItems();
                break;
            case "help":
                printHelp();
                break;
            case "quit":
                gameState = handleQuit();
                break;
            case "pickup":
                item = scnr.next();
                map.getAva().pickUpItem(item);
                break;
            case "use":
                item = scnr.next();
                map.getAva().useItem(item);
            default:
                System.out.println("...");
                return false;
        }
        return true;
    }

    // EFFECT: print user controls and other info *todo make dynamic for each tile
    private void printHelp() {
        System.out.println("Enter n, s, e, or w to move North, South, East, or "
                + "West respectively." + '\n'
                + "map: view the map" + '\n'
                + "pickup ____: pick up chosen item" + '\n'
                + "me: view items and status" + '\n'
                + "use ___: use chosen item" + '\n'
                + "help: to get help dialogue" + '\n'
                + "quit: to quit");
    }

    // EFFECTS: prints out current itemList
    public void printItems() {
        ArrayList<Item> itemList = map.getAva().getItemList();
        System.out.println("You are carrying:");
        for (Item i: itemList) {
            System.out.println(i.getName());
        }
    }

    // REQUIRES: gameOver is in the interval [1, 3]
    // EFFECT: prints end text based on int gameOver
    private void printEndText() {
        switch (gameState) {
            case QUIT_GAME:
                break;
            case FAIL_GAME:
                System.out.println("Death: Game Over");
                break;
            case WIN_GAME:
                System.out.println("You escaped successfully...");
                printWinGraphic();
                break;
        }
    }

//    EFFECTS: gives user option to
//      save: saves map and status as single file, named by user
//      cancel: continues the game
//      quit: quit the game without saving
    private int handleQuit() {
        System.out.println("s: save game\n"
                + "c: cancel and continue\n"
                +"q: quit without saving.");
        switch (scnr.nextLine()) {
            case "s":
                System.out.println("What would you like to name your file?");
                writerReader.writeMap(map, scnr.nextLine());
                break;
            case "c":
                System.out.println("You continue...");
                break;
            case "q":
                System.out.println("Quitting...");
                return QUIT_GAME;
            default:
        }
        return CONTINUE_GAME;
    }

/*
        REQUIRES: map has been initialized properly
        EFFECTS: prints current map to screen
*/
private void printDisplayMap() {
        int height = map.getHeight();
        int width = map.getWidth();
        ArrayList<ArrayList<Tile>> tileMatrix = map.getTileMatrix();
        char displayTile;
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
                displayTile = tileMatrix.get(m).get(n).getDisplayChar();
                System.out.print(displayTile);
            }
            System.out.println();
        }
    }

/*
        EFFECTS: prints feedback when ava tries to move into a wall tile
*/
    public static void printMovePlaceholder(String dir) {
        Random ran = new Random();
        switch (ran.nextInt(4)) {
            case 0:
                System.out.println("Dan smacks hilariously against the " + dir + " wall.");
                break;
            case 1:
                System.out.println("Dan stubs his toe painfully on the " + dir + " wall.");
                break;
            case 2:
                System.out.println("Dan flops desperately against the " + dir + " wall.");
                break;
            case 3:
                System.out.println("Dan sits and ponders how his life has culminated in this moment.");
                break;
            default:
        }
    }

    //    EFFECTS: prints celebratory graphic
    private void printWinGraphic() {
        System.out.println("As far as Dan is concerned, pizza had been delivered and eaten, another successful day.");
        System.out.println("                                  .''.\n"
                + "        .''.             *''*    :_\\/_:     .\n"
                + "       :_\\/_:   .    .:.*_\\/_*   : /\\ :  .'.:.'.\n"
                + "   .''.: /\\ : _\\(/_  ':'* /\\ *  : '..'.  -=:o:=-\n"
                + "  :_\\/_:'.:::. /)\\*''*  .|.* '.\\'/.'_\\(/_'.':'.'\n"
                + "  : /\\ : :::::  '*_\\/_* | |  -= o =- /)\\    '  *\n"
                + "   '..'  ':::'   * /\\ * |'|  .'/.\\'.  '._____\n"
                + "       *        __*..* |  |     :      |.   |' .---\"|\n"
                + "        _*   .-'   '-. |  |     .--'|  ||   | _|    |\n"
                + "     .-'|  _.|  |    ||   '-__  |   |  |    ||      |\n"
                + "     |' | |.    |    ||       | |   |  |    ||      |\n"
                + "  ___|  '-'     '    \"\"       '-'   '-.'    '`      |____\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "                       ~-~-~-~-~-~-~-~-~-~   /|\n"
                + "          )      ~-~-~-~-~-~-~-~  /|~       /_|\\\n"
                + "        _-H-__  -~-~-~-~-~-~     /_|\\    -~======-~\n"
                + "~-\\XXXXXXXXXX/~     ~-~-~-~     /__|_\\ ~-~-~-~\n"
                + "~-~-~-~-~-~    ~-~~-~-~-~-~    ========  ~-~-~-~");
    }
}
