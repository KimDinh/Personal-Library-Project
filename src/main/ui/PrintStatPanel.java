package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PrintStatPanel extends JPanel {
    public JButton backButton;
    public JLabel textDisplay;

    public PrintStatPanel() {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(new Insets(150, 50, 150, 50)));
        textDisplay = new JLabel();
        JScrollPane scrollPane = new JScrollPane(textDisplay);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = c.weighty = 1;
        c.ipady = 100;
        c.ipadx = 150;
        add(scrollPane, c);
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 50;
        c.ipady = 20;
        c.gridy = 1;
        c.weightx = c.weighty = 0;
        add(backButton = HomePanel.initButton("Back", ButtonAction.BACK.getAction()), c);
    }

    public JLabel getTextDisplay() {
        return textDisplay;
    }

    // EFFECTS: return a list of buttons in PrintStatPanel
    public List<JButton> getButtons() {
        List<JButton> buttons = new ArrayList<>();
        buttons.add(backButton);
        return buttons;
    }

}
