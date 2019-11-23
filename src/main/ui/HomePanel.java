package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HomePanel extends JPanel {
    public JButton addBookButton;
    public JButton loanBookButton;
    public JButton returnBookButton;
    public JButton seeAllBookButton;
    public JButton findBookButton;
    public JButton seeRecordButton;

    public HomePanel() {
        //setName(PanelName.HOME_PANEL.getName());
        setLayout(new GridLayout(3,2));
        setBorder(new EmptyBorder(new Insets(200,100,200,100)));
        add(addBookButton = initButton("Add a book", ButtonAction.SHOW_ADD_BOOK_PANEL.getAction()));
        add(loanBookButton = initButton("Loan a book", ButtonAction.SHOW_LOAN_BOOK_PANEL.getAction()));
        add(returnBookButton = initButton("Return a book", ButtonAction.SHOW_RETURN_BOOK_PANEL.getAction()));
        add(seeAllBookButton = initButton("See all the books", ButtonAction.SHOW_PRINT_ALL_BOOKS_PANEL.getAction()));
        add(findBookButton = initButton("See a book", ButtonAction.SHOW_FIND_BOOK_PANEL.getAction()));
        add(seeRecordButton = initButton("See activity record", ButtonAction.SHOW_PRINT_RECORD_PANEL.getAction()));
    }

    public static JButton initButton(String buttonLable, String actionCommand) {
        JButton button = new JButton(buttonLable);
        button.setActionCommand(actionCommand);
        return button;
    }

    public List<JButton> getButtons() {
        List<JButton> buttons = new ArrayList<>();
        buttons.add(addBookButton);
        buttons.add(loanBookButton);
        buttons.add(returnBookButton);
        buttons.add(seeAllBookButton);
        buttons.add(findBookButton);
        buttons.add(seeRecordButton);
        return buttons;
    }
}
