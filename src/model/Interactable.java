package model;

abstract class Interactable {

    String name;
    String description;
    int status;
    int startY;
    int startX;

    Interactable(int y, int x){
        startY = y;
        startX = x;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }
}
