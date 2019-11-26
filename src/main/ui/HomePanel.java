/*
https://stackoverflow.com/questions/14821952/changing-panels-using-the-card-layout
https://docs.oracle.com/javase/tutorial/uiswing/examples/components/TextFieldDemoProject/src/components/TextFieldDemo.java
https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
https://docs.oracle.com/javase/tutorial/uiswing/examples/layout/GridBagLayoutDemoProject/src/layout/GridBagLayoutDemo.java
https://stackoverflow.com/questions/2152742/java-swing-multiline-labels
https://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
*/

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
        setLayout(new GridLayout(3,2));
        setBorder(new EmptyBorder(new Insets(200,100,200,100)));
        add(addBookButton = initButton("Add a book", ButtonAction.SHOW_ADD_BOOK_PANEL.getAction()));
        add(loanBookButton = initButton("Loan a book", ButtonAction.SHOW_LOAN_BOOK_PANEL.getAction()));
        add(returnBookButton = initButton("Return a book", ButtonAction.SHOW_RETURN_BOOK_PANEL.getAction()));
        add(seeAllBookButton = initButton("See all the books", ButtonAction.SHOW_PRINT_ALL_BOOKS_PANEL.getAction()));
        add(findBookButton = initButton("See a book", ButtonAction.SHOW_FIND_BOOK_PANEL.getAction()));
        add(seeRecordButton = initButton("See activity record", ButtonAction.SHOW_PRINT_RECORD_PANEL.getAction()));
    }

    // EFFECTS: return a JButton named buttonLable and its command is actionCommand
    public static JButton initButton(String buttonLable, String actionCommand) {
        JButton button = new JButton(buttonLable);
        button.setActionCommand(actionCommand);
        return button;
    }

    // EFFECTS: return a list of buttons in HomePanel
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
