package model.Interactables;

/*sounds made by surrounding interactables, varying in intensity, and fades or strengthens based on distance*/
public class Sound extends Interactable {

    public Sound(int y, int x, String name, String description) {
        super(y, x);
        typeId = TYPE_SOUND;
        this.name = name;
        this.description = description;
    }

    /*effects: prints sound description*/
    @Override
    public boolean interact(String target) {
        System.out.println(description);
        return true;
    }

    @Override
    public boolean examine(String ui) {
        return true; // todo nothing apparently???
    }
}
