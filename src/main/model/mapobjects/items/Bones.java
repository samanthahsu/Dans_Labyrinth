package model.mapobjects.items;

public class Bones extends Item {

    public static final String NAME = "bones";
    private int num = 1;

    public Bones() {
        name = NAME;
    }

    public void addBone() {
        num++;
    }

    @Override
    public String getDescription() {
        return "A pile of " + num + " bones from a dear friend.";
    }

    @Override
    /*cant be used on anything*/
    public boolean use(String target) {
        return false;
    }

}
