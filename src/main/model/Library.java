package model;

import java.util.ArrayList;

public class Library {
    private ArrayList<Book> availableBooks;
    private ArrayList<Book> loanedBooks;

    // EFFECTS: initialize an empty library
    public Library() {

    }

    // EFFECTS: return the number of available books
    public int numOfAvailable() {
        return 0;
    }

    // EFFECT: return the number of loaned books
    public int numOfLoaned() {
        return 0;
    }

    // EFFECTS: return the total number of books
    public int numOfBooks() {
        return 0;
    }
}
