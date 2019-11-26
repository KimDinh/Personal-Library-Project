package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddBookPanel extends JPanel {
    public JTextField titleField;
    public JTextField authorField;
    public JCheckBox rareBookCheckBox;
    public JButton enterButton;
    public JButton backButton;
    public JLabel textDisplay;

    public AddBookPanel() {
        setLayout(new GridLayout(5, 1));
        setBorder(new EmptyBorder(new Insets(150,50,150,50)));
        titleField = new JTextField();
        authorField = new JTextField();
        rareBookCheckBox = new JCheckBox("YES");
        add(getInputRow("Enter book's title:", titleField));
        add(getInputRow("Enter book's author:", authorField));
        add(getInputRow("Choose 'YES' if it is a rare book", rareBookCheckBox));
        JPanel buttonRow = new JPanel(new GridLayout(1, 2));
        buttonRow.add(backButton = HomePanel.initButton("Back", ButtonAction.BACK.getAction()));
        buttonRow.add(enterButton = HomePanel.initButton("Enter", ButtonAction.ADD_BOOK.getAction()));
        add(buttonRow);
        textDisplay = new JLabel();
        add(textDisplay);
    }

    // EFFECTS: return a JPanel containing instruction on the left and inputField on the right
    public static JPanel getInputRow(String instruction, Component inputField) {
        JPanel row = new JPanel(new GridLayout(1, 2));
        row.add(new JLabel(instruction));
        row.add(inputField);
        return row;
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextField getAuthorField() {
        return authorField;
    }

    public JCheckBox getRareBookCheckBox() {
        return rareBookCheckBox;
    }

    public JLabel getTextDisplay() {
        return textDisplay;
    }

    // EFFECTS: return a list of buttons in AddBookPanel
    public List<JButton> getButtons() {
        List<JButton> buttons = new ArrayList<>();
        buttons.add(enterButton);
        buttons.add(backButton);
        return buttons;
    }
}
