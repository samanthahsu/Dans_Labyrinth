package model;

public class Character {
    private int status = 0; //health bar of sorts
//    private ArrayList<Item> items = new ArrayList<>();


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //    REQUIRES: dir is N, S, W, or E
//    EFFECT: moves char in specified direction on specified map
    public void moveChar(Map m, char dir){}
}
