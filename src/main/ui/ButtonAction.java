package ui;

public enum ButtonAction {
    SHOW_ADD_BOOK_PANEL("Show panel to add a book"),
    SHOW_LOAN_BOOK_PANEL("Show panel to loan a book"),
    SHOW_RETURN_BOOK_PANEL("Show panel to return a book"),
    SHOW_FIND_BOOK_PANEL("Show panel to find a book"),
    SHOW_PRINT_ALL_BOOKS_PANEL("Show panel to print all books"),
    SHOW_PRINT_RECORD_PANEL("Show panel to print record"),
    ADD_BOOK("Add a book"),
    LOAN_BOOK("Loan a book"),
    RETURN_BOOK("Return a book"),
    FIND_BOOK("Find a book"),
    BACK("Back to home panel");

    private final String action;

    ButtonAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
