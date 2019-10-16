package model;

import exceptions.BookNotAvailableException;
import exceptions.EmptyStringException;
import exceptions.NullPersonException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class Book implements Loadable, Saveable {
    protected String title;
    protected String author;
    protected boolean available;
    protected Person borrower;

    public Book() {}

    // EFFECTS: initialize title and author of this book to the parameter passed in
    public Book(String title, String author) throws EmptyStringException {
        if (title.isEmpty() || author.isEmpty()) {
            throw new EmptyStringException();
        }
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

    // MODIFIES: this
    // EFFECTS: change the status of this book to be loaned to borrower
    public void beLoaned(Person borrower) throws NullPersonException, BookNotAvailableException {
        if (borrower == null) {
            throw new NullPersonException();
        }
        if (!available) {
            throw new BookNotAvailableException();
        }
        this.borrower = borrower;
        available = false;
    }

    // MODIFIES: this
    // EFFECTS: change the status of this book to be returned
    public void beReturned() throws BookNotAvailableException {
        if (available) {
            throw new BookNotAvailableException();
        }
        this.borrower = null;
        available = true;
    }

    // EFFECTS: return a String that displays the information of this book
    @Override
    public abstract String toString();

    @Override
    public void load(Scanner inFile) {
        title = inFile.nextLine();
        author = inFile.nextLine();
        available = (inFile.nextLine().equals("1"));
        if (!available) {
            if (inFile.nextLine().equals("1")) {
                borrower = new Friend();
            } else {
                borrower = new RegularPerson();
            }
            borrower.load(inFile);
        }
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(title + "\n" + author + "\n" + ((available) ? "1" : "0") + "\n");
        if (!available) {
            borrower.save(outFile);
        }
    }
}
