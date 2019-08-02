//import main.model.MapObjects.Avatar;
//import main.model.mapobjects.Examinable;
//import main.model.Map;
//import Exo;
//import AbsolutelyNothing;
//
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//abstract class TestHelper {
//
////    CONSTANT FIELDS
//    protected Map map1;
//    protected static final String TEST_MAP_1 =
//            "@@@ @@"
//            + "@@ @ @"
//            + "@   @@"
//            + "@@@@@@";
//    protected static final int TEST_HEIGHT_1 = 4;
//    protected static final int TEST_WIDTH_1 = 6;
//    protected static final int TEST_START_Y_1 = 2;
//    protected static final int TEST_START_X_1 = 1;
//    protected static final int WIN_Y_1 = 0;
//    protected static final int WIN_X_1 = 3;
//
//    protected static final String TEST_MAP_2 =
//            "@@@"+
//                    "@ @"+
//                    "@ @";
//    protected static final int TEST_HEIGHT_2 = 3;
//    protected static final int TEST_WIDTH_2 = 3;
//    protected static final int TEST_START_Y_2 = 1;
//    protected static final int TEST_START_X_2 = 1;
//    protected static final int WIN_Y_2 = 2;
//    protected static final int WIN_X_2 = 1;
//
//
//    protected Map map3;
//    protected static final String TEST_MAP_3 =
//            "  +  g"
//            + "  +  h"
//            + "  +  i"
//            + "  +  k";
//    protected static final int TEST_HEIGHT_3 = 4;
//    protected static final int TEST_WIDTH_3 = 6;
//    protected static final int TEST_START_Y_3 = 2;
//    protected static final int TEST_START_X_3 = 1;
//    protected static final int WIN_Y_3 = 0;
//    protected static final int WIN_X_3 = 3;
//
//    // FIELDS THAT CHANGE
//    protected static ArrayList<Examinable> interactableArrayList = new ArrayList<>();
//    protected static ArrayList<Examinable> items = new ArrayList<>();
//    protected static int interY1 = 0;
//    protected static int interX1 = 0;
//
//    Avatar ava1;
//    Exo exo1;
//
//    //    REQUIRES: expMapStr is same size as h x w
//    //    EFFECT converts expectedMap string to char matrix of test DEFAULT_HEIGHT and DEFAULT_WIDTH
//    protected char[][] strToTestCharMtrx(String expMapStr, int DEFAULT_HEIGHT, int DEFAULT_WIDTH){
//        char[][] expMatrix = new char[DEFAULT_HEIGHT][DEFAULT_WIDTH];
//        int i = 0;
//        for (int j = 0; j < DEFAULT_HEIGHT; j++) {
//            for(int k = 0; k < DEFAULT_WIDTH; k++){
//                expMatrix[j][k] = expMapStr.charAt(i);
//                i++;
//            }
//        }
//        return expMatrix;
//    }
//    //    REQUIRES: expMapStr is same size as h x w
//    //    EFFECT: passes if expMapStr is equal to mapDisplay when mapDisplay is converted into String
//    protected void assertEqualsStrCharMtrx(String expMapStr, Map mapActual, int h, int w) {
//        char[][] expMap = strToTestCharMtrx(expMapStr, h, w);
//        char[][] actualMap = mapActual.getMapDisplay();
//        assertEqualsCharMtrx(expMap, actualMap, h, w);
//    }
//
//    //    REQUIRES: charMtrxExp and charMtrxActual are matrices of size hxw
//    // EFFECT: passes if two char matrices are identical
//    protected void assertEqualsCharMtrx(char[][] charMtrxExp, char[][] charMtrxActual, int h, int w) {
//        for (int i = 0; i < h; i++) {
//            for (int j = 0; j < w; j++) {
//                assertEquals(charMtrxExp[i][j], charMtrxActual[i][j]);
//            }
//        }
//    }
//
////    EFFECTS: passes if the mapexp == mapactual
//    protected void assertEqualsMap(Map mapexp, Map mapactual) {
//        assertEquals(mapexp.getHeight(), mapactual.getHeight());
//        assertEquals(mapexp.getWidth(), mapactual.getWidth());
//
//        assertEqualsCharMtrx(mapexp.getMap(), mapactual.getMap(), mapactual.getHeight(), mapactual.getWidth());
//        assertEqualsCharMtrx(mapexp.getMapDisplay(), mapactual.getMapDisplay() , mapactual.getHeight(), mapactual.getWidth());
//
//        assertEquals(mapexp.getWinY(), mapactual.getWinY());
//        assertEquals(mapexp.getWinX(), mapactual.getWinX());
//
//        assertEquals(mapexp.getAva().getCurrY(), mapactual.getAva().getCurrY());
//        assertEquals(mapexp.getAva().getCurrX(), mapactual.getAva().getCurrX());
//    }
//
//
//    //    EFFECTS: passes if the files at the two given file paths have matching contents
//    protected void assertEqualsFile(String fpE, String fp) {
//        String file = fileToString(fp);
//        String fileExpected = fileToString(fpE);
//        assertEquals(fileExpected, file);
//    }
//
//    //    EFFECTS: returns files at filePath as string
//    protected String fileToString(String filePath) {
//        String fileContents = "";
//        try {
//            BufferedReader reader = new BufferedReader((new FileReader(filePath)));
//            String line = reader.readLine();
//            fileContents = fileContents.concat(line);
//            while (line != null) {
//                fileContents = fileContents.concat(line);
//                line = reader.readLine();
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("fileToString failed");
//        }
//        return fileContents;
//    }
//
////    EFFECTS: passes if two array lists are equal
//    protected void assertEqualsInteractableMtrx(ArrayList<ArrayList<Examinable>> expected,
//                                                ArrayList<ArrayList<Examinable>> actual,
//                                                int h, int w) {
//        for (int i = 0; i < h; i++) {
//            for (int j = 0; j < w; j++) {
//                assertEqualsInteractable(expected.get(i).get(j), actual.get(i).get(j));
//            }
//        }
//
//    }
//
////    EFFECTS: passes if interactables are the same
//    private void assertEqualsInteractable(Examinable a, Examinable b) {
//        assertEquals(a.getName(), b.getName());
//        assertEquals(a.getCurrY(), b.getCurrY());
//        assertEquals(a.getCurrX(), b.getCurrX());
//    }
//
//    //    EFFECTS: returns interactable matrix built from // todo extract code to method from here and map.initInteractables
//    protected ArrayList<ArrayList<Examinable>> makeInteractableMatrix(ArrayList<Examinable> inter, int h, int w) {
//        ArrayList<ArrayList<Examinable>> interactables = new ArrayList<>();
//        Examinable nothing = new AbsolutelyNothing();
//        ArrayList<Examinable> widthList;
//        for (int i = 0; i < h; i++) {
//            widthList = new ArrayList<>();
//            for (int j = 0; j < w; j++) {
//                widthList.add(nothing);
//            }
//            interactables.add(widthList);
//        }
//        if (inter != null) {
//            for (Examinable AVATAR : inter) {
//                    interactables.get(AVATAR.getCurrY()).set(AVATAR.getCurrX(), AVATAR);
//            }
//        }
//        return interactables;
//    }
//}