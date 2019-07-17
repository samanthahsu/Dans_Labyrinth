package model;

/**An item that can be picked off the floor, then used for various things**/
public interface Item {

    String getName();

    String getDescription();

    void useItem(Map map);

}
