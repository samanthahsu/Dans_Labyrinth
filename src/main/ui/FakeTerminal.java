package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//todo
// https://gist.github.com/ryan-beckett/1599018
/*so we get one box on the bottom for input
* one box on the top for uneditable output*/
public class FakeTerminal implements ActionListener, GameObserver {
    private static final int WIDTH = 700;
    private static final int HEIGHT = 700;
    private static final int TEXT_BAR_HEIGHT = 20;

    private static final String nLine = "\n";

    private JFrame jFrame;
    private JPanel mainPanel;
    private JTextField txtField;
//    private JTextArea txtArea;
    private JList txtList;
    private DefaultListModel listModel;
    public Printer out;
    public String userInput;

    public FakeTerminal() {
        initializeGraphics();
        out = new Printer(listModel);
        userInput = "";
    }

    public Printer getOut() {
        return out;
    }

    public String getUserInput() {
        return userInput;
    }

    public static void main(String[] args) {
        new FakeTerminal();
    }

    // MODIFIES: this
    // EFFECTS:  draws the JFrame window where this DrawingEditor will operate, and populates the tools to be used
    //           to manipulate this drawing
    private void initializeGraphics() {
        jFrame = new JFrame();
        mainPanel = new JPanel();
        JFrame.setDefaultLookAndFeelDecorated(true);

        txtField = new JTextField(WIDTH);
        txtField.addActionListener(this);
//        txtArea = new JTextArea(WIDTH, HEIGHT - TEXT_BAR_HEIGHT);

//        https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
        listModel = new DefaultListModel();
        txtList = new JList(listModel);
        txtList.setLayoutOrientation(JList.VERTICAL);
        txtList.setVisibleRowCount(-1);
        txtList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //todo try making not selectable?

        txtField.setBackground(Color.WHITE);
//        txtArea.setBackground(Color.YELLOW);

//        JScrollPane scrollPane = new JScrollPane();
//        CommandProcessor processor = CommandProcessor.getInstance
        final String LINE_SEPARATOR = System.lineSeparator();
        Font font = new Font("Ubuntu", Font.BOLD, 14);

        jFrame.setLayout(new BorderLayout());
        jFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
//        JTextField ui = new JTextField();
//        JPanel fakeTerminal = new JPanel();
//        ui.setSize(WIDTH, 25);
//        ui.setBackground(Color.WHITE);
//        add(ui);
        jFrame.add(txtField, BorderLayout.SOUTH);
        jFrame.add(txtList, BorderLayout.NORTH);
//        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jFrame.add(scrollPane);

//        txtArea.setFont(font);
        txtField.addKeyListener(new ConcreteKeyListener(txtField));
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    /*https://www.math.uni-hamburg.de/doc/java/tutorial/uiswing/components/textfield.html
    * requires:
    * modifies: this
    * effects: adds text in text field to the display, and clears text field
    * removes the first entry if there are too many entries
    * sets entered text as user input*/
    @Override
    public void actionPerformed(ActionEvent evt) {
        String text = txtField.getText();
        out.print(text);
        userInput = text;
        txtField.setText("");
//        txtArea.setCaretPosition(txtArea.getDocument().getLength());
    }

    @Override
    public void update(String message) {
        out.print(message);
    }
}
