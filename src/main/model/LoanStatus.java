package model;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParsePosition;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class LoanStatus implements Loadable, Saveable {

    private Calendar loanDate;
    private Calendar dueDate;

    public LoanStatus() {}

    public LoanStatus(Book book, Person borrower) {
        this(book, borrower, Clock.systemDefaultZone());
    }

    public LoanStatus(Book book, Person borrower, Clock clock) {
        loanDate = Activity.getDateFromClock(clock);
        dueDate = Activity.getDateFromClock(clock);
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
        Calendar currentDate = Activity.getDateFromClock(clock);
        return currentDate.after(dueDate);
    }

    // REQUIRES: the loan is overdue
    // MODIFIES: this
    // EFFECTS: extends the due date to OVERDUE_EXTEND_DAY after the current date
    public void extendDueDate(Clock clock) {
        dueDate = Activity.getDateFromClock(clock);
        dueDate.add(Calendar.DAY_OF_MONTH, Library.OVERDUE_EXTEND_DAY - 1);
    }

    @Override
    public void load(Scanner inFile) {
        String loanDateString = inFile.nextLine();
        Date date = Activity.DATE_FORMAT.parse(loanDateString, new ParsePosition(0));
        loanDate = new GregorianCalendar();
        loanDate.setTime(date);
        String dueDateString = inFile.nextLine();
        date = Activity.DATE_FORMAT.parse(dueDateString, new ParsePosition(0));
        dueDate = new GregorianCalendar();
        dueDate.setTime(date);
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(Activity.DATE_FORMAT.format(loanDate.getTime()) + "\n"
                + Activity.DATE_FORMAT.format(dueDate.getTime()) + "\n");
    }

}
