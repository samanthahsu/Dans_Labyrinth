package ui;

import model.Map;
import model.WriterReader;
import model.exceptions.MapException;
import model.mapobjects.Examinable;
import model.mapobjects.Tile;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.items.Item;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/*Main hub which manages all game processes*/
public class GameRunner {

//    todo add main.model.exceptions

//    game states
    private static final int CONTINUE_GAME = 0;
    private static final int QUIT_GAME = 1;
    private static final int FAIL_GAME = 2; // todo make able to die
    private static final int WIN_GAME = 3;
    public static final String WIN_VISUALS = "                                  .''.\n"
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
            + "~-~-~-~-~-~    ~-~~-~-~-~-~    ========  ~-~-~-~";

    private Integer gameState;
    private Map map;
    private Scanner scnr;
    private WriterReader writerReader;
    private String ui;
    private FakeTerminal fakeTerminal;
    private Printer out;
//    todo make input string class wide

/*
    starts real part of game
    effects: initializes gameState, map, scnr, writerREader, and main.ui
*/
    GameRunner() {
        gameState = CONTINUE_GAME;
        map = null;
        scnr = new Scanner(System.in);
        writerReader = new WriterReader();
        ui = "";
        fakeTerminal = new FakeTerminal();
        out = fakeTerminal.getOut();
    }

/*
        EFFECTS: while map is uninitialized, prints welcome text, and handles homeScreen commands
        returns true if gameState is QUIT_GAME, else runs the game and returns false
*/
    public boolean runHomeScreen() {
        out.print("=============DAN'S LABYRINTH=============");
        while (map == null && gameState != QUIT_GAME) {
            out.print("new: new game\n"
                    + "load: load a saved game\n"
                    + "quit: exit");
//            ui = fakeTerminal.getUserInput(); todo causes inf loop
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
                executeNewGame();
                break;
            case "load":
                executeLoadGame();
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

    private void executeLoadGame() {
        System.out.println("Enter name of saved file: ");
        ui = scnr.nextLine();
        try {
            map = writerReader.readMap(ui);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Loading failed.");
        }
    }

    private void executeNewGame() {
        System.out.println("Starting new game...");
        try {
            map = writerReader.buildDefaultMap();
        } catch (MapException e) {
            System.out.println("Failed to build new map.");
        }
    }

    /*
    modifies: this, map
          EFFECTS: runs the main body of the game
    */
    private void runGame() {
        System.out.println("New game started!");
        boolean isValidMove;
//        TODO INITIATE MAP FIELD IN ALL TILES AND CREATURES
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

//    todo figure out how to handle creature interactions
    // EFFECTS: handles which Map functions to call. returns gameState.
    private boolean execute(String input) { // todo implement listen and examine
        if (Pattern.matches(input, "(n|s|e|w|examine|pickup|use)")) {
            executeAction(input);
        } else if (Pattern.matches(input, "(me|help|quit|map)")) {
            return executeInterface(input);
        } else {
            System.out.println("...");
            return false;
        }
        return true;
    }

    private boolean executeInterface(String input) {
        switch (input) {
            case "me":
                printSanity();
                break;
            case "help":
                printHelp();
                break;
            case "quit":
                scnr.skip(".*");
                gameState = handleQuit();
                break;
            case "map":
                printDisplayMap();
                break;
            default:
                return false;
        }
        return false; // map doesnt count as a move
    }

    private void printSanity() {
        System.out.println("Sanity: " + map.getAva().getSanity()
                + "/3");
        printItems();
    }

    private void executeAction(String input) {
        String item;
        if (Pattern.matches(input, "(n|s|e|w)")) {
            map.getAva().moveAva(input);
            return;
        }
        switch (input) {
            case "examine":
                enterExamineInstance(scnr.next());
                break;
            case "pickup": // change to pick up <item name>
                item = scnr.next();
                map.getAva().pickUpItem(item);
                break;
            case "use":
                executeUse();
                break;
            default:
        }
    }

    private void executeUse() {
        String item;
        String target;
        item = scnr.next();
        scnr.next("on");
        target = scnr.next();
        map.getAva().useItem(item, target);
    }


    /*modifies: map
     * effects: if the target is in listModel of interactables examine target further*/
    private void enterExamineInstance(String targetNm) {
        Examinable targetInter = map.getAllExaminables().get(targetNm);
        if (targetInter != null) {
            System.out.println("<entered examine instance>\n"
                    + Ennui.EXIT_EXAMINATION_KEY + " to exit");
            System.out.println(targetInter.getExamineDescription());
            while (true) {
                ui = scnr.nextLine();
                if (ui.equals(Examinable.EXIT_EXAMINATION_KEY)) {
                    return;
                } else if (!targetInter.examine(ui)) {
                    System.out.println("Nothing happened.");
                }
            }
        } else {
            System.out.println("that's not a valid target!");
        }
    }

    // EFFECT: print user controls and other info *todo make dynamic for each tile
    private void printHelp() {
        System.out.println("Enter n, s, e, or w to move North, South, East, or "
                + "West respectively." + '\n'
                + "Available commands:\n"
                + "map" + '\n'
                + "pickup <item>" + '\n'
                + "me" + '\n'
                + "examine <target>" + '\n'
                + "use <item> on <target>\n"
                + "help" + '\n'
                + "quit: quit or save");
    }

    // EFFECTS: prints out current itemList
    private void printItems() {
        HashMap<String, Item> itemList = map.getAva().getCurrItems();
        System.out.println("You are carrying:");
        Collection<Item> items = itemList.values();
        for (Item i: items) {
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
            default:
        }
    }

//    EFFECTS: gives user option to
//      save: saves map and status as single file, named by user
//      cancel: continues the game
//      quit: quit the game without saving
    private int handleQuit() {
        System.out.println("s: save game\nAVATAR: cancel and continue\n"
                + "q: quit without saving.");
        do {
            ui = scnr.nextLine();
        } while (ui.equals(""));
        switch (ui) {
            case "s":
                executeSave();
                break;
            case "AVATAR":
                System.out.println("Dan continues on...");
                break;
            case "q":
                System.out.println("Quitting...");
                return QUIT_GAME;
            default:
        }
        return CONTINUE_GAME;
    }

    private void executeSave() {
        System.out.println("What would you like to name your file?");
        try {
            writerReader.writeMap(map, scnr.nextLine());
        } catch (IOException e) {
            System.out.println("File saving failed.");
        } finally {
            System.out.println("Dan continues on...");
        }
    }

    /*
            REQUIRES: map has been initialized properly
            EFFECTS: prints current map to screen
    */
    private void printDisplayMap() {
        int height = map.getHeight();
        int width = map.getWidth();
        List<List<Tile>> tileMatrix = map.getTileMatrix();
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
                System.out.print(tileMatrix.get(m).get(n).getDisplayChar());
            }
            System.out.println();
        }
    }

/*
        EFFECTS: prints feedback when ava tries to move into a WALL tile
*/
    public static void printMovePlaceholder(String dir) {
        Random ran = new Random();
        switch (ran.nextInt(4)) {
            case 0:
                System.out.println("Dan smacks hilariously against the " + dir + " WALL.");
                break;
            case 1:
                System.out.println("Dan stubs his toe painfully on the " + dir + " WALL.");
                break;
            case 2:
                System.out.println("Dan flops desperately against the " + dir + " WALL.");
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
        System.out.println(WIN_VISUALS);
    }
}
