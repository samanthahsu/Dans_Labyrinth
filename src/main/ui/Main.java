package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Map;
import model.WriterReader;
import model.exceptions.BadFileNameException;
import model.exceptions.MapException;
import model.mapobjects.Avatar;
import model.mapobjects.Examinable;
import model.mapobjects.Tile;
import model.mapobjects.items.Item;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/*Main hub which manages all game processes*/
public class Main extends Application implements PrintObserver {

    private static final String EXIT_EXAMINATION_KEY = "back";
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
    private static final String NEW_GAME_TEXT =
            "Dan blinks, and it's still dark, he can barely see the floor a few feet in "
            + "front\nof him. As if to make up for it, every sound he makes is amplified, echoing\nthrough the caverns"
            + "His only comfort, the warmth of the pizzabox in his hands,\nand the protection of the familiar cheesy"
            + " smell wrapping around him.\nJust another delivery for Dan the pizza man.";
    private static final String HELP_ABBRV = "'help' for controls";
    private static final String CONTINUE_TEXT = "Dan could've sworn he had fallen asleep for a moment. "
            + "Ah well, dreaming would\nnot get him out of here.";
    private static final String PRINT_AVA_STATUS_CMD = "dan";
    private static final String UI_INDICATOR = ">> ";
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 800;
    private static final int SPACING = 10;
    private static final int PADDING = 20;
    private static final String MSG_GAME_OVER = "Game Over";
    private static final String MSG_WIN = "Dan stepped into the sunlight.";
    private static final String MSG_QUIT = "Thanks for playing!";
    private static final String MSG_CONTINUE = "Dan continues on...";
    static final String URL_STYLE_DEFAULT_CSS = "\\src\\main\\ui\\style_default.css";
    static final String URL_STYLE_WIN_CSS = "\\src\\main\\ui\\style_win.css";
    private static double barHeight;

    private WriterReader writerReader;
    private static Integer gameState;
    private Map map;
    private String ui;

    private Stage mainWindow;
    private ListView<String> outputDisplay;
    private TextField inputBar;
    private Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /*called right after launch*/
    public void start(Stage primaryStage) {
        initGameRunner();
        initGraphics(primaryStage);
        runHomeScreen();
    }

    private void initGameRunner() {
        gameState = CONTINUE_GAME;
        writerReader = new WriterReader();
        writerReader.addObserver(this);
        ui = "";
    }

    private void initGraphics(Stage primaryStage) {
        mainWindow = primaryStage;
        mainWindow.setResizable(false);
        primaryStage.setTitle(GAME_TITLE);
        setUserAgentStylesheet(STYLESHEET_MODENA);
        inputBar = new TextField();
        inputBar.setPromptText("type here");
        barHeight = inputBar.getHeight();
        formatOutputDisplay();

        VBox layout = formatVbox();
        scene = new Scene(layout, SCENE_WIDTH, SCENE_HEIGHT);
        clearAndSetSceneStyle(URL_STYLE_DEFAULT_CSS);
        primaryStage.setScene(scene);
        inputBar.requestFocus();
        primaryStage.show();
    }

    private void clearAndSetSceneStyle(String url) {
        scene.getStylesheets().clear();
        addSceneStyle(url);
        addSceneStyle("\\src\\main\\ui\\style_constant.css");
    }

