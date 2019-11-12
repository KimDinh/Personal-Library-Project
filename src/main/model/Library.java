package model;

import exceptions.AlreadyBorrowException;
import exceptions.BookNotAvailableException;
import exceptions.NullBookException;
import exceptions.NullPersonException;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library implements Loadable, Saveable {
    public static final int MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_FRIEND = 7;
    public static final int MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_REGULAR_PERSON = 5;
    public static final int MAXIMUM_LOAN_DAY_RARE_BOOK = 3;
    public static final int OVERDUE_EXTEND_DAY = 2;

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
        NotificationCenter.notifyNewLoan(book, borrower);
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
        NotificationCenter.notifyReturn(book, borrower);
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

    // EFFECTS: return a list of available books
    public List<Book> getAvailableBooks() {
        List<Book> ret = new ArrayList<>();
        for (Book book : availableBooks) {
            ret.add(book);
        }
        return ret;
    }

    // EFFECTS: return a list of loaned books
    public List<Book> getLoanedBooks() {
        List<Book> ret = new ArrayList<>();
        for (Book book : loanedBooks) {
            ret.add(book);
        }
        return ret;
    }

    // REQUIRES: book is overdue
    // MODIFIES: book
    // EFFECTS: extend the loan due date for the book
    public void extendLoan(Book book, Clock clock) {
        book.getLoanStatus().extendDueDate(clock);
    }

    // MODIFIES: this
    // EFFECTS: check every loaned book in this library, if it is overdue, notify borrower
    // and extend due date, otherwise do nothing
    public void checkAllLoan(Clock clock) {
        for (Book book : loanedBooks) {
            if (book.isOverdue(clock)) {
                extendLoan(book, clock);
                NotificationCenter.notifyOverdueAndExtend(book, book.getBorrower());
            }
        }
    }

    @Override
    public void load(Scanner inFile) {
        while (inFile.hasNext()) {
            Book book;
            if (inFile.nextLine().equals(Book.RARE_BOOK_CODE)) {
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
