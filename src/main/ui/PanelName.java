package ui;

public enum PanelName {
    HOME_PANEL("Home panel"),
    ADD_BOOK_PANEL("Add book panel"),
    LOAN_BOOK_PANEL("Loan book panel"),
    RETURN_BOOK_PANEL("Return book panel"),
    FIND_BOOK_PANEL("Find book panel");

    private final String name;

    PanelName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
