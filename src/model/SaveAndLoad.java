package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SaveAndLoad {

//    todo fix it so it doesnt repeat
    private static final int continueGame = 0;
    private static final int quitGame = 1;
    private static final int failGame = 2;
    private static final int winGame = 3;
    private static final int loopHome = 4;

    //    todo: add option to get current filepath instead of having this static one
    private static final String FILE_PATH = "C:\\Users\\shsu8\\IdeaProjects\\cpsc210\\project_sam7891\\saves\\";
//    markers in mazeGame files
    private static final String FILE_DIMENSION_MARKER = "<dimensions>";
    private static final String FILE_MAP_MARKER = "<map>";
    private static final String FILE_MAP_DISPLAY_MARKER = "<mapDisplay>";
    private static final String FILE_WIN_COORD_MARKER = "<winCoord>";
    private static final String FILE_AVA_COORD_MARKER = "<avaCoord>";


//    EFFECTS: creates and returns a Map according to the file if named file exists,
//      otherwise prints failed message.
//    https://www.journaldev.com/709/java-read-file-line-by-line
    public Map loadFile(String nm) {
        BufferedReader reader;
        try {
            reader = new BufferedReader((new FileReader(FILE_PATH + nm)));
            String line = reader.readLine();

            int height=0;
            int width=0;
            String map_String = "";
            String dispMap_String = "";
            int winY=0;
            int winX=0;
            int startY=0;
            int startX=0;

            while (line != null) {

                switch (line) {
                    case FILE_DIMENSION_MARKER:
                        height = Integer.parseInt(reader.readLine());
                        width = Integer.parseInt(reader.readLine());
                        break;
                    case FILE_MAP_MARKER:
                        for (int i = 0; i < height; i++) {
                            map_String = map_String.concat(reader.readLine());
                        }
                        break;
                    case FILE_MAP_DISPLAY_MARKER:
                        for (int i = 0; i < height; i++) {
                            dispMap_String = dispMap_String.concat(reader.readLine());
                        }
                        break;
                    case FILE_WIN_COORD_MARKER:
                        winY = Integer.parseInt(reader.readLine());
                        winX = Integer.parseInt(reader.readLine());
                        break;
                    case FILE_AVA_COORD_MARKER:
                        startY = Integer.parseInt(reader.readLine());
                        startX = Integer.parseInt(reader.readLine());
                        break;
                }
                line = reader.readLine(); // read next line
            }
            reader.close();

            System.out.println("Game Loaded");

            return new Map(height, width, map_String, dispMap_String, startY, startX,
                    winY, winX);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed.");
        }

        return null;
    }


//    EFFECTS: saves current game state into a text file (either new or overwritten)
//      called: "nm.text"
    public void saveGame(String nm, Map map, Avatar ava) {

        try (FileWriter fileWriter = new FileWriter
                (FILE_PATH +nm)){
            fileWriter.write(makeFile(map, ava));
            System.out.println("Saved to file: " + nm);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to save!");
        }
    }

    //    EFFECTS: compiles all game information to be saved into a string
    private String makeFile(Map map, Avatar ava) {
        System.out.println("making file to be saved...");
        return FILE_DIMENSION_MARKER + '\n' +
                Integer.toString(map.getHeight()) + '\n' +
                Integer.toString(map.getWidth()) + '\n' +
                FILE_MAP_MARKER + '\n' +
                charMatrixToString(map.getMap()) +
                FILE_MAP_DISPLAY_MARKER + '\n' +
                charMatrixToString(map.getMapDisplay()) +
                FILE_WIN_COORD_MARKER + '\n' +
                Integer.toString(map.getWinY()) + '\n' +
                Integer.toString(map.getWinX()) + '\n' +
                FILE_AVA_COORD_MARKER + '\n' +
                Integer.toString(ava.getY()) + '\n' +
                Integer.toString(ava.getX());
    }

    //    EFFECTS: converts char[][] to string todo fix
    private String charMatrixToString(char[][] chars) {
        String temp = "";
        for (char[] charArray : chars) {
            temp = temp.concat(new String(charArray) + "\n");
        }
        System.out.println("converting map to string...");
        return temp;
    }

}
