package model;

import java.util.Random;
import java.util.Scanner;

// Main hub, manages all game processes
public class MazeGame {


    private static final int CONTINUE_GAME = 0;
    private static final int QUIT_GAME = 1;
    private static final int FAIL_GAME = 2;
    private static final int WIN_GAME = 3;

    Integer gameState = CONTINUE_GAME; // 0=continue, 1=quit, 2=death, 3=victory, 5=new game, 6=load game
    Map map = null;
    Scanner scnr = new Scanner(System.in);
    Random ran = new Random();
    SaveAndLoad svl = new SaveAndLoad();

    //    EFFECTS: prints welcome dialogue, if map is uninitialized,
    // handles homeScreen commands
    public void runHomeScreen() {

        while (map == null && gameState!=QUIT_GAME) {
            System.out.println( "Enter 'n' for new game, 'l' to load a saved " +
                    "game, 'h' for more controls, 'q' to end program");
            homeExecute(scnr.nextLine());
        }

        if(gameState==QUIT_GAME){return;}

        map.printDisplayMap();
        runGame();
    }

//    MODIFIES: this
//    EFFECTS: handles available commands from the home screen, which are:
//      start new game: default map is loaded, and calls runGame
//      load saved game: selected mazeGame file is loaded, calls runGame
//      help: prints help dialogue
//    then sets gameState to appropriate value
    private void homeExecute(String input) {
        switch (input) {
            case "n":
                System.out.println("Starting new game...");
                map = svl.loadFile("default_map");
                break;
            case "l":
                System.out.println("Enter name of saved file: ");
                input = scnr.nextLine();
                map = svl.loadFile(input);
                break;
            case "h":
                printHelp();
                break;
            case "q":
                gameState = QUIT_GAME;
                System.out.println("Thanks for playing!");
                break;
            default:
                System.out.println("Command not available on home screen.");
                break;
        }
    }

    //  EFFECTS: runs the main body of the game
    private void runGame(){
        String ui;

//        while loop for when the game is being played
        while (gameState == CONTINUE_GAME) {

            ui = scnr.nextLine();
            gameState = execute(ui); // each move is one tick

            if(map.isWin()){
                System.out.println("CONGRATS, YOU WON!");
                gameState = WIN_GAME;
            }
        }
        printEndText();
        map = null;
        runHomeScreen();
    }

    // EFFECTS: handles which Map functions to call. returns gameState.
    private int execute(String input) {
        int gameState = 0;
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
            case "m":
                map.printDisplayMap();
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
            case "items":
                map.getAva().printItems();
                break;
            default:
                System.out.println("Confusion ensues.");
        }
        return gameState;
    }

    // EFFECT: print user controls and other info
    private void printHelp(){
//        todo: not final text
        System.out.println("Enter n, s, e, or w to move North, South, East, or West respectively."+'\n'+
                "'m' to view the map"+'\n'+
                "'pick up': pick up item"+'\n'+
                "'items': view items"+'\n'+
                "'h' to get help dialogue"+'\n'+
                "'q' to quit");
    }

    // REQUIRES: gameOver is in the interval [1, 3]
    // EFFECT: prints end text based on int gameOver
    private void printEndText() {
//        todo: not final text
        switch (gameState) {
            case QUIT_GAME:
                System.out.println("You quit.");
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
        System.out.println
                ("Enter 's' to save, 'c' to cancel or 'q' again to quit without saving.");
        switch (scnr.nextLine()) {
            case "s":
                System.out.println("What would you like to name your file?");
                svl.saveGame(scnr.nextLine(), map, map.getAva());
                break;
            case "c":
                System.out.println("You continue...");
                break;
            case "q":
                System.out.println("Quitting...");
                return QUIT_GAME;
            }
        return CONTINUE_GAME;
    }

    private void printWinGraphic() {
        System.out.println("                                  .''.\n" +
                "        .''.             *''*    :_\\/_:     .\n" +
                "       :_\\/_:   .    .:.*_\\/_*   : /\\ :  .'.:.'.\n" +
                "   .''.: /\\ : _\\(/_  ':'* /\\ *  : '..'.  -=:o:=-\n" +
                "  :_\\/_:'.:::. /)\\*''*  .|.* '.\\'/.'_\\(/_'.':'.'\n" +
                "  : /\\ : :::::  '*_\\/_* | |  -= o =- /)\\    '  *\n" +
                "   '..'  ':::'   * /\\ * |'|  .'/.\\'.  '._____\n" +
                "       *        __*..* |  |     :      |.   |' .---\"|\n" +
                "        _*   .-'   '-. |  |     .--'|  ||   | _|    |\n" +
                "     .-'|  _.|  |    ||   '-__  |   |  |    ||      |\n" +
                "     |' | |.    |    ||       | |   |  |    ||      |\n" +
                "  ___|  '-'     '    \"\"       '-'   '-.'    '`      |____\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                "                       ~-~-~-~-~-~-~-~-~-~   /|\n" +
                "          )      ~-~-~-~-~-~-~-~  /|~       /_|\\\n" +
                "        _-H-__  -~-~-~-~-~-~     /_|\\    -~======-~\n" +
                "~-\\XXXXXXXXXX/~     ~-~-~-~     /__|_\\ ~-~-~-~\n" +
                "~-~-~-~-~-~    ~-~~-~-~-~-~    ========  ~-~-~-~");
    }

}
