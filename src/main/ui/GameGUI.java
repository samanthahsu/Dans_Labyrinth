package ui;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameGUI extends Application implements EventHandler<ActionEvent> {

    private Stage window;
    Scene scene;
    private javafx.scene.control.Button button;
    private TextField inputBar;
    private ListView<String> listView;

    public static void main(String[] args) {
        launch(args);
    }

    /*called right after launch*/
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        primaryStage.setTitle("ello");

        button = new Button("click me");
        inputBar = new TextField("type here");
        inputBar.setOnAction(this);
        listView = new ListView<>();
        listView.setMinHeight(600);
        listView.getItems().addAll();
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listView.setEditable(false);

//        StackPane layout = new StackPane();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20 ,20, 20, 20));
        layout.getChildren().addAll(listView, inputBar);
//        button.setOnAction(this);
        button.setOnAction(e -> buttonClicked());
        
        Scene scene = new Scene(layout, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

//        layout.getChildren().add(btn);
//        primaryStage.setScene(new Scene(layout, 300, 250));
//        primaryStage.show();
    }

    private void buttonClicked() {
        String message = "";
        ObservableList<String> movies;
        movies = listView.getSelectionModel().getSelectedItems();

        for (String m:movies
             ) {
            message += m + "\n";
        }
        System.out.println(message);

    }

    /*called when user clicks button*/
    @Override
    public void handle(ActionEvent event) {
        listView.getItems().add(inputBar.getText());
        inputBar.setText("");
        if (listView.getItems().size() > 20) {
            listView.getItems().remove(0);
        }
    }
}
