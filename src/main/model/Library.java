package model;

import exceptions.AlreadyBorrowException;
import exceptions.BookNotAvailableException;
import exceptions.NullBookException;
import exceptions.NullPersonException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library implements Loadable, Saveable {
    private ArrayList<Book> availableBooks;
    private ArrayList<Book> loanedBooks;
    private ArrayList<Person> borrowers;

    // EFFECTS: initialize an empty library
    public Library() {
        availableBooks = new ArrayList<>();
        loanedBooks = new ArrayList<>();
        borrowers = new ArrayList<>();
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

    // MODIFIES: this
    // EFFECTS: add newBook to availableBooks
    public void addBook(Book newBook) throws NullBookException {
        if (newBook == null) {
            throw new NullBookException();
        }
        availableBooks.add(newBook);
    }

    // MODIFIES: this
    // EFFECTS: loan the book to borrower
    public void loanBook(String title, Person borrower) throws NullPersonException,
            BookNotAvailableException, NullBookException, AlreadyBorrowException {
        if (borrower == null) {
            throw new NullPersonException();
        }
        Book book = findInAvailable(title);
        if (book == null) {
            throw new BookNotAvailableException();
        }
        if (borrowers.contains(borrower)) {
            throw new AlreadyBorrowException();
        }
        availableBooks.remove(book);
        loanedBooks.add(book);
        borrowers.add(borrower);
        book.beLoaned(borrower);
    }

    // MODIFIES: this
    // EFFECTS: move the book to availableBooks
    public void returnBook(String title, Person borrower) throws BookNotAvailableException,
            NullPersonException, NullBookException {
        if (borrower == null) {
            throw new NullPersonException();
        }
        Book book = findInLoaned(title);
        if (book == null || !book.getBorrower().equals(borrower)) {
            throw new BookNotAvailableException();
        }
        loanedBooks.remove(book);
        availableBooks.add(book);
        borrowers.remove(borrower);
        book.beReturned(borrower);
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

    // EFFECTS: print all of the available books
    public void printAvailableBooks() {
        for (Book book : availableBooks) {
            System.out.println(book);
        }
    }

    // EFFECTS: print all of the loaned books
    public void printLoanedBooks() {
        for (Book book : loanedBooks) {
            System.out.println(book);
        }
    }

    @Override
    public void load(Scanner inFile) {
        while (inFile.hasNext()) {
            Book book;
            if (inFile.nextLine().equals("1")) {
                book = new RareBook();
            } else {
                book = new RegularBook();
            }
            book.load(inFile);
            if (book.isAvailable()) {
                availableBooks.add(book);
            } else {
                loanedBooks.add(book);
                borrowers.add(book.getBorrower());
            }
        }
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        for (Book book : availableBooks) {
            book.save(outFile);
        }
        for (Book book : loanedBooks) {
            book.save(outFile);
        }
    }
}
