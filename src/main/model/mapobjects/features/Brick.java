package model.mapobjects.features;

import model.mapobjects.items.Poptart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Brick extends Feature {

    private static final String Q1 = "The answer to the Ultimate Question of Life, the Universe, and Everything.";
    private static final String Q2 = "The number of laws of cricket.";
    private static final String Q3 = "The sum of the codes of the letters in the words \"BIG BANG\" using the encoding A=1, B=2, C=3, etc.";
    private static final String Q4 = "The number of US gallons in a barrel of oil.";
    private static final String Q5 = "The number of museums in Amsterdam\n(DYK: Netherlands has the highest concentration of museums in the world).";
    private static final String Q6 = "The number of spots (or pips, circular patches or pits) on a pair of standard six-sided dice.";
    private static final String TRIVIA_42 = "https://numbersapi.p.rapidapi.com/42/trivia?fragment=true&notfound=floor&json=false.";
    private List<String> qList;
    private boolean solved = false;

    public Brick(int y, int x) {
        super(y, x);
        name = "brick";
        description = "a brick shaped monitor sits on the floor";
        examineDescription = "a number keypad is on the right side, with a large button above it"; //todo fix text
        qList = new ArrayList<>(
                Arrays.asList(Q1, Q2, Q3, Q4, Q5, Q6));
    }

    /*todo test only*/
    public boolean isSolved() {
        return solved;
    }

    @Override
    /*requires:
    * modifies:
    * effects: enters instance where it accepts turning the knob to produce random trivia relating to number 42*/
    public boolean examine(String ui) {
        if (solved) {
            notifyObservers("The poor brick has no more treats to offer.");
            return true;
        } else if (Pattern.matches("press (large )?button", ui)) {
            notifyObservers("The screen displays:\n" + getRandomQuestion());
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

    private String getRandomQuestion() {
        Random random = new Random();
        int i = random.nextInt(qList.size());
        return qList.get(i);
    }

    private void ejectPoptart() {
        map.addExaminable(new Poptart(), yc, xc);
        notifyObservers("The front of the brick swings open revealing a poptart inside.");
    }
}
