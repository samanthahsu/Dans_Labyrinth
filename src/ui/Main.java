package ui;

//scanner code from B4 little calculator

import model.Avatar;
import model.Map;
import model.MazeGame;

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
        MazeGame game = new MazeGame();
        int gameOver = 0; // 0=keep going, 1=quit, 2=death, 3=victory

        Random ran = new Random();
        Avatar avatar = new Avatar();
        Scanner scanner = new Scanner(System.in);

        Map map = new Map(default_height, default_width, default_map, default_startY, default_startX);

        String input;
        game.printWelcomeText();
        while (gameOver==continueGame) {
//            map.printTileDescription(); // each tile is a room???? doesn't make too much sense
            input = scanner.nextLine();
            gameOver = game.execute(input, map, ran, avatar); // each move is one tick
//
//            victory = check if victory;
        }
        game.printEndText(gameOver);
    } // END OF MAIN
}
