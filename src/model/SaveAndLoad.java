package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveAndLoad {

//    todo: add option to get current filepath instead of having this static one
    private static final String FILE_PATH = "C:\\Users\\shsu8\\IdeaProjects\\cpsc210\\project_sam7891\\saves\\";
//    markers in mazeGame files
    private static final String FILE_DIMENSION_MARKER = "<dimensions>";
    private static final String FILE_MAP_MARKER = "<map>";
    private static final String FILE_MAP_DISPLAY_MARKER = "<mapDisplay>";
    private static final String FILE_WIN_COORD_MARKER = "<winCoord>";
    private static final String FILE_AVA_COORD_MARKER = "<avaCoord>";

//    EFFECTS: creates and returns a Map according to the file if named file exists,
//      otherwise does nothing
//    https://www.journaldev.com/709/java-read-file-line-by-line
    public Map loadFile(Scanner scnr) {
        BufferedReader reader;
        String nm = scnr.nextLine();
        try {
            reader = new BufferedReader((new FileReader(FILE_PATH + nm)));
            String line = reader.readLine();

            int height;
            int width;
            String map;
            String displayMap;
            int winY;
            int winX;
            int startY;
            int startX;

            while (line != null) {
                System.out.println(line);

                if (line.equals(FILE_DIMENSION_MARKER)) {
                    line = reader.readLine();
                    height = Integer.parseInt(line);
                    width = Integer.parseInt(line);
                }


                line = reader.readLine(); // read next line
            }
            reader.close();
            return new Map(height, width, map, displayMap, startY, startX,
                    winY, winX);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File loading failed.");
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
                FILE_DIMENSION_MARKER + '\n' +
                FILE_MAP_MARKER + '\n' +
                charMatrixtoString(map.getMap()) + '\n' +
                FILE_MAP_MARKER +
                FILE_MAP_DISPLAY_MARKER + '\n' +
                charMatrixtoString(map.getMapDisplay()) + '\n' +
                FILE_MAP_DISPLAY_MARKER +
                FILE_WIN_COORD_MARKER + '\n' +
                Integer.toString(map.getWinY()) + '\n' +
                Integer.toString(map.getWinX()) + '\n' +
                FILE_WIN_COORD_MARKER + '\n' +
                FILE_AVA_COORD_MARKER + '\n' +
                Integer.toString(ava.getY()) + '\n' +
                Integer.toString(ava.getX()) + '\n' +
                FILE_AVA_COORD_MARKER;
    }

    //    EFFECTS: converts cha[][] to string
    private String charMatrixtoString(char[][] chars) {
        String temp = "";
        for (char[] charArray : chars) {
            temp.concat(new String(charArray) + "\n");
        }
        System.out.println("converting map to string...");
        return temp;
    }


}
