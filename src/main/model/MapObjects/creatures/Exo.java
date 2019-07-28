//package main.model.Interactables.creatures;
//
//import main.model.MapObjects.Avatar;
//import main.model.Map;
//
///*tries to run away form you, holds rusted key*/
//public class Exo extends Creature {
//
//    //    EFFECTS: set starting coordinates
//    public Exo(int y, int x) {
//        super(y, x);
//        description = "a fuzzy black orange sized creature stares back at "
//                + "you with a deploring gaze";
//        name = "Exo";
//        health = 1;
//    }
//
//    @Override
//    void move(Map map) {
////        todo stub
//    }
//
////    EFFECTS:
//    @Override
//    public boolean interact(Map map) {
//        Avatar ava = map.getAva();
////        todo stub
//        if(ava.getCurrY() == currentY && ava.getCurrX() == currentX) {
//            attack(map);
//        }
//        return false;
//    }
//
////    REQUIRES: only called when ava is on same tile
////    EFFECTS: explodes causing those on the same tile by 1,
////      eliminates itself from map
//    @Override
//    public void attack(Map map) {
///*
//        int futureStat = map.getAva().getStatus() - 1;
//        if (futureStat >= 0) {
//            map.getAva().setStatus(futureStat);
//        }
//        map.getCurrInteractables().get(currentY).set(currentX, new AbsolutelyNothing());
//        System.out.println("KABOOM!");
//*/
//    }
//
////    EFFECTS: prints
//    @Override
//    public void speak() {
//        System.out.println("a sad ticking noise fills the hall");
//    }
//
//    @Override
//    public void doPassiveActions() {
//
//    }
//}
