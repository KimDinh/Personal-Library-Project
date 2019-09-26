package model;

import java.util.ArrayList;

public class Library {
    private ArrayList<Book> availableBooks;
    private ArrayList<Book> loanedBooks;

    // EFFECTS: initialize an empty library
    public Library() {
        availableBooks = new ArrayList<>();
        loanedBooks = new ArrayList<>();
    }

    // EFFECTS: return the number of available books
    public int numOfAvailable() {
        return availableBooks.size();
    }

    // EFFECT: return the number of loaned books
    public int numOfLoaned() {
        return loanedBooks.size();
    }

    // EFFECTS: return the total number of books
    public int numOfBooks() {
        return numOfAvailable() + numOfLoaned();
    }

    // REQUIRES: newBook != null
    // MODIFIES: this
    // EFFECTS: add newBook to availableBooks
    public void addBook(Book newBook) {
        availableBooks.add(newBook);
    }

    // MODIFIES: this
    // EFFECTS: if title matches the title of an available book, loan
    // the book to borrower and return true; otherwise return false
    public boolean loanBook(String title, Person borrower) {
        Book book = findInAvailable(title);
        if (book == null) {
            return false;
        }
        availableBooks.remove(book);
        loanedBooks.add(book);
        book.beLoaned(borrower);
        return true;
    }

    // MODIFIES: this
    // EFFECTS: if title matches the title of a loaned book. move the book
    // to availableBooks and return true; otherwise return false
    public boolean returnBook(String title) {
        Book book = findInLoaned(title);
        if (book == null) {
            return false;
        }
        loanedBooks.remove(book);
        availableBooks.add(book);
        book.beReturned();
        return true;
    }

    // EFFECTS: if title matches the title of an available book,
    // return the book; otherwise return null
    public Book findInAvailable(String title) {
        for (Book book : availableBooks) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // EFFECTS: if title matches the title of a loaned book,
    // return the book; otherwise return null
    public Book findInLoaned(String title) {
        for (Book book : loanedBooks) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }
}
