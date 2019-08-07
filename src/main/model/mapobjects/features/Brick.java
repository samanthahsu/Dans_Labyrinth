package model.mapobjects.features;

import model.mapobjects.items.Poptart;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.regex.Pattern;

public class Brick extends Feature {

    private static final String TRIVIA_42 = "https://numbersapi.p.rapidapi.com/42/trivia?fragment=true&notfound=floor&json=false";
    private boolean solved = false;

    private String question = "";
    private String answer = "42";

    public Brick(int y, int x) {
        super(y, x);
        name = "brick";
        description = "a brick shaped monitor sits on the floor";
        examineDescription = "a number keypad is on the right side, with a large button above it"; //todo fix text
        Random random = new Random();
        answer = Integer.toString(random.nextInt(20));
        initConnection();
    }

    /*temp for tests*/
    public String getAnswer() {
        return answer;
    }

    /*todo test only*/
    public boolean isSolved() {
        return solved;
    }

    /*require:
    * modifies: this
    * effects: */
    private void initConnection() {
        HttpsURLConnection apiCon;
        try {
            apiCon = (HttpsURLConnection) (new URL(TRIVIA_42)).openConnection();
            apiCon.setRequestProperty("x-rapidapi-host", "numbersapi.p.rapidapi.com");
            apiCon.addRequestProperty("x-rapidapi-key", "50e78147b7msh88cadc2841a1911p17987cjsnf0b56c8e7791");
            apiCon.setUseCaches(false);
            apiCon.setDoInput(true);
            apiCon.setDoOutput(true);
            apiCon.setRequestMethod("GET");
            parseJson(apiCon);
        } catch (IOException e) {
            notifyObservers("Brick failed construction!");
        }
    }

    /*effects: retrieves question and answer from random trivia api*/
    private void parseJson(HttpsURLConnection apiCon) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(apiCon.getInputStream()));
        String jsonLine;
        in.readLine();
        jsonLine = in.readLine();
        question = jsonLine.substring(8);
        in.close();
    }

    @Override
    /*requires:
    * modifies:
    * effects: enters instance where it accepts turning the knob to produce random trivia relating to number 42*/
    public boolean examine(String ui) {
        initConnection();
        if (solved) {
            notifyObservers("The poor brick has no more treats to offer.");
            return true;
        } else if (Pattern.matches("press (large )?button", ui)) {
            notifyObservers("The screen displays:\n" + question);
            return true;
        } else if (Pattern.matches("(type |enter |input )?42( into keypad)?", ui)) {
            solved = true;
            ejectPoptart();
            return true;
        } else if (Pattern.matches("(type |enter |input )?[0-9]*( into keypad)?", ui)) {
            notifyObservers("The screen flashes red in rejection.");
        }
        return false;
    }

    private void ejectPoptart() {
        map.addExaminable(new Poptart(), yc, xc);
        notifyObservers("The front of the brick swings open revealing a poptart inside.");
    }
}
