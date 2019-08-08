package model.mapobjects.features;

import java.util.regex.Pattern;

/*need to press stones in right order to open (generated by random each time)*/
public class FourStoneGate extends Gate {

    public static final int START_STATE = 0;
    public static final int OPEN_STATE = 4;
    private int state;

    public FourStoneGate(int y, int x, int yblock, int xblock) {
        super(y, x, yblock, xblock);
        name = "stone-gate";
        description = "A classic.";
        examineDescription = "A solid stone gateway with four large candy corn shaped stone buttons.\n"
                + "Up, down, left, right.";
    }

    /*for tests only*/
    public void setState(int state) {
        this.state = state;
    }

    /*requires:
    * modifies: this
    * effects: if player enters directions in the correct order 4 times, open the gate
    * password is up down left down*/ // public for tests only
    @Override
    public boolean examine(String ui) {
        if (!isOpened() && Pattern.matches("up|down|left|right", ui)) {
            notifyObservers("Dan presses down on the stone in the " + ui + " direction.");
            switchState(ui);
            return true;
        } else {
            return false;
        }
    }

    public void switchState(String ui) {
        switch (state) {
            case 0:
                stateHandler(ui, "left");
                break;
            case 1:
                stateHandler(ui, "up");
                break;
            case 2:
                stateHandler(ui, "right");
                break;
            case 3:
                stateHandlerLast(ui, "down");
                break;
            default:
        }
    }

    private void stateHandlerLast(String ui, String rightInput) {
        stateHandler(ui, rightInput);
        if (state == OPEN_STATE) {
            open();
            notifyObservers("With a resonant click, the stones sink into the "
                    + "gate as it splits and grudgingly scrapes open.");
        }
    }

    private void stateHandler(String ui, String rightInput) {
        if (ui.equals(rightInput)) {
            state++;
        } else {
            state = START_STATE;
        }
    }
}
