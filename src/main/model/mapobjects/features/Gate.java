package model.mapobjects.features;

public abstract class Gate extends Feature {

    private int yblock;
    private int xblock;
    private boolean isOpen;

    public Gate(int y, int x, int yblock, int xblock) {
        super(y, x);
        this.yblock = yblock;
        this.xblock = xblock;
        isOpen = false;
    }

    /*effects: opens the gate setting the blocked index to walkable*/
    public void open() {
        isOpen = true;
        getMap().getTileMatrix().get(yblock).get(xblock).setWalkable(true);
    }

    public boolean isOpened() {
        return isOpen;
    }

    /* requires: gate indexes are valid on map
    effects: makes sure gate is blocked if gate isn't open*/
    @Override
    public void doPassiveActions() {
        if (!isOpen) {
            getMap().getTileMatrix().get(yblock).get(xblock).setWalkable(false);
        }
    }

}
