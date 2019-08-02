package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConcreteActionListener implements ActionListener {

    private JTextField textField;

    public ConcreteActionListener(JTextField textField) {
        super();
        this.textField = textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textField.getText();
//        out.print(text);
//        userInput = text;
        textField.setText("");
    }
}