package ui;

//scanner code from B4 little calculator
// this class handles the overview

import model.Avatar;
import model.Map;
import model.MazeGame;
import model.SaveAndLoad;

import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int continueGame = 0;

    private static final String default_map =
                    "@@@@@@@@@ @@@@@@@@@@@"+
                    "@     @ @ @       @ @"+
                    "@ @ @ @ @   @@@ @ @ @"+
                    "@@@ @ @ @@@@@ @ @ @ @"+
                    "@   @   @   @   @ @ @"+
                    "@ @@@@@@@ @ @@@@@   @"+
                    "@ @ @     @   @     @"+
                    "@ @   @  @@ @ @ @@@@@"+
                    "@ @@@@@@  @ @ @ @   @"+
                    "@         @@@       @"+
                    "@@@@@@@@@@@@@@@@@@@@@";
    private static final int default_width = 21; // hardcoded for now just to see it working
    private static final int default_height = 10;
    private static final int default_startX = 3;
    private static final int default_startY = 7;
    private static final int winX = 9;
    private static final int winY = 0;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random ran = new Random();

        SaveAndLoad svl = new SaveAndLoad();
        MazeGame game = new MazeGame();

        int gameState = 0; // 0=keep going, 1=quit, 2=death, 3=victory, 5=new game, 6=loadgame
        gameState = game.runHomeScreen(scanner, svl);


        Avatar ava = new Avatar(default_startY, default_startX);
        Map map = new Map(default_height, default_width, default_map, default_startY, default_startX, winY,winX);

        String input;

//        while loop for when the game is being played
        while (gameState==continueGame) {
//            map.printTileDescription(); // each tile is a room???? doesn't make too much sense
            input = scanner.nextLine();
            gameState = game.execute(input, map, ran, ava, scanner, svl); // each move is one tick

            if(map.isWin(ava)){
                System.out.println("CONGRATS YOU WON!");
            }
//            victory = check if victory;
        }
        game.printEndText(gameState);
    } // END OF MAIN
}
