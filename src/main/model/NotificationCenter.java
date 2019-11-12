package model;

public class NotificationCenter {

    // REQUIRES: book is loaned to borrower
    // EFFECTS: create a new Activity recording the loan, notify the borrower and return the activity
    public static Activity notifyNewLoan(Book book, Person borrower) {
        String content = borrower.getName() + " has borrowed " + book.getTitle();
        return notify(borrower, content);
    }

    // REQUIRES: book was previously loaned and borrower has returned the book
    // EFFECTS: create a new Activity recording the return, notify the borrower and return the activity
    public static Activity notifyReturn(Book book, Person borrower) {
        String content = borrower.getName() + " has returned " + book.getTitle();
        return notify(borrower, content);
    }

    // REQUIRES: book is loaned, was overdue and the due date has been extended
    // EFFECTS: create a new Activity recording the overdue and extension, notify the borrower and return the activity
    public static Activity notifyOverdueAndExtend(Book book, Person borrower) {
        String content = borrower.getName() + " has not returned " + book.getTitle() + ". The due date is extended to "
                + Activity.DATE_FORMAT.format(book.getLoanStatus().getDueDate().getTime());
        return notify(borrower, content);
    }

    public static Activity notify(Person borrower, String content) {
        Activity activity = new Activity(content);
        borrower.updateActivity(activity);
        return activity;
    }
}
