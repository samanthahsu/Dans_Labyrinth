package ui;

//scanner code from B4 little calculator

import model.Avatar;
import model.Map;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int continueGame = 0;
    private static final int quitGame = 1;
    private static final int failGame = 2;
    private static final int winGame = 3;

    public static void main(String[] args) {
        int gameOver = 0; // 0=keep going, 1=quit, 2=death, 3=victory

//        boolean hasMap = false; // boolean for when multiple maps can be loaded
        Random ran = new Random();
        Avatar avatar = new Avatar();
        Scanner scanner = new Scanner(System.in);

/*
// todo: have option to read in different maps
        while(!hasMap){
            System.out.println("Available maps: ");
        }
*/

        Map map = new Map();

        String input;
        printWelcomeText();
        while (gameOver==0) {
//            map.printTileDescription(); // each tile is a room???? doesn't make too much sense
            input = scanner.nextLine();
            gameOver = execute(input, map, ran, avatar); // each move is one tick
//             set gameOver variable
//            victory = check if victory;
        }
        printEndText(gameOver);
    } // END OF MAIN

    //EFFECT: chooses which map function to call. returns true when input = 'q'
    private static int execute(String input, Map map, Random ran, Avatar avatar) {
        int gameState = 0;
        switch (input) {
            case "n":
                map.printMovePlaceholder("northern", ran);
                avatar.moveCharN(map);
                break;
            case "s":
                map.printMovePlaceholder("southern", ran);
                break;
            case "e":
                map.printMovePlaceholder("eastern", ran);
                break;
            case "w":
                map.printMovePlaceholder("western", ran);
                break;
            case "look":
                System.out.println("hello darkness.");
                break;
            case "m":
                System.out.println("the map");
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
        return gameState;
    }

//    **PRINTING**

//    EFFECT: prints welcome dialogue (or nothing) >>launch straight in?
    private static void printWelcomeText() {
        System.out.println("**WELCOME TO THE NEXT INSTALLMENT OF...**"+'\n'+
                "Some kind of game maybe?" +'\n'+
                "Enter 'h' for help");
    }

// EFFECT: print most controls
    private static void printHelp(){
        System.out.println("Enter n, s, e, or w to move North, South, East, or West respectively."+'\n'+
                "'m' to view the map"+'\n'+
                "'h' to get help dialogue"+'\n'+
                "'q' to quit");
    }
    //    REQUIRES: gameOver is in the interval [1, 3]
//    EFFECT: prints end text based on int gameOver
    private static void printEndText(int gameOver) {
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
