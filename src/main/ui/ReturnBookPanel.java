package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnBookPanel extends JPanel {
    public JTextField titleField;
    public JTextField nameField;
    public JTextField phoneNumberField;
    public JTextField emailField;
    public JCheckBox friendCheckBox;
    public JButton enterButton;
    public JButton backButton;
    public JLabel textDisplay;


    public ReturnBookPanel() {
        //setName(PanelName.RETURN_BOOK_PANEL.getName());
        setLayout(new GridLayout(7, 1));
        setBorder(new EmptyBorder(new Insets(130, 50, 130, 50)));
        titleField = new JTextField();
        nameField = new JTextField();
        phoneNumberField = new JTextField();
        emailField = new JTextField();
        friendCheckBox = new JCheckBox("YES");
        add(AddBookPanel.getInputRow("Enter book's title:", titleField));
        add(AddBookPanel.getInputRow("Enter borrower's name:", nameField));
        add(AddBookPanel.getInputRow("Enter borrower's phone number:", phoneNumberField));
        add(AddBookPanel.getInputRow("Enter borrower's email:", emailField));
        add(AddBookPanel.getInputRow("Choose 'YES' if borrower is a friend", friendCheckBox));
        JPanel buttonRow = new JPanel(new GridLayout(1, 2));
        buttonRow.add(backButton = HomePanel.initButton("Back", ButtonAction.BACK.getAction()));
        buttonRow.add(enterButton = HomePanel.initButton("Enter", ButtonAction.RETURN_BOOK.getAction()));
        add(buttonRow);
        textDisplay = new JLabel();
        add(textDisplay);
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public JCheckBox getFriendCheckBox() {
        return friendCheckBox;
    }

    public List<JButton> getButtons() {
        List<JButton> buttons = new ArrayList<>();
        buttons.add(enterButton);
        buttons.add(backButton);
        return buttons;
    }

    public JLabel getTextDisplay() {
        return textDisplay;
    }
}
