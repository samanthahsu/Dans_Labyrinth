package model.mapobjects.features;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Pattern;

public class Brick extends Feature {

    static final String TRIVIA_42 = "https://numbersapi.p.rapidapi.com/42/trivia?fragment=true&notfound=floor&json=true";
    HttpsURLConnection apiCon;
    boolean solved = false;

    public Brick(int y, int x) {
        super(y, x);
        name = "brick";
        description = "a windows 95 monitor shaped brick with one knob in a corner";

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

    private void initConnection() throws IOException {
        apiCon = (HttpsURLConnection) (new URL(TRIVIA_42)).openConnection();
        apiCon.setRequestProperty("x-rapidapi-host", "numbersapi.p.rapidapi.com");
        apiCon.addRequestProperty("x-rapidapi-key", "50e78147b7msh88cadc2841a1911p17987cjsnf0b56c8e7791");
        apiCon.setUseCaches(false);
        apiCon.setDoInput(true);
        apiCon.setDoOutput(true);
        apiCon.setRequestMethod("GET");
    }

    private String parseJson() throws IOException {
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
            return false;
        }
        if (Pattern.matches("press button", ui)) {
            try {
                notifyObservers(parseJson());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        } else if (Pattern.matches("(type |enter )?42", ui)) {
            solved = true;
//            todo eject poptart
            notifyObservers("Something ejected from the back of the brick with a pop.");
            return true;
        }
        return false;
    }
}
