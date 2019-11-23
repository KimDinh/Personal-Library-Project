package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FindBookPanel extends JPanel {
    public JTextField titleField;
    public JButton enterButton;
    public JButton backButton;
    public JLabel textDisplay;

    public FindBookPanel() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(new Insets(100,50,100,50)));
        titleField = new JTextField();
        textDisplay = new JLabel();
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        JPanel buttonRow = new JPanel(new GridLayout(1, 2));
        buttonRow.add(backButton = HomePanel.initButton("Back", ButtonAction.BACK.getAction()));
        buttonRow.add(enterButton = HomePanel.initButton("Enter", ButtonAction.FIND_BOOK.getAction()));
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 20;
        c.ipadx = 200;
        add(AddBookPanel.getInputRow("Enter book's title:", titleField), c);
        c.gridy = 1;
        add(buttonRow, c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = c.weighty = 1;
        c.gridy = 2;
        add(scrollPane, c);
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JLabel getTextDisplay() {
        return textDisplay;
    }

    public List<JButton> getButtons() {
        List<JButton> buttons = new ArrayList<>();
        buttons.add(enterButton);
        buttons.add(backButton);
        return buttons;
    }
}
