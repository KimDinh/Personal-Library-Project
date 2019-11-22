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

    public AddBookPanel() {
        setLayout(new GridLayout(4, 1));
        setBorder(new EmptyBorder(new Insets(200,50,200,50)));
        titleField = new JTextField();
        authorField = new JTextField();
        rareBookCheckBox = new JCheckBox("YES");
        add(getInputRow("Enter book's title:", titleField));
        add(getInputRow("Enter book's author:", authorField));
        add(getInputRow("Choose 'YES' if it is a rare book", rareBookCheckBox));
        JPanel lastRow = new JPanel(new GridLayout(1, 2));
        lastRow.add(backButton = HomePanel.initButton("Back", ButtonAction.BACK.getAction()));
        lastRow.add(enterButton = HomePanel.initButton("Enter", ButtonAction.ADD_BOOK.getAction()));
        add(lastRow);
    }

    public static JPanel getInputRow(String lable, Component inputField) {
        JPanel row = new JPanel(new GridLayout(1, 2));
        row.add(new JLabel(lable));
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

    public List<JButton> getButtons() {
        List<JButton> buttons = new ArrayList<>();
        buttons.add(enterButton);
        buttons.add(backButton);
        return buttons;
    }
}
