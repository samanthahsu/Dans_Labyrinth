package model.mapobjects.features;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Brick extends Feature {

    static final String TRIVIA_42 = "https://numbersapi.p.rapidapi.com/42/trivia?fragment=true&notfound=floor&json=true";
    HttpsURLConnection apiCon;

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

    private void initConnection() throws IOException {
        apiCon = (HttpsURLConnection) (new URL(TRIVIA_42)).openConnection();
        apiCon.setRequestProperty("x-rapidapi-host", "numbersapi.p.rapidapi.com");
        apiCon.addRequestProperty("x-rapidapi-key", "50e78147b7msh88cadc2841a1911p17987cjsnf0b56c8e7791");
        apiCon.setUseCaches(false);
        apiCon.setDoInput(true);
        apiCon.setDoOutput(true);
        apiCon.setRequestMethod("GET");
    }

    private static void tryGoat() throws IOException {
        BufferedReader br = null;

        try {
            String placeGoat = "http://placegoat.com/200";
            String theURL = "https://www.ugrad.cs.ubc.ca/~cs210/2018w1/welcomemsg.html"; //this can point to any TRIVIA_42
            URL url = new URL(placeGoat);
            br = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;

            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }

            System.out.println(sb);
        } finally {

            if (br != null) {
                br.close();
            }
        }
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
                }
                else if (w.equals("text")) {
                    saveNext = true;
                }
                System.out.println(w);
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
        try {
            notifyObservers(parseJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
