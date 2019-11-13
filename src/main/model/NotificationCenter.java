package model;

import java.time.Clock;

public class NotificationCenter {
    private final Library library;

    public NotificationCenter(Library library) {
        this.library = library;
    }

    // REQUIRES: book is in library
    // EFFECTS: notify the library about the new book
    public void notifyNewBook(Book book, Clock clock) {
        String content = book.getTitle() + " has been added to the library";
        library.updateActivity(content, clock);
    }

    // REQUIRES: book is loaned to borrower
    // EFFECTS: notify the borrower and the library about the loan
    public void notifyNewLoan(Book book, Person borrower, Clock clock) {
        String content = borrower.getName() + " has borrowed " + book.getTitle();
        notify(borrower, content, clock);
    }

    // REQUIRES: book was previously loaned and borrower has returned the book
    // EFFECTS: notify the borrower and the library about the return
    public void notifyReturn(Book book, Person borrower, Clock clock) {
        String content = borrower.getName() + " has returned " + book.getTitle();
        notify(borrower, content, clock);
    }

    // REQUIRES: book is loaned, was overdue and the due date has been extended
    // EFFECTS: notify the borrower and the library about the overdue and extension
    public void notifyOverdueAndExtend(Book book, Person borrower, Clock clock) {
        String content = borrower.getName() + " has not returned " + book.getTitle() + ". The due date is extended to "
                + ActivityRecord.DATE_FORMAT.format(book.getLoanStatus().getDueDate().getTime());
        notify(borrower, content, clock);
    }

    // EFFECTS: notify the borrower and the library about the content
    public void notify(Person borrower, String content, Clock clock) {
        borrower.updateActivity(content, clock);
        library.updateActivity(content, clock);
    }
}
