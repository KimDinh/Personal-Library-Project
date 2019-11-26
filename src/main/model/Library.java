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
    private NotificationCenter notiCenter;
    private ActivityRecord activityRecord;

    // EFFECTS: initialize an empty library
    public Library() {
        availableBooks = new ArrayList<>();
        loanedBooks = new ArrayList<>();
        borrowers = new ArrayList<>();
        notiCenter = new NotificationCenter(this);
        activityRecord = new ActivityRecord();
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
    // EFFECTS: if newBook is null, throw NullBookException,
    // otherwise add newBook to availableBooks and record it being added at the time specified by clock
    public void addBook(Book newBook, Clock clock) throws NullBookException {
        if (newBook == null) {
            throw new NullBookException();
        }
        availableBooks.add(newBook);
        notiCenter.notifyNewBook(newBook, clock);
    }

    // MODIFIES: this, borrower
    // EFFECTS: if borrower is null, throw NullPersonException,
    // if title is not of a book in availableBooks, throw BookNotAvailableException,
    // if borrower already borrows a book, throw AlreadyBorrowException
    // otherwise, loan the book to borrower and record the loan at the time specified by clock
    public void loanBook(String title, Person borrower, Clock clock) throws NullPersonException,
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
        notiCenter.notifyNewLoan(book, borrower, clock);
    }

    // MODIFIES: this, borrower
    // EFFECTS: if borrower is null, throw NullPersonException,
    // if title is not of a book in loanedBooks, throw BookNotAvailableException,
    // if book is not loaned to borrower, throw BookNotAvailableException,
    // otherwise let the borrower return the book and record the return at the time specified by clock
    public void returnBook(String title, Person borrower, Clock clock) throws BookNotAvailableException,
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
        notiCenter.notifyReturn(book, borrower, clock);
    }

    // EFFECTS: if title matches the title of an available book, return the book;
    // otherwise return null
    public Book findInAvailable(String title) {
        for (Book book : availableBooks) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // EFFECTS: if title matches the title of a loaned book, return the book;
    // otherwise return null
    public Book findInLoaned(String title) {
        for (Book book : loanedBooks) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }
        return null;
    }

    // EFFECTS: return a new list of available books
    public List<Book> getAvailableBooks() {
        List<Book> ret = new ArrayList<>();
        for (Book book : availableBooks) {
            ret.add(book);
        }
        return ret;
    }

    // EFFECTS: return a new list of loaned books
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
    // EFFECTS: check every loaned book in this library, if it is overdue with current date specified by clock,
    // notify borrower and extend due date, otherwise do nothing
    public void checkAllLoan(Clock clock) {
        for (Book book : loanedBooks) {
            if (book.isOverdue(clock)) {
                extendLoan(book, clock);
                notiCenter.notifyOverdueAndExtend(book, book.getBorrower(), clock);
            }
        }
    }

    // EFFECTS: return the activity record
    public ActivityRecord getActivityRecord() {
        return activityRecord;
    }

    // MODIFIES: this
    // EFFECTS: add the content to activityRecord using the date from clock
    public void updateActivity(String content, Clock clock) {
        activityRecord.addActivity(content, clock);
    }

    // MODIFIES: this
    // EFFECTS: read library's data from file
    @Override
    public void load(Scanner inFile) {
        int num = Integer.parseInt(inFile.nextLine());
        for (int i = 0; i < num; i++) {
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
        activityRecord.load(inFile);
    }

    // EFFECTS: save library's data to file
    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(numOfBooks() + "\n");
        for (Book book : availableBooks) {
            book.save(outFile);
        }
        for (Book book : loanedBooks) {
            book.save(outFile);
        }
        activityRecord.save(outFile);
    }
}
