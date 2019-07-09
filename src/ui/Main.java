package ui;

//scanner code from B4 little calculator

import model.Map;

import java.util.Scanner;

public class Main {
    private static boolean gameOver = false;
    Scanner scanner = new Scanner(System.in);
    Map map;

    public void main(String[] args) {
        String input = "";
        printWelcome();
        map = new Map(11, 21);
        while (!gameOver) {
            map.printTileDescription();
            input = scanner.nextLine();
            execute(input);
        }
    }

    //EFFECT chooses which map function to call
    private void execute(String input) {
        switch (input) {
            case "go n":
            case "go s":
            case "go e":
            case "go w":
        }
    }

    //    EFFECT: prints welcome dialogue (or nothing) >>launch straight in?
    private static void printWelcome() {
    }

}
