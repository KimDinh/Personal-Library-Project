package model;

import exceptions.BookNotAvailableException;
import exceptions.EmptyStringException;
import exceptions.NullBookException;
import exceptions.NullPersonException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class Book implements Loadable, Saveable {
    public static final String REGULAR_BOOK_CODE = "Regular book";
    public static final String RARE_BOOK_CODE = "Rare book";
    public static final String AVAILABLE_CODE = "Available";
    public static final String NOT_AVAILABLE_CODE = "Not available";

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
    public void beLoaned(Person borrower) throws NullPersonException, BookNotAvailableException, NullBookException {
        if (borrower == null) {
            throw new NullPersonException();
        }
        if (!available) {
            throw new BookNotAvailableException();
        }
        this.borrower = borrower;
        available = false;
        if (borrower.getBorrowedBook() != this) {
            borrower.borrowBook(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: change the status of this book to be returned
    public void beReturned(Person borrower) throws BookNotAvailableException, NullPersonException, NullBookException {
        if (borrower == null) {
            throw new NullPersonException();
        }
        if (available) {
            throw new BookNotAvailableException();
        }
        this.borrower = null;
        available = true;
        if (borrower.getBorrowedBook() == this) {
            borrower.returnBook(this);
        }
    }

    // EFFECTS: return a String that displays the information of this book
    @Override
    public abstract String toString();

    @Override
    public void load(Scanner inFile) {
        title = inFile.nextLine();
        author = inFile.nextLine();
        available = (inFile.nextLine().equals(AVAILABLE_CODE));
        borrower = null;
        if (!available) {
            if (inFile.nextLine().equals(Person.FRIEND_CODE)) {
                borrower = new Friend();
            } else {
                borrower = new RegularPerson();
            }
            borrower.load(inFile);
            try {
                borrower.borrowBook(this);
            } catch (Exception e) {
                System.out.println("Load did not complete successfully!");
            }
        }
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(title + "\n" + author + "\n" + ((available) ? AVAILABLE_CODE : NOT_AVAILABLE_CODE) + "\n");
        if (!available) {
            borrower.save(outFile);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || (!(o instanceof RegularBook) && !(o instanceof RareBook) && getClass() != o.getClass())) {
            return false;
        }
        Book book = (Book) o;
        return this.title.equals(book.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
