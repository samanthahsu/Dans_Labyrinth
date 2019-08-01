package model.MapObjects.features;

public abstract class Gate extends Feature {

    private int yBlock;
    private int xBlock;
    private boolean isOpen;

    public Gate(int y, int x, int yBlock, int xBlock) {
        super(y, x);
        this.yBlock = yBlock;
        this.xBlock = xBlock;
        isOpen = false;
    }

    /*effects: opens the gate setting the blocked index to walkable*/
    public void open() {
        isOpen = true;
        getMap().getTileMatrix().get(yBlock).get(xBlock).setWalkable(true);
    }

    public boolean isOpened() {
        return isOpen;
    }

    /* requires: gate indexes are valid on map
    effects: makes sure gate is blocked if gate isn't open*/
    @Override
    public void doPassiveActions() {
        if (!isOpen) {
            getMap().getTileMatrix().get(yBlock).get(xBlock).setWalkable(false);
        }
    }

}
