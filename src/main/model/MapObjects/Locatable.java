package model.MapObjects;

import model.Map;

import java.io.Serializable;

public abstract class Locatable implements Serializable {
    private Map map;
    private int y;
    private int x;

    public Locatable(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public Locatable(Map map, int y, int x) {
        this.map = map;
        this.y = y;
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
