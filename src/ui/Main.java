package ui;

// this class launches the game

import model.MazeGame;

public class Main {

    public static void main(String[] args) {

        MazeGame game = new MazeGame();
        game.runHomeScreen();
        }
}

/*
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
*/
