package ui;

// this class launches the game

import model.MazeGame;

public class Main {

    public static void main(String[] args) {

        MazeGame game = new MazeGame();
        System.out.println("**WELCOME TO THE NEXT INSTALLMENT OF...**" + '\n'
                + "Some kind of game maybe?" + "\n"
                + "====================================");
        game.runHomeScreen();
    }
}