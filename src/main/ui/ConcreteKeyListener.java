package ui;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_ENTER;

public class ConcreteKeyListener implements java.awt.event.KeyListener {

    JTextField txtField;

    public ConcreteKeyListener(JTextField txtField) {
        this.txtField = txtField;
    }

//    todo kill arrow keys and mouse control

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == VK_ENTER) {
            txtField.getText();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
