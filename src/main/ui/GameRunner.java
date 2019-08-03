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
import model.Map;
import model.WriterReader;
import model.exceptions.MapException;
import model.mapobjects.Examinable;
import model.mapobjects.Tile;
import model.mapobjects.creatures.Ennui;
import model.mapobjects.items.Item;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/*Main hub which manages all game processes*/
public class GameRunner extends Application implements EventHandler<ActionEvent> {

    private static final int CONTINUE_GAME = 0;
    private static final int QUIT_GAME = 1;
    private static final int FAIL_GAME = 2; // todo make able to die
    private static final int WIN_GAME = 3;
    public static final String WIN_VISUALS = "                                  .''.\n"
            + "        .''.             *''*    :_\\/_:     .\n"
            + "       :_\\/_:   .    .:.*_\\/_*   : /\\ :  .'.:.'.\n"
            + "   .''.: /\\ : _\\(/_  ':'* /\\ *  : '..'.  -=:o:=-\n"
            + "  :_\\/_:'.:::. /)\\*''*  .|.* '.\\'/.'_\\(/_'.':'.'\n"
            + "  : /\\ : :::::  '*_\\/_* | |  -= o =- /)\\    '  *\n"
            + "   '..'  ':::'   * /\\ * |'|  .'/.\\'.  '._____\n"
            + "       *        __*..* |  |     :      |.   |' .---\"|\n"
            + "        _*   .-'   '-. |  |     .--'|  ||   | _|    |\n"
            + "     .-'|  _.|  |    ||   '-__  |   |  |    ||      |\n"
            + "     |' | |.    |    ||       | |   |  |    ||      |\n"
            + "  ___|  '-'     '    \"\"       '-'   '-.'    '`      |____\n"
            + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
            + "                       ~-~-~-~-~-~-~-~-~-~   /|\n"
            + "          )      ~-~-~-~-~-~-~-~  /|~       /_|\\\n"
            + "        _-H-__  -~-~-~-~-~-~     /_|\\    -~======-~\n"
            + "~-\\XXXXXXXXXX/~     ~-~-~-~     /__|_\\ ~-~-~-~\n"
            + "~-~-~-~-~-~    ~-~~-~-~-~-~    ========  ~-~-~-~";
    private static final String GAME_TITLE = "DAN'S LABYRINTH";

    private static Integer gameState;
    private Map map;
    private Scanner scnr;
    private WriterReader writerReader;
    private String ui;
//    private FakeTerminal fakeTerminal;
    private Printer out;
//    todo make input string class wide
    private Stage mainWindow;
    Scene scene;
    private TextField inputBar;
    private ListView<String> outputDisplay;

    public static void main(String[] args) {
        launch(args);
    }

    /*called right after launch*/
    @Override
    public void start(Stage primaryStage) {
        initGameRunner();
        initGraphics(primaryStage);
        runHomeScreen();
    }

    private void initGameRunner() {
        gameState = CONTINUE_GAME;
        scnr = new Scanner(System.in);
        writerReader = new WriterReader();
        ui = "";
    }

    private void initGraphics(Stage primaryStage) {
        mainWindow = primaryStage;
        primaryStage.setTitle(GAME_TITLE);
        setUserAgentStylesheet(STYLESHEET_MODENA);

//        button = new Button("click me");
        inputBar = new TextField();
        inputBar.setPromptText("type here");
        outputDisplay = new ListView<>();
        outputDisplay.setMinHeight(600);
        outputDisplay.setStyle("-fx-font: normal 14px 'monospace' ");
        outputDisplay.getItems().addAll();
        outputDisplay.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        outputDisplay.setEditable(false);
//        StackPane layout = new StackPane();
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20 ,20, 20, 20));
        layout.getChildren().addAll(outputDisplay, inputBar);
        layout.setStyle("-fx-background-color: #303030");
