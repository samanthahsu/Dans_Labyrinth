package model;

import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class MazeGame {
    private static final int continueGame = 0;
    private static final int quitGame = 1;
    private static final int failGame = 2;
    private static final int winGame = 3;


    //EFFECT: chooses which map function to call. returns gameState
    public int execute(String input, Map map, Random ran, Avatar avatar, Scanner scnr) {
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
                gameState = handleQuit(scnr);
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

//    EFFECTS: gives user option to
//      save: saves map and status as single file, named by user
//      cancel: continues the game
//      quit: quit the game without saving
    private int handleQuit(Scanner scnr) {
        System.out.println
                ("Enter 's' to save, 'c' to cancel or 'q' again to quit without saving.");
        switch (scnr.nextLine()) {
            case "s":
                System.out.println("What would you like to name your file?");
                saveGame(scnr.nextLine());
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

//    EFFECTS: saves current game state into a file (either new or overwritten)
//      called: "nm.text"
    private void saveGame(String nm) {

        try (FileWriter fileWriter = new FileWriter
                ("C:\\Users\\shsu8\\IdeaProjects\\cpsc210\\project_sam7891\\saves\\"+nm)){
            fileWriter.write("replace string");
            System.out.println("Saved to file: "+nm);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to save!");
        }
    }


/*
        String currentPath = null;
        try {
            currentPath = new File(".").getCanonicalPath();
            System.out.println("Current directory path using canonical path method :- " + currentPath);

            String usingSystemProperty = System.getProperty("user.dir");
            System.out.println("Current directory path using system property:- " + usingSystemProperty);

        } catch (IOException e) {
            System.out.println("IOException Occured" + e.getMessage());
        }

        File file = new File(currentPath);
        if (file.createNewFile()) {
            System.out.println("File is created.");
        } else {
            System.out.println("File already exists");
        }


        BufferedWriter out = null;
        String fileName =
        String str = "test";
        try {
            out = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert out != null;
        try {
            out.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/

}
