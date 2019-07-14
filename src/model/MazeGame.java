package model;

import java.util.Random;
import java.util.Scanner;

// Main hub, manages all game processes
public class MazeGame {

    private static final int continueGame = 0;
    private static final int quitGame = 1;
    private static final int failGame = 2;
    private static final int winGame = 3;
    private static final int loopHome = 4;

    int gameState = 0; // 0=continue, 1=quit, 2=death, 3=victory, 5=new game, 6=load game
    Avatar ava;
    Map map;
    Scanner scnr = new Scanner(System.in);
    Random ran = new Random();
    SaveAndLoad svl = new SaveAndLoad();


    //  EFFECTS: runs the main body of the game
    public void runGame(){
        ava = new Avatar(default_startY, default_startX);
        map = new Map(default_height, default_width, default_map, default_startY, default_startX, winY,winX);

        String input;

//        while loop for when the game is being played
        while (gameState==continueGame) {
//            map.printTileDescription(); // each tile is a room???? doesn't make too much sense
            input = scanner.nextLine();
            gameState = game.execute(input, map, ran, ava, scanner, svl); // each move is one tick

            if(map.isWin(ava)){
                System.out.println("CONGRATS, YOU WON!");
            }
//            victory = check if victory;
        }

    }

    // EFFECTS: chooses which Map functions to call. returns gameState.
    public int execute(String input) {
        int gameState = 0;
        switch (input) {
            case "n":
            case "s":
            case "e":
            case "w":
                avatar.moveAva(input, map);
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
                gameState = handleQuit(scnr, map, avatar, svl);
                break;
            default:
                System.out.println("Confusion ensues.");
        }
//        if(map.isWin){
        return gameState;
    }

    //    EFFECTS: prints welcome dialogue, and handles homeScreen commands
    public int runHomeScreen() {
        int gameState = loopHome;
        System.out.println("**WELCOME TO THE NEXT INSTALLMENT OF...**"+'\n'+
                "Some kind of game maybe?"+"\n"+
                "====================================");

        while (gameState == loopHome) {
            System.out.println( "Enter 'n' for new game, 'l' to load a saved " +
                    "game, 'h' for more controls, 'q' to end program");
            gameState = homeExecute(scnr.nextLine(), scnr, svl);
        }

        return gameState;
    }

//    MODIFIES: this
//    EFFECTS: handles available commands from the home screen, which are:
//      start new game: default map is loaded, and calls runGame
//      load saved game: selected mazeGame file is loaded, calls runGame
//      help: prints help dialogue
    private int homeExecute(String input) {
        switch (input) {
            case "n":
                System.out.println("Starting new game...");
                break;
            case "l":
                System.out.println("Enter name of saved file: ");
                svl.loadFile(scnr);
                break;
            case "h":
                printHelp();
                return loopHome;
            case "q":
                return quitGame;
                default:
                    System.out.println("Command not available on home screen.");
                    return loopHome;
        }
        return continueGame;
    }

    // EFFECT: print user controls and other info
    private void printHelp(){
//        todo: not final text
        System.out.println("Enter n, s, e, or w to move North, South, East, or West respectively."+'\n'+
                "'m' to view the map"+'\n'+
                "'h' to get help dialogue"+'\n'+
                "'q' to quit");
    }

    // REQUIRES: gameOver is in the interval [1, 3]
    // EFFECT: prints end text based on int gameOver
    public void printEndText(int gameState) {
//        todo: not final text
        switch (gameOver) {
            case quitGame:
                System.out.println("You quit.");
                break;
            case failGame:
                System.out.println("Death: Game Over");
                break;
            case winGame:
                System.out.println("You escaped successfully");
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
                svl.saveGame(scnr.nextLine(), map, ava);
                break;
            case "c":
                System.out.println("You continue...");
                break;
            case "q":
                System.out.println("Quitting...");
                return quitGame;
            }
        return continueGame;
    }

}
