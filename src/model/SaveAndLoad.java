package model;

import model.creatures.Exo;
import model.items.Apple;

import java.io.*;
import java.util.ArrayList;

public class SaveAndLoad {

    //    todo: add option to get current filepath instead of having this static one
    private static final String PROJECT_PATH = System.getProperty("user.dir");
//    markers in mazeGame files
    private static final String FILE_DIMENSION_MARKER = "<dimensions>";
    private static final String FILE_MAP_MARKER = "<map>";
    private static final String FILE_MAP_DISPLAY_MARKER = "<mapDisplay>";
    private static final String FILE_WIN_YX_MARKER = "<winCoord>";
    private static final String FILE_AVA_YX_MARKER = "<avaCoord>";
    private static final String FILE_ITEMS_START_MARKER = "<avaItems>";
    private static final String FILE_ITEMS_STOP_MARKER = "</avaItems>";
    private static final String FILE_INTER_START_MARKER = "<interactables>";
    private static final String FILE_INTER_STOP_MARKER = "</interactables>";
    private static final String FILE_CREATURE_EXO_MARKER = "<exo>";
    private static final String FILE_ITEM_APPLE_MARKER = "<apple>";

    //    REQUIRES: nm, map and ava are not null
    //    EFFECTS: saves current game state into a text file (either new or overwritten)
//      called: "nm.text"
    public void saveGame(String nm, Map map, Avatar ava) {
        String savePath = PROJECT_PATH + "\\saves\\" + nm;
        File file = new File(savePath);
        try {
            if (file.createNewFile()) {
                System.out.println("New save file created");
            } else {
                System.out.println("Save file already present");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("file creation throws exception");
        }

        try (FileWriter fileWriter = new FileWriter(savePath)) {
            fileWriter.write(makeFile(map, ava));
            System.out.println("Saved to file: " + nm);
        } catch (Exception e) {
            System.out.println("Unable to save!");
        }
    }

    //    EFFECTS: if file "nm" exists in the saves folder of project directory,
//      creates and returns the map saved in the file
//      otherwise prints failed message and return null
//    https://www.journaldev.com/709/java-read-file-line-by-line
    public Map loadFile(String nm) { // todo add items section also split into more functions
        try {
            BufferedReader reader = new BufferedReader((new FileReader(PROJECT_PATH
                    + "\\saves\\" + nm)));
            String line = reader.readLine();

            int height = 0;
            int width = 0;
            String mapString = "";
            String displayMapString = "";
            int winY = 0;
            int winX = 0;
            int startY = 0;
            int startX = 0;
            ArrayList<Interactable> interactables = new ArrayList<>();
            ArrayList<Interactable> items = new ArrayList<>();

            while (line != null) {
                switch (line) {
                    case FILE_DIMENSION_MARKER:
                        height = Integer.parseInt(reader.readLine());
                        width = Integer.parseInt(reader.readLine());
                        break;
                    case FILE_MAP_MARKER:
                        for (int i = 0; i < height; i++) {
                            mapString = mapString.concat(reader.readLine());
                        }
                        break;
                    case FILE_MAP_DISPLAY_MARKER:
                        for (int i = 0; i < height; i++) {
                            displayMapString = displayMapString.concat(reader.readLine());
                        }
                        break;
                    case FILE_WIN_YX_MARKER:
                        winY = Integer.parseInt(reader.readLine());
                        winX = Integer.parseInt(reader.readLine());
                        break;
                    case FILE_AVA_YX_MARKER:
                        startY = Integer.parseInt(reader.readLine());
                        startX = Integer.parseInt(reader.readLine());
                        break;
                    case FILE_INTER_START_MARKER:
                        interactables = loadInteractables(line, reader);
                        break;
                    case FILE_ITEMS_START_MARKER:
                        items = loadItems(line, reader);
                        break;

                    default:
                }
                line = reader.readLine(); // read next line
            }
            reader.close();

            System.out.println("Game Loaded");

            return new Map(height, width, mapString, displayMapString, startY, startX,
                    winY, winX, interactables, items);

        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Loading failed.");
        }
        return null;
    }

//    EFFECTS: loads items into array
    private ArrayList<Interactable> loadItems(String line, BufferedReader reader) throws IOException {
        ArrayList<Interactable> iarraylist = new ArrayList<>();
        line = reader.readLine();
        while (!line.equals(FILE_ITEMS_STOP_MARKER)) {
            if (line != null) {
                iarraylist.add(loadOneItem(reader.readLine()));
            }
            line = reader.readLine();
        }
        return iarraylist;
    }

//    REQUIRES: line is the name of a valid item
//    EFFECTS: returns corresponding interactable else return null
    private Interactable loadOneItem(String line) {
        Interactable i;
        switch (line) {
            case "apple":
                i = new Apple();
                break;
            default:
                return null;
        }
        return i;
    }

    // REQUIRES: line and reader are not null
//    EFFECTS: adds each creature onto tempCreatureList until creature stop marker is reach
//    then returns tempCreatureList
    private ArrayList<Interactable> loadInteractables(String line, BufferedReader reader) throws IOException {
        ArrayList<Interactable> iarraylist = new ArrayList<>();
        int y;
        int x;
        while (!line.equals(FILE_INTER_STOP_MARKER)) {
            switch (line) {
                case FILE_CREATURE_EXO_MARKER:
                    y = Integer.parseInt(reader.readLine());
                    x = Integer.parseInt(reader.readLine());
                    iarraylist.add(new Exo(y, x));
                    break;
                default:
            }
            line = reader.readLine();
        }
        return iarraylist;
    }

    //    EFFECTS: compiles all game information to be saved into a string
    private String makeFile(Map map, Avatar ava) {
        System.out.println("making file to be saved...");
        return FILE_DIMENSION_MARKER + '\n'
                + Integer.toString(map.getHeight()) + '\n'
                + Integer.toString(map.getWidth()) + '\n'
                + FILE_MAP_MARKER + '\n'
                + charMatrixToString(map.getMap())
                + FILE_MAP_DISPLAY_MARKER + '\n'
                + charMatrixToString(map.getMapDisplay())
                + FILE_WIN_YX_MARKER + '\n'
                + Integer.toString(map.getWinY()) + '\n'
                + Integer.toString(map.getWinX()) + '\n'
                + FILE_AVA_YX_MARKER + '\n'
                + Integer.toString(ava.getYpos()) + '\n'
                + Integer.toString(ava.getXpos()) + '\n'
                + FILE_ITEMS_START_MARKER + '\n'
                + saveItems(ava)
                + FILE_ITEMS_STOP_MARKER + '\n'
                + FILE_INTER_START_MARKER + '\n'
                + saveInteractables(map) + '\n'
                + FILE_INTER_STOP_MARKER;
    }

//    EFFECTS: returns string of items held by ava
    private String saveItems(Avatar ava) {
        String returnStr = "";
        for (Interactable i : ava.getItems()) {
            returnStr = returnStr.concat(i.getName()+'\n');
        }
        return returnStr;
    }

    //    REQUIRES: map is not null
//    EFFECTS: returns interactables as strings in saving form
    private String saveInteractables(Map map) {
        String returnString = "";
        for (ArrayList<Interactable> is: map.getInteractables()) {
            for (Interactable i : is) {
                if (i.getName() != null) {
                    switch (i.getName()) {
                        case "Exo":
                            returnString = returnString.concat(FILE_CREATURE_EXO_MARKER + '\n'
                                    + i.startY + '\n'
                                    + i.startX + '\n'); // todo pull method for this
                            break;
                        case "apple":
                            returnString = returnString.concat(FILE_ITEM_APPLE_MARKER + '\n'
                                    + i.startY + '\n'
                                    + i.startX + '\n');
                            break;
                        default:
                    }
                }
            }
        }
        return returnString;
    }

    //    EFFECTS: converts char[][] to string
    private String charMatrixToString(char[][] chars) {
        String temp = "";
        for (char[] charArray : chars) {
            temp = temp.concat(new String(charArray) + "\n");
        }
        return temp;
    }
}
