package ui;

/*the nice clean diving board*/

public class Main {

    public static void main(String[] args) {
        GameRunner game = new GameRunner();
        boolean quit;

        do {
           quit = game.runHomeScreen(); // also runs the rest of the game
        } while (!quit); // can only quit from the home screen now
    }
}