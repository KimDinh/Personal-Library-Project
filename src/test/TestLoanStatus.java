import exceptions.EmptyStringException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;


public class TestLoanStatus {
    private Book regularBookA;
    private Book rareBookB;
    private Person regularPerson;
    private Person friend;
    private LoanStatus regularBookRegularPerson;
    private LoanStatus regularBookFriend;
    private LoanStatus rareBookRegularPerson;
    private LoanStatus rareBookFriend;
    private Clock clock;

    @BeforeEach
    void runBefore() throws EmptyStringException {
        regularBookA = new RegularBook("Book A", "Author A");
        rareBookB = new RareBook("Book B", "Author B");
        regularPerson = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
        friend = new Friend("Goku", "987654321", "aaaaaa@gmail.com");
        regularBookRegularPerson = new LoanStatus(regularBookA, regularPerson);
        regularBookFriend = new LoanStatus(regularBookA, friend);
        rareBookRegularPerson = new LoanStatus(rareBookB, regularPerson);
        rareBookFriend = new LoanStatus(rareBookB, friend);
        clock = Clock.systemDefaultZone();
    }

    @Test
    void testConstructor() {
        Calendar loanDate = regularBookRegularPerson.getLoanDate();
        Calendar dueDate = regularBookRegularPerson.getDueDate();
        loanDate.add(Calendar.DAY_OF_MONTH, Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_REGULAR_PERSON - 1);
        assertTrue(loanDate.equals(dueDate));
        loanDate = regularBookFriend.getLoanDate();
        dueDate = regularBookFriend.getDueDate();
        loanDate.add(Calendar.DAY_OF_MONTH, Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_FRIEND - 1);
        assertTrue(loanDate.equals(dueDate));
        loanDate = rareBookRegularPerson.getLoanDate();
        dueDate = rareBookRegularPerson.getDueDate();
        loanDate.add(Calendar.DAY_OF_MONTH, Library.MAXIMUM_LOAN_DAY_RARE_BOOK - 1);
        assertTrue(loanDate.equals(dueDate));
        loanDate = rareBookFriend.getLoanDate();
        dueDate = rareBookFriend.getDueDate();
        loanDate.add(Calendar.DAY_OF_MONTH, Library.MAXIMUM_LOAN_DAY_RARE_BOOK - 1);
        assertTrue(loanDate.equals(dueDate));
    }

    @Test
    void testIsOverdueRegularBookRegularPerson() {
        clock = Clock.offset(clock, Duration.ofDays(Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_REGULAR_PERSON-1));
        assertFalse(regularBookRegularPerson.isOverdue(clock));
        clock = Clock.offset(clock, Duration.ofDays(1));
        assertTrue(regularBookRegularPerson.isOverdue(clock));
    }

    @Test
    void testIsOverdueRegularBookFriend() {
        clock = Clock.offset(clock, Duration.ofDays(Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_FRIEND-1));
        assertFalse(regularBookFriend.isOverdue(clock));
        clock = Clock.offset(clock, Duration.ofDays(1));
        assertTrue(regularBookFriend.isOverdue(clock));
    }

    @Test
    void testIsOverdueRareBookRegularPerson() {
        clock = Clock.offset(clock, Duration.ofDays(Library.MAXIMUM_LOAN_DAY_RARE_BOOK-1));
        assertFalse(rareBookRegularPerson.isOverdue(clock));
        clock = Clock.offset(clock, Duration.ofDays(1));
        assertTrue(rareBookRegularPerson.isOverdue(clock));
    }

    @Test
    void testIsOverdueRareBookFriend() {
        clock = Clock.offset(clock, Duration.ofDays(Library.MAXIMUM_LOAN_DAY_RARE_BOOK-1));
        assertFalse(rareBookFriend.isOverdue(clock));
        clock = Clock.offset(clock, Duration.ofDays(1));
        assertTrue(rareBookFriend.isOverdue(clock));
    }

    @Test
    void testExtendDueDate() {
        clock = Clock.offset(clock, Duration.ofDays(Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_REGULAR_PERSON));
        regularBookRegularPerson.extendDueDate(clock);
        Calendar expected = ActivityRecord.getDateFromClock(clock);
        expected.add(Calendar.DAY_OF_MONTH, Library.OVERDUE_EXTEND_DAY-1);
        assertEquals(expected, regularBookRegularPerson.getDueDate());
    }
}
