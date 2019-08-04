package model.mapobjects.items;

/*can be fed to ennui*/
public class Poptart extends Item {


    @Override
    /*effects: can raise sanity by one,
    be fed to ennui to cause sugar high
    adds nothing of value???*/
    public boolean use(String target) {
        return false;
    }
}
