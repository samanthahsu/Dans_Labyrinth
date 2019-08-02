package model.mapobjects;

import model.Map;
import ui.PrintObservable;

import java.io.Serializable;

public abstract class Locatable extends PrintObservable implements Serializable {
    protected Map map;
    protected int yc;
    protected int xc;

    public Locatable(int y, int xc) {
        this.yc = y;
        this.xc = xc;
    }

    public Locatable(Map map, int yc, int xc) {
        this.map = map;
        this.yc = yc;
        this.xc = xc;
    }

    public int getYc() {
        return yc;
    }

    public void setYc(int yc) {
        this.yc = yc;
    }

    public int getXc() {
        return xc;
    }

    public void setXc(int xc) {
        this.xc = xc;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
}
