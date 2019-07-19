package model;

import java.util.Scanner;

/**Main hub which manages all game processes**/
public class GameRunner {

//    todo add exceptions

    private static final int CONTINUE_GAME = 0;
    private static final int QUIT_GAME = 1;
    private static final int FAIL_GAME = 2;
    private static final int WIN_GAME = 3;

    Integer gameState; // 0=continue, 1=quit, 2=death, 3=victory, 5=new game, 6=load game
    Map map = null;
    Scanner scnr;
    WriterReader writerReader;
    String ui;
//    todo make input string class wide

    public GameRunner() {
        gameState = CONTINUE_GAME;
        map = null;
        scnr = new Scanner(System.in);
        writerReader = new WriterReader();
        ui = "";
    }

    //    EFFECTS: prints welcome dialogue, if map is uninitialized,
    // handles homeScreen commands
    public boolean runHomeScreen() { // todo modify so that homescreen and game run in tandem in main
        System.out.println("BlackBox"
                + "==========");

        while (map == null && gameState != QUIT_GAME) {
            System.out.println("new : new game\n"
                    + "load : load a saved game\n"
                    + "quit : exit");
            ui = scnr.nextLine();
            homeExecuteUi();
        }
        if (gameState == QUIT_GAME) {
            return false;
        } else {
            map.printDisplayMap();
            runGame();
        }
        return true;
    }

//    MODIFIES: this
//    EFFECTS: handles available commands from the home screen, which are:
//      start new game: default map is loaded, and calls runGame
//      load saved game: selected mazeGame file is loaded, calls runGame
//      help: prints help dialogue
//    then sets gameState to appropriate value
    private void homeExecuteUi() {
        switch (ui) {
            case "new":
                System.out.println("Starting new game...");
                map = writerReader.readMap("default_map");
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

    //  EFFECTS: runs the main body of the game
    public void runGame() {
        String ui;

//        while loop for when the game is being played
        while (gameState == CONTINUE_GAME) {

            ui = scnr.nextLine();
            if (execute(ui)) { // each move is one tick
                map.nextState();
            }

            if (map.isWin()) {
                System.out.println("CONGRATS, YOU WON!");
                gameState = WIN_GAME;
            }
        }
        printEndText();
        map = null;
    }


    // EFFECTS: handles which Map functions to call. returns gameState.
    private boolean execute(String input) { // todo add use item
        switch (input) {
            case "n":
            case "s":
            case "e":
            case "w":
                map.getAva().moveAva(input, map);
                break;
            case "look":
                System.out.println("hello darkness.");
                break;
            case "map":
                map.printDisplayMap();
                break;
            case "me":
                System.out.println("Health: " + map.getAva().getStatus()
                + "/3");
                map.getAva().printItems();
                break;
            case "h":
                printHelp();
                break;
            case "q":
                gameState = handleQuit();
                break;
            case "pick up":
                map.getAva().pickUpItem(map);
                break;
            case "use":
                System.out.println("Choose an item:");
                input = scnr.nextLine();
                map.getAva().useItem(input, map);
            default:
                System.out.println("...");
                return false;
        }
        return true;
    }

    // EFFECT: print user controls and other info
    private void printHelp() {
        System.out.println("Enter n, s, e, or w to move North, South, East, or "
                + "West respectively." + '\n'
                + "map: view the map" + '\n'
                + "pick up: pick up item" + '\n'
                + "me: view items and status" + '\n'
                + "use: use and item" + '\n'
                + "h: to get help dialogue" + '\n'
                + "q: to quit");
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
        System.out.println("Enter 's' to save, 'c' to cancel or 'q' again to "
                + "quit without saving.");
        switch (scnr.nextLine()) {
            case "s":
                System.out.println("What would you like to name your file?");
                writerReader.writeMap(map, scnr.nextLine());
//                svl.saveGame(scnr.nextLine(), map, map.getAva());
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

//    EFFECTS: prints celebratory graphic
    private void printWinGraphic() {
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
