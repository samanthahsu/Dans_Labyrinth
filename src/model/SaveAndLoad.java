package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SaveAndLoad {

    private static final String FILE_PATH = "C:\\Users\\shsu8\\IdeaProjects\\cpsc210\\project_sam7891\\saves\\";
    private static final String FILE_DIMENSION_MARKER = "<dimensions>";
    private static final String FILE_MAP_MARKER = "<map>";
    private static final String FILE_MAPDISPLAY_MARKER = "<mapDisplay>";
    private static final String FILE_WINCOORD_MARKER = "<winCoord>";
    private static final String FILE_AVACOORD_MARKER = "<avaCoord>";

    //    EFFECTS: loads the file if given named file exists,
//      otherwise does nothing and sets loops around
//    https://www.journaldev.com/709/java-read-file-line-by-line
    public void loadFile(Scanner scnr) {
        BufferedReader reader;
        String nm = scnr.nextLine();
        try {
            reader = new BufferedReader((new FileReader(FILE_PATH + nm)));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
//                todo do stuff here
                line = reader.readLine(); // read next line
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File loading failed.");
        }
    }
    //    EFFECTS: saves current game state into a file (either new or overwritten)
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

    //    EFFECTS: compiles all information to be saved into a string
    private String makeFile(Map map, Avatar ava) {
        System.out.println("making file to be saved...");
        return FILE_DIMENSION_MARKER + '\n' +
                Integer.toString(map.getHeight()) + '\n' +
                Integer.toString(map.getWidth()) + '\n' +
                FILE_DIMENSION_MARKER + '\n' +
                FILE_MAP_MARKER + '\n' +
                charMatrixtoString(map.getMap()) + '\n' +
                FILE_MAP_MARKER +
                FILE_MAPDISPLAY_MARKER + '\n' +
                charMatrixtoString(map.getMapDisplay()) + '\n' +
                FILE_MAPDISPLAY_MARKER +
                FILE_WINCOORD_MARKER + '\n' +
                Integer.toString(map.getWinY()) + '\n' +
                Integer.toString(map.getWinX()) + '\n' +
                FILE_WINCOORD_MARKER + '\n' +
                FILE_AVACOORD_MARKER + '\n' +
                Integer.toString(ava.getY()) + '\n' +
                Integer.toString(ava.getX()) + '\n' +
                FILE_AVACOORD_MARKER;
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
