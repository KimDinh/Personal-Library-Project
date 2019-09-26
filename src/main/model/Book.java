package model;

public class Book {
    private String title;
    private String author;
    private boolean available;
    private Person borrower;

    public Book() {

    }

    // REQUIRES: title and author must be non-empty strings
    // EFFECTS: initialize title and author of this book to the parameter passed in
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
        this.borrower = null;
    }

    // EFFECTS: return the title of this book
    public String getTitle() {
        return title;
    }

    // EFFECTS: return the author of this book
    public String getAuthor() {
        return author;
    }

    // EFFECTS: return true if this book is available in the library,
    // otherwise return false
    public boolean isAvailable() {
        return available;
    }

    // EFFECTS: return the person borrowing this book
    public Person getBorrower() {
        return borrower;
    }

    // REQUIRES: (borrower != null) and (available == true)
    // MODIFIES: this
    // EFFECTS: change the status of this book to be loaned to borrower
    public void beLoaned(Person borrower) {
        this.borrower = borrower;
        available = false;
    }

    // REQUIRES: available == false
    // MODIFIES: this
    // EFFECTS: change the status of this book to be returned
    public void beReturned() {
        this.borrower = null;
        available = true;
    }

    // EFFECTS: return a String that displays the information of this book
    @Override
    public String toString() {
        return ("Title: " + title
                + "\nAuthor: " + author
                + "\nThis book is " + ((available) ? "available.\n" : "loaned.\n"));
    }
}
