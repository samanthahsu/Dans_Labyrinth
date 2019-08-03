package ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application implements EventHandler<ActionEvent> {

/*
    public static void main(String[] args) {
        GameRunner game = new GameRunner();
        boolean quit;

        do {
            quit = game.runHomeScreen(); // also runs the rest of the game
        } while (!quit); // can only quit from the home screen now
    }
*/

    private Stage mainWindow;
    Scene scene;
    private javafx.scene.control.Button button;
    private TextField inputBar;
    private ListView<String> outputDisplay;

    public static void main(String[] args) {
        launch(args);
    }

    /*called right after launch*/
    @Override
    public void start(Stage primaryStage) {
        initGraphics(primaryStage);
        runGame();
    }

    private void initGraphics(Stage primaryStage) {
        mainWindow = primaryStage;
        primaryStage.setTitle("ello");

//        button = new Button("click me");
        inputBar = new TextField("type here");
        inputBar.setOnAction(this);
        outputDisplay = new ListView<>();
        outputDisplay.setMinHeight(600);
        outputDisplay.getItems().addAll();
        outputDisplay.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        outputDisplay.setEditable(false);

//        StackPane layout = new StackPane();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20 ,20, 20, 20));
        layout.getChildren().addAll(outputDisplay, inputBar);
//        button.setOnAction(this);
//        button.setOnAction(e -> buttonClicked());

        Scene scene = new Scene(layout, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void runGame() {
        GameRunner game = new GameRunner();
        boolean quit;

        do {
            quit = game.runHomeScreen(); // also runs the rest of the game
        } while (!quit); // can only quit from the home screen now
    }

//    private void buttonClicked() {
//        String message = "";
//        ObservableList<String> movies;
//        movies = outputDisplay.getSelectionModel().getSelectedItems();
//
//        for (String m:movies
//                ) {
//            message += m + "\n";
//        }
//        System.out.println(message);
//
//    }

    /*called when user clicks button*/
    @Override
    public void handle(ActionEvent event) {
        print(inputBar.getText());
        inputBar.setText("");
    }

    /*prints message to output*/
    private void print(String message) {
        outputDisplay.getItems().add(message);
        if (outputDisplay.getItems().size() > 20) {
            outputDisplay.getItems().remove(0);
        }
    }

}