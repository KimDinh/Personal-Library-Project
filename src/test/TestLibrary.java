import exceptions.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Clock;
import java.time.Duration;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestLibrary {
    private Library library;
    private Book regularBookA;
    private Book rareBookB;
    private Person regularPerson;
    private Person friend;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() throws EmptyStringException {
        library = new Library();
        regularBookA = new RegularBook("Book A", "Author A");
        rareBookB = new RareBook("Book B", "Author B");
        regularPerson = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
        friend = new Friend("Goku", "987654321", "aaaaaa@gmail.com");
    }

    @Test
    void testAddBookNothingThrown() {
        assertEquals(0, library.numOfAvailable());
        assertEquals(0, library.numOfBooks());
        try {
            library.addBook(regularBookA, Clock.systemDefaultZone());
        } catch (NullBookException e) {
            fail();
        }
        assertEquals(1, library.numOfAvailable());
        assertEquals(1, library.numOfBooks());
        assertEquals(regularBookA, library.findInAvailable("Book A"));
        try {
            library.addBook(rareBookB, Clock.systemDefaultZone());
        } catch (NullBookException e) {
            fail();
        }
        assertEquals(2, library.numOfAvailable());
        assertEquals(2, library.numOfBooks());
        assertEquals(rareBookB, library.findInAvailable("Book B"));
    }

    @Test
    void testAddBookExpectNullBookException() {
        try {
            library.addBook(null, Clock.systemDefaultZone());
            fail();
        } catch (NullBookException e) {}
    }

    @Test
    void testLoanBookNothingThrown() {
        try {
            library.addBook(regularBookA, Clock.systemDefaultZone());
            assertEquals(1, library.numOfBooks());
            assertEquals(1, library.numOfAvailable());
            assertEquals(0, library.numOfLoaned());
            library.loanBook("Book A", regularPerson, Clock.systemDefaultZone());
        } catch (Exception e) {
            fail();
        }
        assertEquals(0, library.numOfAvailable());
        assertEquals(1, library.numOfLoaned());
        assertEquals(regularBookA, library.findInLoaned("Book A"));
    }

    @Test
    void testLoanBookExpectBookNotAvailableException() {
        try {
            library.loanBook("Book A", regularPerson, Clock.systemDefaultZone());
            fail();
        } catch (BookNotAvailableException e) {
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void testLoanBookExpectNullPersonException() {
        try {
            library.addBook(regularBookA, Clock.systemDefaultZone());
            library.loanBook("Book A", null, Clock.systemDefaultZone());
            fail();
        } catch (NullPersonException e) {
        } catch (Exception e) {
            fail();
        }
        assertEquals(regularBookA, library.findInAvailable("Book A"));
    }

    @Test
    void testLoanBookExpectAlreadyBorrowException() {
        try {
            library.addBook(regularBookA, Clock.systemDefaultZone());
            library.addBook(rareBookB, Clock.systemDefaultZone());
            library.loanBook("Book A", regularPerson, Clock.systemDefaultZone());
            library.loanBook("Book B", regularPerson, Clock.systemDefaultZone());
        } catch (AlreadyBorrowException e) {
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testReturnBookNothingThrown() {
        try{
            library.addBook(regularBookA, Clock.systemDefaultZone());
            library.loanBook("Book A", regularPerson, Clock.systemDefaultZone());
            assertEquals(1, library.numOfBooks());
            assertEquals(1, library.numOfLoaned());
            library.returnBook("Book A", regularPerson, Clock.systemDefaultZone());
            assertEquals(0, library.numOfLoaned());
            assertEquals(1, library.numOfAvailable());
            assertEquals(regularBookA, library.findInAvailable("Book A"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testReturnBookExpectBookNotAvailableException() {
        try {
            library.returnBook("Book A", regularPerson, Clock.systemDefaultZone());
            fail();
        } catch (BookNotAvailableException e) {
        } catch (Exception e) {
            fail();
        }
        try {
            library.addBook(regularBookA, Clock.systemDefaultZone());
            library.loanBook("Book A", regularPerson, Clock.systemDefaultZone());
            Person p = new RegularPerson("Person", "123456789", "aaaaaa@gmail.com");
            library.returnBook("Book A", p, Clock.systemDefaultZone());
            fail();
        } catch (BookNotAvailableException e) {
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testReturnBookExpectNullPersonException() {
        try {
            library.returnBook("Book A", null, Clock.systemDefaultZone());
            fail();
        } catch (NullPersonException e) {
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testFindInAvailable() {
        try {
            assertEquals(null, library.findInAvailable("Book A"));
            library.addBook(regularBookA, Clock.systemDefaultZone());
            assertEquals(regularBookA, library.findInAvailable("Book A"));
            assertEquals(null, library.findInAvailable("Book B"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testFindInLoaned() {
        try {
            assertEquals(null, library.findInLoaned("Book A"));
            library.addBook(regularBookA, Clock.systemDefaultZone());
            library.loanBook("Book A", regularPerson, Clock.systemDefaultZone());
            assertEquals(regularBookA, library.findInLoaned("Book A"));
            assertEquals(null, library.findInLoaned("Book B"));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testGetAvailableBooks() {
        List<Book> books = library.getAvailableBooks();
        assertEquals(0, books.size());
        try {
            library.addBook(regularBookA, Clock.systemDefaultZone());
            library.addBook(rareBookB, Clock.systemDefaultZone());
            books = library.getAvailableBooks();
            assertEquals(2, books.size());
            assertTrue(books.contains(regularBookA));
            assertTrue(books.contains(rareBookB));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testGetLoanedBooks() {
        try {
            library.addBook(regularBookA, Clock.systemDefaultZone());
            library.addBook(rareBookB, Clock.systemDefaultZone());
            library.loanBook("Book A", regularPerson, Clock.systemDefaultZone());
            library.loanBook("Book B", friend, Clock.systemDefaultZone());
            List<Book> books = library.getLoanedBooks();
            assertEquals(2, books.size());
            assertTrue(books.contains(regularBookA));
            assertTrue(books.contains(rareBookB));
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testCheckAllLoanNoExtend() {
        Clock clock = Clock.systemDefaultZone();
        try {
            library.addBook(regularBookA, clock);
            library.loanBook("Book A", regularPerson, clock);
            library.checkAllLoan(clock);
            clock = Clock.offset(clock, Duration.ofDays(Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_REGULAR_PERSON-1));
            assertEquals(ActivityRecord.getDateFromClock(clock), regularBookA.getLoanStatus().getDueDate());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testCheckAllLoanNeedExtend() {
        Clock clock = Clock.systemDefaultZone();
        try {
            library.addBook(regularBookA, clock);
            library.loanBook("Book A", regularPerson, clock);
            clock = Clock.offset(clock, Duration.ofDays(Library.MAXIMUM_LOAN_DAY_REGULAR_BOOK_FOR_REGULAR_PERSON));
            library.checkAllLoan(clock);
            clock = Clock.offset(clock, Duration.ofDays(1));
            assertEquals(ActivityRecord.getDateFromClock(clock), regularBookA.getLoanStatus().getDueDate());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testLoadAndSaveEmpty() throws IOException {
        inFile = new Scanner(new FileInputStream("data/testLibraryLoadEmpty.txt"));
        library.load(inFile);
        inFile.close();
        assertEquals(0, library.numOfBooks());
        assertEquals(0, library.numOfAvailable());
        assertEquals(0, library.numOfLoaned());

        outFile = new FileWriter(new File("data/testSave.txt"));
        library.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("data/testSave.txt"));
        assertTrue(inFile.nextLine().equals("0"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testLoadAndSaveMany() throws IOException {
        inFile = new Scanner(new FileInputStream("data/testLibraryLoadMany.txt"));
        library.load(inFile);
        inFile.close();
        assertEquals(3, library.numOfBooks());
        assertEquals(1, library.numOfAvailable());
        assertEquals(2, library.numOfLoaned());

        outFile = new FileWriter(new File("data/testSave.txt"));
        library.save(outFile);
        ActivityRecord record = library.getActivityRecord();
        outFile.close();
        inFile = new Scanner(new FileInputStream("data/testSave.txt"));
        assertTrue(inFile.nextLine().equals("3"));
        assertTrue(inFile.nextLine().equals(Book.REGULAR_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals(Book.AVAILABLE_CODE));
        assertTrue(inFile.nextLine().equals(Book.RARE_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book B"));
        assertTrue(inFile.nextLine().equals("Author B"));
        assertTrue(inFile.nextLine().equals(Book.NOT_AVAILABLE_CODE));
        assertTrue(inFile.nextLine().equals(Person.REGULAR_PERSON_CODE));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertTrue(inFile.nextLine().equals(ActivityRecord.DATE_FORMAT.format(new GregorianCalendar(2019,10,11).getTime())));
        assertTrue(inFile.nextLine().equals(ActivityRecord.DATE_FORMAT.format(new GregorianCalendar(2019,10,13).getTime())));
        assertTrue(inFile.nextLine().equals(Book.RARE_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book C"));
        assertTrue(inFile.nextLine().equals("Author C"));
        assertTrue(inFile.nextLine().equals(Book.NOT_AVAILABLE_CODE));
        assertTrue(inFile.nextLine().equals(Person.FRIEND_CODE));
        assertTrue(inFile.nextLine().equals("Goku"));
        assertTrue(inFile.nextLine().equals("987654321"));
        assertTrue(inFile.nextLine().equals("aaaaaa@gmail.com"));
        assertTrue(inFile.nextLine().equals(ActivityRecord.DATE_FORMAT.format(new GregorianCalendar(2019,10,11).getTime())));
        assertTrue(inFile.nextLine().equals(ActivityRecord.DATE_FORMAT.format(new GregorianCalendar(2019,10,13).getTime())));
        assertTrue(inFile.nextLine().equals("10/11/2019"));
        assertTrue(inFile.nextLine().equals("3"));
        assertTrue(inFile.nextLine().equals("Book A has been added to the library"));
        assertTrue(inFile.nextLine().equals("Book B has been added to the library"));
        assertTrue(inFile.nextLine().equals("Book C has been added to the library"));
        assertTrue(inFile.nextLine().equals("11/11/2019"));
        assertTrue(inFile.nextLine().equals("2"));
        assertTrue(inFile.nextLine().equals("Kim has borrowed Book B"));
        assertTrue(inFile.nextLine().equals("Goku has borrowed Book C"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }
}
