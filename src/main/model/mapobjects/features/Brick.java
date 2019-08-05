package model.mapobjects.features;

import model.mapobjects.items.Poptart;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;

public class Brick extends Feature {

    static final String TRIVIA_42 = "https://numbersapi.p.rapidapi.com/42/trivia?fragment=true&notfound=floor&json=true";
    boolean solved = false;

    public Brick(int y, int x) {
        super(y, x);
        name = "brick";
        description = "a brick shaped monitor sits on the floor";
        examineDescription = "a number keypad is on the right side, with a large button above it";
        try {
            initConnection();
        } catch (IOException e) {
            notifyObservers("Brick failed construction!");
        }
    }

    /*todo test only*/
    public boolean isSolved() {
        return solved;
    }

    private String initConnection() throws IOException {
        HttpsURLConnection apiCon;
        apiCon = (HttpsURLConnection) (new URL(TRIVIA_42)).openConnection();
        apiCon.setRequestProperty("x-rapidapi-host", "numbersapi.p.rapidapi.com");
        apiCon.addRequestProperty("x-rapidapi-key", "50e78147b7msh88cadc2841a1911p17987cjsnf0b56c8e7791");
        apiCon.setUseCaches(false);
        apiCon.setDoInput(true);
        apiCon.setDoOutput(true);
        apiCon.setRequestMethod("GET");
        return parseJson(apiCon);
    }

    private String parseJson(HttpsURLConnection apiCon) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(apiCon.getInputStream()));
        String jsonLine;
        boolean saveNext = false;
        while ((jsonLine = in.readLine()) != null) {
            String[] words = jsonLine.split(" \"|\": \"|\",|\": |\"");
            for (String w : words) {
                if (saveNext) {
                    return w;
                } else if (w.equals("text")) {
                    saveNext = true;
                }
            }
        }
        in.close();
        return null;
    }

    @Override
    /*requires:
    * modifies:
    * effects: enters instance where it accepts turning the knob to produce random trivia relating to number 42*/
    public boolean examine(String ui) {
        if (solved) {
            notifyObservers("The poor brick has no more treats to offer.");
            return true;
        }
        if (Pattern.matches("press (large )?button", ui)) {
            try {
                notifyObservers("The screen displays: " + initConnection());
            } catch (IOException e) {
                e.printStackTrace(); //todo test this
            }
            return true;
        } else if (Pattern.matches("(type |enter |input )?42( into keypad)?", ui)) {
            solved = true;
            ejectPoptart();
            return true;
        }
        return false;
    }

    private void ejectPoptart() {
        map.addExaminable(new Poptart(), yc, xc);
        notifyObservers("Something ejected from the back of the brick with a pop.");
    }
}
