package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnBookPanel extends JPanel {
    private JTextField titleField;
    private JTextField nameField;
    private JTextField phoneNumberField;
    private JTextField emailField;
    private JCheckBox friendCheckBox;
    private JButton enterButton;
    private JButton backButton;

    public ReturnBookPanel() {
        //setName(PanelName.RETURN_BOOK_PANEL.getName());
        setLayout(new GridLayout(6, 1));
        setBorder(new EmptyBorder(new Insets(150, 50, 150, 50)));
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
        JPanel lastRow = new JPanel(new GridLayout(1, 2));
        lastRow.add(backButton = HomePanel.initButton("Back", ButtonAction.BACK.getAction()));
        lastRow.add(enterButton = HomePanel.initButton("Enter", ButtonAction.RETURN_BOOK.getAction()));
        add(lastRow);
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
}