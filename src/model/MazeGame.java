package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class MazeGame {

    private static final int continueGame = 0;
    private static final int quitGame = 1;
    private static final int failGame = 2;
    private static final int winGame = 3;
    private static final int loopHome = 4;

    private static final String FILE_PATH = "C:\\Users\\shsu8\\IdeaProjects\\cpsc210\\project_sam7891\\saves\\";
    private static final String FILE_DIMENSION_MARKER = "<dimensions>";
    private static final String FILE_MAP_MARKER = "<map>";
    private static final String FILE_MAPDISPLAY_MARKER = "<mapDisplay>";
    private static final String FILE_WINCOORD_MARKER = "<winCoord>";
    private static final String FILE_AVACOORD_MARKER = "<avaCoord>";

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
                gameState = handleQuit(scnr, map, avatar);
                break;
            default:
                System.out.println("Confusion ensues.");
        }
//        if(map.isWin){
        return gameState;
    }

//    **PRINTING**

    //    EFFECT: prints welcome dialogue, and handles options to start new game,
    //      load saved game, and help
    public int runHomeScreen(Scanner scnr) {
        int gameState = loopHome;
        System.out.println("**WELCOME TO THE NEXT INSTALLMENT OF...**"+'\n'+
                "Some kind of game maybe?"+"\n"+
                "====================================");

        while (gameState == loopHome) {
            System.out.println( "Enter 'n' for new game, 'l' to load a saved " +
                    "game, 'h' for more controls, 'q' to end program");
            gameState = homeExecute(scnr.nextLine(), scnr);
        }

        return gameState;
    }

//    EFFECTS: handles available commands from the home screen
    private int homeExecute(String input, Scanner scnr) {
        switch (input) {
            case "n":
                System.out.println("Starting new game...");
                break;
            case "l":
                System.out.println("Enter name of saved file: ");
                loadFile(scnr);
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

//    EFFECTS: loads the file if given named file exists,
//      otherwise does nothing and sets loops around
//    https://www.journaldev.com/709/java-read-file-line-by-line
    private void loadFile(Scanner scnr) {
        BufferedReader reader;
        String nm = scnr.nextLine();
        try {
            reader = new BufferedReader((new FileReader(FILE_PATH+nm)));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
//                todo do stuff here
                line = reader.readLine(); // read next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File loading failed.");
        }
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
    private int handleQuit(Scanner scnr, Map map, Avatar ava) {
        System.out.println
                ("Enter 's' to save, 'c' to cancel or 'q' again to quit without saving.");
        switch (scnr.nextLine()) {
            case "s":
                System.out.println("What would you like to name your file?");
                saveGame(scnr.nextLine(), map, ava);
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
    private void saveGame(String nm, Map map, Avatar ava) {

        try (FileWriter fileWriter = new FileWriter
                (FILE_PATH +nm)){
            fileWriter.write(makeFile(map, ava));
            System.out.println("Saved to file: " + nm);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to save!");
        }
    }

//    EFFECTS: compiles all information to be saved into a string
    private String makeFile(Map map, Avatar ava) {
        System.out.println("making file to be saved...");
        return FILE_DIMENSION_MARKER + '\n' +
                Integer.toString(map.getHeight()) + '\n' +
                Integer.toString(map.getWidth()) + '\n' +
                FILE_DIMENSION_MARKER + '\n' +
                FILE_MAP_MARKER + '\n' +
                charMatrixtoString(map.getMap()) + '\n' +
                FILE_MAP_MARKER +
                FILE_MAPDISPLAY_MARKER + '\n' +
                charMatrixtoString(map.getMapDisplay()) + '\n' +
                FILE_MAPDISPLAY_MARKER +
                FILE_WINCOORD_MARKER + '\n' +
                Integer.toString(map.getWinY()) + '\n' +
                Integer.toString(map.getWinX()) + '\n' +
                FILE_WINCOORD_MARKER + '\n' +
                FILE_AVACOORD_MARKER + '\n' +
                Integer.toString(ava.getY()) + '\n' +
                Integer.toString(ava.getX()) + '\n' +
                FILE_AVACOORD_MARKER;
    }

//    EFFECTS: converts cha[][] to string
    private String charMatrixtoString(char[][] chars) {
        String temp = "";
        for (char[] charArray : chars) {
            temp.concat(new String(charArray) + "\n");
        }
        System.out.println("converting map to string...");
        return temp;
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
