package model;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.util.Calendar;
import java.util.Scanner;

public class LoanStatus implements Loadable, Saveable {

    private Calendar loanDate;
    private Calendar dueDate;

    public LoanStatus() {}

    public LoanStatus(Book book, Person borrower) {
        this(book, borrower, Clock.systemDefaultZone());
    }

    public LoanStatus(Book book, Person borrower, Clock clock) {
        loanDate = ActivityRecord.getDateFromClock(clock);
        dueDate = ActivityRecord.getDateFromClock(clock);
        if (book instanceof RareBook) {
            dueDate.add(Calendar.DAY_OF_MONTH, Library.MAXIMUM_LOAN_DAY_RARE_BOOK - 1);
        } else {
            if (borrower instanceof Friend) {
                dueDate.add(Calendar.DAY_OF_MONTH, Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_FRIEND - 1);
            } else {
                dueDate.add(Calendar.DAY_OF_MONTH, Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_REGULAR_PERSON - 1);
            }
        }
    }

    // EFFECTS: return the loan date of this loan
    public Calendar getLoanDate() {
        return loanDate;
    }

    // EFFECTS: return the due date of this loan
    public Calendar getDueDate() {
        return dueDate;
    }

    // EFFECTS: return true if the loan is overdue; otherwise return false
    public boolean isOverdue(Clock clock) {
        Calendar currentDate = ActivityRecord.getDateFromClock(clock);
        return currentDate.after(dueDate);
    }

    // REQUIRES: the loan is overdue
    // MODIFIES: this
    // EFFECTS: extends the due date to OVERDUE_EXTEND_DAY after the current date
    public void extendDueDate(Clock clock) {
        dueDate = ActivityRecord.getDateFromClock(clock);
        dueDate.add(Calendar.DAY_OF_MONTH, Library.OVERDUE_EXTEND_DAY - 1);
    }

    @Override
    public void load(Scanner inFile) {
        loanDate = ActivityRecord.parseDate(inFile.nextLine());
        dueDate = ActivityRecord.parseDate(inFile.nextLine());
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(ActivityRecord.DATE_FORMAT.format(loanDate.getTime()) + "\n"
                + ActivityRecord.DATE_FORMAT.format(dueDate.getTime()) + "\n");
    }

}
