package model.mapobjects.features;

public class Pan extends Feature {


    public static final String NAME = "bones";

    public Pan(int y, int x) {
        super(y, x);
        name = NAME;
        description = "A ribcage, and skull sit halfburied in the dirt "
                + "beside a neatly folded uniform.";
        examineDescription = "The nametag on the orange uniform's breast says 'Pan'";
    }

    @Override
    public void doPassiveActions() {

    }

    @Override
    public boolean examine(String ui) {
        if (ui.equals("uniform")) {
            System.out.println(examineDescription);
        } else if (ui.equals("bones")) {
            System.out.println(description);
        }
        return false;
    }
}
