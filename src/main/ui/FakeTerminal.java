package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//todo
// https://gist.github.com/ryan-beckett/1599018
/*so we get one box on the bottom for input
* one box on the top for uneditable output*/
public class FakeTerminal extends JFrame implements ActionListener {
    public static final int WIDTH = 700;
    public static final int HEIGHT = 700;
    public static final int TEXT_BAR_HEIGHT = 20;

    private static final String nLine = "\n";

    JTextField txtField;
    JTextArea txtArea;
    JList txtList;

    public FakeTerminal() {
        initializeGraphics();
    }

    public static void main(String[] args) {
        new FakeTerminal();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        setDefaultLookAndFeelDecorated(true);


        txtField = new JTextField(WIDTH);
        txtField.addActionListener(this);
        txtArea = new JTextArea(WIDTH, HEIGHT - TEXT_BAR_HEIGHT);

//        https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
        txtList = new JList();
        txtList.setModel(new DefaultListModel());
        txtList.setLayoutOrientation(JList.VERTICAL);
        txtList.setVisibleRowCount(-1);
        txtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //todo try making not selectable?

        txtField.setBackground(Color.WHITE);
        txtArea.setBackground(Color.YELLOW);

//        JScrollPane scrollPane = new JScrollPane();
//        CommandProcessor processor = CommandProcessor.getInstance
        final String LINE_SEPARATOR = System.lineSeparator();
        Font font = new Font("Ubuntu", Font.BOLD, 14);

        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        JTextField ui = new JTextField();
//        JPanel fakeTerminal = new JPanel();
//        ui.setSize(WIDTH, 25);
//        ui.setBackground(Color.WHITE);
//        add(ui);
        add(txtField, BorderLayout.SOUTH);
        add(txtArea, BorderLayout.NORTH);
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane);

        txtArea.setFont(font);
        txtField.addKeyListener(new ConcreteKeyListener(txtField));
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /*https://www.math.uni-hamburg.de/doc/java/tutorial/uiswing/components/textfield.html
    * requires:
    * modifies:
    * effects:*/
    public void actionPerformed(ActionEvent evt) {
        String text = txtField.getText();
        txtArea.append(text + nLine);
        txtField.setText("");
        txtArea.setCaretPosition(txtArea.getDocument().getLength());
    }
}