    private void addSceneStyle (String url) {
        String cssString = System.getProperty("user.dir") + url;
        File cssFile = new File(cssString);
        try {
            scene.getStylesheets().add(cssFile.toURI().toURL().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private VBox formatVbox() {
        VBox layout = new VBox(SPACING);
        layout.setId("main");
        layout.setPadding(new Insets(PADDING, PADDING, PADDING, PADDING));
        layout.getChildren().addAll(outputDisplay, inputBar);
        return layout;
    }

    private void formatOutputDisplay() {
        outputDisplay = new ListView<>();
        outputDisplay.setMinHeight(SCENE_HEIGHT - barHeight - (PADDING * 2) - (SPACING * 4));
        outputDisplay.setStyle("-fx-font: normal 15px 'monospace'");
        outputDisplay.getItems().addAll();
        outputDisplay.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        outputDisplay.setEditable(false);
        outputDisplay.setPrefWidth(SCENE_WIDTH - 2 * PADDING);
    }

    /*prints and scrolls to message in output, keeps number of messages under 20*/
    private void printToDisplay(String message) {
        outputDisplay.getItems().add(message);
        if (outputDisplay.getItems().size() >= 20) {
            outputDisplay.getItems().remove(0);
            outputDisplay.scrollTo(outputDisplay.getItems().size() - 1);
        }
    }

    /**
     * prints welcome text and sets action to homeExecute Ui*/
    private void runHomeScreen() {
        printToDisplay("=============DAN'S LABYRINTH=============");
        printToDisplay("new: new game | load: load a saved game | quit: exit");
        inputBar.setOnAction(event -> homeExecuteUi());
    }

    /*MODIFIES: this
    EFFECTS: handles available commands from the home screen, which are:
      start new game: default map is loaded, and calls runGame
      load saved game: selected mazeGame file is loaded, calls runGame
    then sets gameState to appropriate value*/
    private void homeExecuteUi() {
        consumeUI();
        switch (ui) {
            case "new":
                startNewGame();
                inputBar.setOnAction(event -> runGame());
                break;
            case "load":
                printToDisplay("Enter the name of saved file");
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
        consumeUI();
        clearAndSetSceneStyle(URL_STYLE_DEFAULT_CSS);
        try {
            map = writerReader.readMap(ui);
            map.addObservers(this);
            printToDisplay("Game loaded!");
            printToDisplay(HELP_ABBRV);
            printToDisplay(CONTINUE_TEXT);
            inputBar.setOnAction(event -> runGame());
        } catch (IOException | ClassNotFoundException e) {
            printToDisplay("Loading failed. Returning to home screen...");
            runHomeScreen();
        }
    }

    /*builds new default map and then runs the game*/
    private void startNewGame() {
        printToDisplay("Starting new game...");
        clearAndSetSceneStyle(URL_STYLE_DEFAULT_CSS);
        try {
            map = writerReader.buildDefaultMap();
            map.addObservers(this);
            printToDisplay("New game started!");
            printToDisplay(HELP_ABBRV);
            printToDisplay(NEW_GAME_TEXT);
            map.getAva().printExaminables();
            inputBar.setOnAction(event -> runGame());
        } catch (MapException e) {
            printToDisplay("Failed to build new map. (Many apologies)");
        }
    }

    /*modifies: this, map
    EFFECTS: runs the main body of the game*/
    private void runGame() {
        consumeUI();
        String[] uiAsWords = ui.split(" ");

        if (execute(uiAsWords)) { // each move is one tick of game clock
            map.nextState();
            if (map.isWin()) {
                printEndText();
                map = null;
                inputBar.setOnAction(event -> runHomeScreen());
            }
        }
    }

    private void printUiToDisplay() {
        printToDisplay(UI_INDICATOR + ui);
    }

    // EFFECTS: handles which Map functions to call. returns gameState.
    private boolean execute(String[] input) {
        if (Pattern.matches("n|s|e|w|examine|pickup|use", input[0])) {
            executeAction(input);
        } else if (Pattern.matches(PRINT_AVA_STATUS_CMD + "|help|quit|map", input[0])) {
            executeInterface(input[0]);
        } else {
            printToDisplay("...");
            return false;
        }
        return true;
    }

    private void executeInterface(String input) {
        switch (input) {
            case PRINT_AVA_STATUS_CMD:
                prntAvaInfo();
                break;
            case "help":
                printHelp();
                break;
            case "quit":
                printToDisplay("s: save game | c: cancel and continue | "
                        + "q: quit");
                inputBar.setOnAction(event -> handleQuitInGame());
                break;
            case "map":
                printDisplayMap();
                break;
            default:
        }
    }

    private void executeAction(String[] input) {
        if (Pattern.matches("(n|s|e|w)", input[0])) {
            map.getAva().moveAva(input[0]);
            return;
        }
        try {
            switchAction(input);
        } catch (ArrayIndexOutOfBoundsException e) {
            printToDisplay("Dan wonders which thing he should be doing the thing to");
        }
    }

    private void switchAction(String[] input) {
        switch (input[0]) {
            case "examine":
                enterExamineInstance(input[1]);
                break;
            case "pickup":
                map.getAva().pickUpItem(input[1]);
                break;
            case "use":
                executeUse(input);
                break;
            default:
        }
    }

    private void executeUse(String[] input) {
        if (input.length == 4) {
            map.getAva().useItem(input[1], input[3]);
        }
    }

    /*modifies: map
     * effects: if the target is in listModel of interactables examine target further*/
    private void enterExamineInstance(String targetNm) {
        Examinable target = map.getAllExaminables().get(targetNm);
        if (Objects.equals(targetNm, Avatar.NAME)) {
            printToDisplay("Dan is very well thank you.");
        } else if (map.getAva().getCurrItems().containsKey(targetNm)) {
            printToDisplay(map.getAva().getCurrItems().get(targetNm).getDescription());
        } else if (target != null) {
            printToDisplay("Entering examine instance.\n"
                     + EXIT_EXAMINATION_KEY + ": exit");
            printToDisplay(target.getExamineDescription());
            inputBar.setOnAction(event -> examineInstance(target));
        } else {
            printToDisplay("That's not a valid target!");
        }
    }

    private void examineInstance(Examinable examinable) {
        consumeUI();
        if (Pattern.matches("(" + EXIT_EXAMINATION_KEY + "|n|s|e|w)", ui)) {
            inputBar.setOnAction(event -> runGame());
            printToDisplay("Exited examine instance.");
        } else if (!examinable.examine(ui)) {
            printToDisplay("Nothing happened...");
        }
    }

    private void prntAvaInfo() {
        printToDisplay("Sanity: " + map.getAva().getSanity()
                + "/3");
        printItems();
    }

    // EFFECT: printToDisplay user controls and other info
    private void printHelp() {
        printToDisplay("Enter n, s, e, or w to move North, South, East, or "
                + "West respectively." + '\n'
                + "Available commands:\n"
                + "map" + '\n'
                + "pickup <item>" + '\n'
                + PRINT_AVA_STATUS_CMD + ": view player status" + '\n'
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
        outputDisplay.getItems().clear();
        printToDisplay(MSG_WIN);
        printToDisplay("As far as he is concerned, pizza had been "
                + "delivered and eaten, another successful day.");
        clearAndSetSceneStyle(URL_STYLE_WIN_CSS);
        inputBar.setOnAction(event -> runHomeScreen());
    }

    //    EFFECTS: gives user option to
//      save: saves map and status as single file, named by user
//      cancel: continues the game
//      quit: quit the game without saving
    private void handleQuitInGame() {
        consumeUI();
        switch (ui) {
            case "s":
                printToDisplay("What would you like to name your file?");
                inputBar.setOnAction(event -> save());
                break;
            case "c":
                printToDisplay(MSG_CONTINUE);
                inputBar.setOnAction(event -> runGame());
                break;
            case "q":
                printToDisplay(MSG_QUIT);
                printToDisplay("Exiting to home screen...");
                runHomeScreen();
                return;
            default:
                printToDisplay("Command not available.");
        }
    }

    /*saves text from and clears the input bar, and prints ">> text" to display*/
    private void consumeUI() {
        ui = inputBar.getText();
        inputBar.setText("");
        printUiToDisplay();
    }

    private void save() {
        consumeUI();
        try {
            writerReader.writeMap(map, ui);
        } catch (IOException e) {
            printToDisplay("File saving failed. Please try again after this has been fixed.");
        } catch (BadFileNameException e) {
            printToDisplay("Names with spaces or special characters are not valid. Please try again.");
        }
        executeInterface("quit");
    }

    /*REQUIRES: map has been initialized properly
      EFFECTS: prints current map to screen*/
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

    @Override
    public void update(String message) {
        printToDisplay(message);
    }
}
