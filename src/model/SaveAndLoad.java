package model;

import java.io.*;

public class SaveAndLoad {

    //    todo: add option to get current filepath instead of having this static one
    private static final String PROJECT_PATH = System.getProperty("user.dir");
//    markers in mazeGame files
    private static final String FILE_DIMENSION_MARKER = "<dimensions>";
    private static final String FILE_MAP_MARKER = "<map>";
    private static final String FILE_MAP_DISPLAY_MARKER = "<mapDisplay>";
    private static final String FILE_WIN_YX_MARKER = "<winCoord>";
    private static final String FILE_AVA_YX_MARKER = "<avaCoord>";

//    REQUIRES: nm, map and ava are not null
    //    EFFECTS: saves current game state into a text file (either new or overwritten)
//      called: "nm.text"
    public void saveGame(String nm, Map map, Avatar ava) {
        String savePath = PROJECT_PATH+"\\saves\\"+nm;
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

        try (FileWriter fileWriter = new FileWriter
                (savePath)){
            fileWriter.write(makeFile(map, ava));
            System.out.println("Saved to file: " + nm);
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("Unable to save!");
        }
    }

    //    EFFECTS: if file "nm" exists in the saves folder of project directory,
//      creates and returns the map saved in the file
//      otherwise prints failed message and return null
//    https://www.journaldev.com/709/java-read-file-line-by-line
    public Map loadFile(String nm) {
        try {
            BufferedReader reader = new BufferedReader((new FileReader
                    (PROJECT_PATH + "\\saves\\" + nm)));
            String line = reader.readLine();

            int height=0;
            int width=0;
            String mapString = "";
            String displayMapString = "";
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
                }
                line = reader.readLine(); // read next line
            }
            reader.close();

            System.out.println("Game Loaded");

            return new Map(height, width, mapString, displayMapString, startY, startX,
                    winY, winX);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed.");
        }
        return null;
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
                FILE_WIN_YX_MARKER + '\n' +
                Integer.toString(map.getWinY()) + '\n' +
                Integer.toString(map.getWinX()) + '\n' +
                FILE_AVA_YX_MARKER + '\n' +
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
