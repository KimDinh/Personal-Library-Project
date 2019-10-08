package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Library implements Loadable, Saveable {
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

    // REQUIRES: book is in availableBooks
    // MODIFIES: this
    // EFFECTS: loan the book to borrower
    public void loanBook(Book book, Person borrower) {
        availableBooks.remove(book);
        loanedBooks.add(book);
        book.beLoaned(borrower);
    }

    // REQUIRES: book is in loanedBooks
    // MODIFIES: this
    // EFFECTS: move the book to availableBooks
    public void returnBook(Book book) {
        loanedBooks.remove(book);
        availableBooks.add(book);
        book.beReturned();
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
