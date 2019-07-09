package ui;

//scanner code from B4 little calculator

import model.Map;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        boolean gameOver = false;
        Random ran = new Random();
        Map map = new Map(10, 10);

        Scanner scanner = new Scanner(System.in);
        String input;
        printWelcome();
        while (!gameOver) {
//            map.printTileDescription();
            input = scanner.nextLine();
            gameOver = execute(input, map, ran);
//             set gameOver variable
        }
    }


    //EFFECT: chooses which map function to call. returns true when input = 'q'
    private static boolean execute(String input, Map map, Random ran) {
        boolean isQuit = false;
        switch (input) {
            case "n":
                map.printMovePlaceholder("northern", ran);
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
                isQuit = true;
                break;
            default:
                    System.out.println("Confusion ensues.");
        }
        return isQuit;
    }

//    EFFECT: prints welcome dialogue (or nothing) >>launch straight in?
    private static void printWelcome() {
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
}
