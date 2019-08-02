package model.mapobjects;

import model.Map;

public class SoundManager {

    private Map map;

    public SoundManager(Map map) {
        this.map = map;
    }

    /*modifies: map
effects: sets close sounds in max 4 FLOOR tiles of orthog radius 1
* sets far sounds in max 8 FLOOR tiles of orthog radius 2, and diagonal radius 1*/
    public void setSurroundSound(int currY, int currX, String sourceName, String soundDescription) {
        addSound(currY - 1, currX, sourceName, soundDescription);
        addSound(currY + 1, currX, sourceName, soundDescription);
        addSound(currY, currX - 1, sourceName, soundDescription);
        addSound(currY, currX + 1, sourceName, soundDescription);
    }

    private void addSound(int y, int x, String sourceName, String soundDescription) {
        if (map.isIndexValid(y, x)) {
            map.getTileMatrix().get(y).get(x)
                    .addSound(new Sound(map, y, x, sourceName, soundDescription));
        }
    }

    /*modifies: map
    effects: removes all sounds within scope caused by this creature*/
    public void removeAllSounds(int oldy, int oldx, int currentY, int currentX, String name) {
        removeOneSoundAtPos(currentY - 1, currentX, name);
        removeOneSoundAtPos(currentY + 1, currentX, name);
        removeOneSoundAtPos(currentY, currentX - 1, name);
        removeOneSoundAtPos(currentY, currentX + 1, name);
    }

    /*effects: if tile index is valid, iterate through interactables list in that tile, and remove
     * interactable named SOUND_NAME*/
    private void removeOneSoundAtPos(int y, int x, String name) {
        if (map.isIndexValid(y, x)) {
            map.getTileMatrix().get(y).get(x).removeSound(name);
//            Collection<Sound> soundCollection = getMap().getTileMatrix().get(y).get(x).getTileSounds();
//            for (Sound sound : soundCollection
//                 ) {
//                if(sound.getSourceName().equals(NAME)) {
//                    soundCollection.remove(sound);
//                    return;
//                }
//            }
        }
    }

}
