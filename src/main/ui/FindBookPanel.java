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
        setLayout(new GridLayout(4, 1));
        setBorder(new EmptyBorder(new Insets(100,50,100,50)));
        titleField = new JTextField();
        add(AddBookPanel.getInputRow("Enter book's title:", titleField));
        textDisplay = new JLabel();
        textDisplay.setPreferredSize(new Dimension(100, 50));
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        scrollPane.setPreferredSize(new Dimension(100, 200));
        add(scrollPane);
        JPanel lastRow = new JPanel(new GridLayout(1, 2));
        lastRow.add(backButton = HomePanel.initButton("Back", ButtonAction.BACK.getAction()));
        lastRow.add(enterButton = HomePanel.initButton("Enter", ButtonAction.ADD_BOOK.getAction()));
        add(lastRow);
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
