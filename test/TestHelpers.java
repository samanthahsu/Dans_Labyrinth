import model.Interactable;
import model.Map;
import model.NullCreature;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TestHelpers {

    //    REQUIRES: expMapStr is same size as h x w
    //    EFFECT converts expectedMap string to char matrix of test height and width
    protected char[][] strToTestCharMtrx(String expMapStr, int height, int width){
        char[][] expMatrix = new char[height][width];
        int i = 0;
        for (int j = 0; j < height; j++) {
            for(int k = 0; k < width; k++){
                expMatrix[j][k] = expMapStr.charAt(i);
                i++;
            }
        }
        return expMatrix;
    }
    //    REQUIRES: expMapStr is same size as h x w
//    EFFECT: tests pass if expMapStr is equal to mapDisplay when mapDisplay is converted into String
    protected void assertEqualsMapDisp(String expMapStr, Map map, int h, int w) {
        char[][] expMap = strToTestCharMtrx(expMapStr, h, w);
        char[][] actualMap = map.getMapDisplay();
        assertEqualsCharMtrx(expMap, actualMap, h, w);
    }
    //    REQUIRES: ava, b are matrices of size hxw
    // EFFECT: test passes if two char matrices are identical
    protected void assertEqualsCharMtrx(char[][] a, char[][] b, int h, int w) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                assertEquals(a[i][j], b[i][j]);
            }
        }
    }

    protected void assertEqualsMapObj(Map map2, Map newMap) {
        assertEquals(map2.getHeight(), newMap.getHeight());
        assertEquals(map2.getWidth(), newMap.getWidth());

        assertEqualsCharMtrx(map2.getMap(), newMap.getMap(), newMap.getHeight(), newMap.getWidth());
        assertEqualsCharMtrx(map2.getMapDisplay(), newMap.getMapDisplay() , newMap.getHeight(), newMap.getWidth());

        assertEquals(map2.getWinY(), newMap.getWinY());
        assertEquals(map2.getWinX(), newMap.getWinX());

        assertEquals(map2.getAva().getY(), newMap.getAva().getY());
        assertEquals(map2.getAva().getX(), newMap.getAva().getX());


    }


    //    EFFECTS: passes if the files at the two given file paths have matching contents
    protected void assertEqualsFile(String fpE, String fp) {
        String file = fileToString(fp);
        String fileExpected = fileToString(fpE);
        assertEquals(fileExpected, file);
    }

    //    EFFECTS: returns files at filePath as string
    protected String fileToString(String filePath) {
        String fileContents = "";
        try {
            BufferedReader reader = new BufferedReader((new FileReader(filePath)));
            String line = reader.readLine();
            fileContents = fileContents.concat(line);
            while (line != null) {
                fileContents = fileContents.concat(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fileToString failed");
        }
        return fileContents;
    }

//    EFFECTS: passes if two array lists are equal
    protected void assertEqualsInteractableMtrx(ArrayList<ArrayList<Interactable>> expected,
                                                ArrayList<ArrayList<Interactable>> actual,
                                                int h, int w) {
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                assertEqualsInteractable(expected.get(i).get(j), actual.get(i).get(j));
            }
        }

    }

//    EFFECTS: passes if interactables are the same
    private void assertEqualsInteractable(Interactable a, Interactable b) {
        assertEquals(a.getName(), b.getName());
        assertEquals(a.getY(), b.getY());
        assertEquals(a.getX(), b.getX());
    }

    //    EFFECTS: returns interactable matrix built from
    protected ArrayList<ArrayList<Interactable>> InteractableMatrix(ArrayList<Interactable> inter, int h, int w) {
        ArrayList<ArrayList<Interactable>> interactables = new ArrayList<>();
        Interactable nullC = new NullCreature();
        ArrayList<Interactable> widthList = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            widthList.add(nullC);
        }
        for (int i = 0; i < h; i++) {
            interactables.add(widthList);
        }
        if (inter != null) {
            for (Interactable c : inter) {
                if (c.getName() != null) {
                    interactables.get(c.getY()).set(c.getX(), c);
                }

            }
        }
        return interactables;
    }
}
