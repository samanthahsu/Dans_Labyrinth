package model;

import java.util.Random;

public class MazeGame {
    private static final int quitGame = 1;
    private static final int failGame = 2;
    private static final int winGame = 3;

    //EFFECT: chooses which map function to call. returns gamestate
    public int execute(String input, Map map, Random ran, Avatar avatar) {
        int gameState = 0;
        switch (input) {
            case "n":
                map.printMovePlaceholder("northern", ran);
//                avatar.moveCharN(map);
                break;
            case "s":
                map.printMovePlaceholder("southern", ran);
                //                avatar.moveCharS(map);
                break;
            case "e":
                map.printMovePlaceholder("eastern", ran);
                //                avatar.moveCharE(map);
                break;
            case "w":
                map.printMovePlaceholder("western", ran);
                //                avatar.moveCharW(map);
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
                gameState = 1;
                break;
            default:
                System.out.println("Confusion ensues.");
        }
//        if(map.isWin){
        return gameState;
    }

//    **PRINTING**

    //    EFFECT: prints welcome dialogue (or nothing) >>launch straight in?
    public void printWelcomeText() {
//        todo: not final text
        System.out.println("**WELCOME TO THE NEXT INSTALLMENT OF...**"+'\n'+
                "Some kind of game maybe?" +'\n'+
                "Enter 'h' for help");
    }

    // EFFECT: print most controls
    private void printHelp(){
//        todo: not final text
        System.out.println("Enter n, s, e, or w to move North, South, East, or West respectively."+'\n'+
                "'m' to view the map"+'\n'+
                "'h' to get help dialogue"+'\n'+
                "'q' to quit");
    }
    // REQUIRES: gameOver is in the interval [1, 3]
    // EFFECT: prints end text based on int gameOver
    public void printEndText(int gameOver) {
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

}
