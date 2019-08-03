package ui;

import javax.swing.*;

/**/
public class Printer implements PrintObserver {

    private DefaultListModel listModel;

    Printer(DefaultListModel listModel) {
        this.listModel = listModel;
    }

    public void print(String str) {
        listModel.addElement(str);
        if (listModel.getSize() > 20) {
            listModel.removeElementAt(0);
        }
    }

    @Override
    public void update(String message) {
        print(message);
    }
}