//        button.setOnAction(this);
//        button.setOnAction(e -> buttonClicked());

        Scene scene = new Scene(layout, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

/*
    private void runGame() {
        GameRunner game = new GameRunner();
        boolean quit;

        do {
            quit = game.runHomeScreen(); // also runs the rest of the game
        } while (!quit); // can only quit from the home screen now
    }
*/

//    private void buttonClicked() {
//        String message = "";
//        ObservableList<String> movies;
//        movies = outputDisplay.getSelectionModel().getSelectedItems();
//
//        for (String m:movies
//                ) {
//            message += m + "\n";
//        }
//        System.out.printToDisplay(message);
//
//    }

    /*called when user clicks button*/
    @Override
    public void handle(ActionEvent event) {
        printToDisplay(inputBar.getText());
        inputBar.setText("");
    }

    /*prints and scrolls to message in output, keeps number of messages under 20*/
    private void printToDisplay(String message) {
        outputDisplay.getItems().add(message);
        if (outputDisplay.getItems().size() >= 20) {
            outputDisplay.getItems().remove(0);
            outputDisplay.scrollTo(outputDisplay.getItems().size()-1);
        }
    }
/*
    starts real part of game
    effects: initializes gameState, map, scnr, writerREader, and main.ui
*/
//    GameRunner() {
//        gameState = CONTINUE_GAME;
//        map = null;
//        scnr = new Scanner(System.in);
//        writerReader = new WriterReader();
//        ui = "";
//        fakeTerminal = new FakeTerminal();
//        out = fakeTerminal.getOut();
//    }

    /*
            EFFECTS: while map is uninitialized, prints welcome text, and handles homeScreen commands
            returns true if gameState is QUIT_GAME, else runs the game and returns false
    */
    public boolean runHomeScreen() {
        inputBar.setOnAction(event -> homeExecuteUi());
        printToDisplay("=============DAN'S LABYRINTH=============");
        printToDisplay("new: new game | load: load a saved game | quit: exit");
        return true;
    }
/*
    public boolean runHomeScreen() {

        out.printToDisplay("=============DAN'S LABYRINTH=============");
        while (map == null && gameState != QUIT_GAME) {
            out.printToDisplay("new: new game\n"
                    + "load: load a saved game\n"
                    + "quit: exit");
        }
        homeExecuteUi();
        if (gameState == QUIT_GAME) {
            return true;
        }
        runGame();
        return false;
    }
*/

/*
    MODIFIES: this
    EFFECTS: handles available commands from the home screen, which are:
      start new game: default map is loaded, and calls runGame
      load saved game: selected mazeGame file is loaded, calls runGame
    then sets gameState to appropriate value
*/
    private void homeExecuteUi() {

        printToDisplay(inputBar.getText());
        getTxtAndClearInBar();

        switch (ui) {
            case "new":
                startNewGame();
                inputBar.setOnAction(event -> runGame());
                break;
            case "load":
                printToDisplay("Enter name of saved file");
                inputBar.setOnAction(event -> executeLoadGame());
                break;
            case "quit":
                mainWindow.close();
                break;
            default:
                printToDisplay("Command not available on home screen.");
                break;
        }
    }

    private void executeLoadGame() {
        getTxtAndClearInBar();
        try {
            map = writerReader.readMap(ui);
        } catch (IOException | ClassNotFoundException e) {
            printToDisplay("Loading failed.");
        }
        inputBar.setOnAction(event -> runGame());
    }

    /*builds new default map and then runs the game*/ //todo
    private void startNewGame() {
        printToDisplay("Starting new game...");
        try {
            map = writerReader.buildDefaultMap();
            printToDisplay("New game started!");
            inputBar.setOnAction(event -> runGame());
        } catch (MapException e) {
            printToDisplay("Failed to build new map.");
        }
    }

    /*modifies: this, map
    EFFECTS: runs the main body of the game*/
    private void runGame() {
        getTxtAndClearInBar();
        String[] uiAsWords = ui.split(" ");

        if (execute(uiAsWords)) { // each move is one tick of game clock
            map.nextState();
            if (map.isWin()) {
                printToDisplay("Dan stepped into the sunlight");
                printEndText();
                map = null;
                inputBar.setOnAction(event -> runHomeScreen());
            }
        }
    }

    // EFFECTS: handles which Map functions to call. returns gameState.
    private boolean execute(String[] input) {
        printToDisplay("first word: '" + input[0] + "'");
        if (Pattern.matches("n|s|e|w|examine|pickup|use", input[0])) {
            executeAction(input);
        } else if (Pattern.matches("me|help|quit|map", input[0])) {
            executeInterface(input[0]);
        } else {
            printToDisplay("...");
            return false;
        }
        return true;
    }

    private void executeInterface(String input) {
        switch (input) {
            case "me":
                prntAvaInfo();
                break;
            case "help":
                prntHelpDialg();
                break;
            case "quit":
                handleQuitInGame();
                printToDisplay("s: save game | c: cancel and continue\n"
                        + "q: quit without saving.");
                inputBar.setOnAction(event -> handleQuitInGame());
                break;
            case "map":
                printDisplayMap();
                break;
            default:
        }
    }

    private void prntAvaInfo() {
        printToDisplay("Sanity: " + map.getAva().getSanity()
                + "/3");
        printItems();
    }

    private void executeAction(String[] input) {
        String item;
        if (Pattern.matches(input[0], "(n|s|e|w)")) {
            map.getAva().moveAva(input[0]);
            return;
        }
        switch (input[0]) {
            case "examine":
                enterExamineInstance(input[1]);
                break;
            case "pickup": // change to pick up <item name>
                item = input[1];
                map.getAva().pickUpItem(item);
                break;
            case "use":
                executeUse();
                break;
            default:
        }
    }

    private void executeUse() {
        String item;
        String target;
        item = scnr.next();
        scnr.next("on");
        target = scnr.next();
        map.getAva().useItem(item, target);
    }


    /*modifies: map
     * effects: if the target is in listModel of interactables examine target further*/
    private void enterExamineInstance(String targetNm) {
        Examinable trgtExamnble = map.getAllExaminables().get(targetNm);
        if (trgtExamnble != null) {
            printToDisplay("entering examine instance\n"
                    + Ennui.EXIT_EXAMINATION_KEY + " to exit");
            printToDisplay(trgtExamnble.getExamineDescription());
            inputBar.setOnAction(event -> examineInstance(trgtExamnble));
            examineInstance(trgtExamnble);
        } else {
            printToDisplay("that's not a valid target!");
        }
    }

    private void examineInstance(Examinable trgtExamnble) {
        ui = inputBar.getText();
        if (ui.equals(Examinable.EXIT_EXAMINATION_KEY)) {
            inputBar.setOnAction(event -> runGame());
            printToDisplay("exited examine instance");
        } else if (!trgtExamnble.examine(ui)) {
            printToDisplay("Nothing happened.");
        }
    }

    // EFFECT: printToDisplay user controls and other info *todo make dynamic for each tile
    private void prntHelpDialg() {
        printToDisplay("Enter n, s, e, or w to move North, South, East, or "
                + "West respectively." + '\n'
                + "Available commands:\n"
                + "map" + '\n'
                + "pickup <item>" + '\n'
                + "me" + '\n'
                + "examine <target>" + '\n'
                + "use <item> on <target>\n"
                + "help" + '\n'
                + "quit: quit or save");
    }

    // EFFECTS: prints out current itemList
    private void printItems() {
        HashMap<String, Item> itemList = map.getAva().getCurrItems();
        printToDisplay("You are carrying:");
        Collection<Item> items = itemList.values();
        for (Item i: items) {
            printToDisplay(i.getName());
        }
    }

    // REQUIRES: gameOver is in the interval [1, 3]
    // EFFECT: prints end text based on int gameOver
    private void printEndText() {
        switch (gameState) {
            case QUIT_GAME:
                break;
            case FAIL_GAME:
                printToDisplay("Death: Game Over");
                break;
            case WIN_GAME:
                printToDisplay("You escaped successfully...");
                printWinGraphic();
                break;
            default:
        }
    }

//    EFFECTS: gives user option to
//      save: saves map and status as single file, named by user
//      cancel: continues the game
//      quit: quit the game without saving
    private void handleQuitInGame() {
        getTxtAndClearInBar();
        switch (ui) {
            case "s":
                printToDisplay("What would you like to name your file?");
                inputBar.setOnAction(event -> executeSave());
                break;
            case "c":
                printToDisplay("Dan continues on...");
                break;
            case "q":
                printToDisplay("Exiting to home screen...");
                runHomeScreen();
                return;
            default:
        }
    }

    private void getTxtAndClearInBar() {
        ui = inputBar.getText();
        inputBar.setText("");
    }

    private void executeSave() {
        inputBar.setOnAction(event -> save());
    }

    private void save() {
        getTxtAndClearInBar();
        try {
            writerReader.writeMap(map, ui);
        } catch (IOException e) {
            printToDisplay("File saving failed.");
        } finally {
            printToDisplay("Dan continues on...");
        }
    }

    /*
            REQUIRES: map has been initialized properly
            EFFECTS: prints current map to screen
    */
    private void printDisplayMap() {
        int height = map.getHeight();
        int width = map.getWidth();
        List<List<Tile>> tileMatrix = map.getTileMatrix();
        StringBuilder mapString = new StringBuilder();
        for (int m = 0; m < height; m++) {
            for (int n = 0; n < width; n++) {
               mapString.append(tileMatrix.get(m).get(n).getDisplayChar());
            }
            mapString.append('\n');
        }
        printToDisplay(mapString.toString());
    }

/*
        EFFECTS: prints feedback when ava tries to move into a WALL tile todo move to observer
*/
    public static void printMovePlaceholder(String dir) {
        Random ran = new Random();
        switch (ran.nextInt(4)) {
            case 0:
                System.out.println("Dan smacks hilariously against the " + dir + " WALL.");
                break;
            case 1:
                System.out.println("Dan stubs his toe painfully on the " + dir + " WALL.");
                break;
            case 2:
                System.out.println("Dan flops desperately against the " + dir + " WALL.");
                break;
            case 3:
                System.out.println("Dan sits and ponders how his life has culminated in this moment.");
                break;
            default:
        }
    }

    //    EFFECTS: prints celebratory graphic
    private void printWinGraphic() {
        printToDisplay("As far as Dan is concerned, pizza had been delivered and eaten, another successful day.");
        printToDisplay(WIN_VISUALS);
    }
}
