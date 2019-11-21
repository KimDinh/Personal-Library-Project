package ui;

public enum PanelName {
    HOME_PANEL("Home panel"),
    ADD_BOOK_PANEL("Add book panel");

    private final String name;

    PanelName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
