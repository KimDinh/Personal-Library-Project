import exceptions.BookNotAvailableException;
import exceptions.EmptyStringException;
import exceptions.NullBookException;
import exceptions.NullPersonException;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestBook {
    private Book regularBookA;
    private Book rareBookB;
    private Person borrower;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() throws EmptyStringException {
        regularBookA = new RegularBook("Book A", "Author A");
        rareBookB = new RareBook("Book B", "Author B");
        borrower = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
    }

    @Test
    void testBookExpectEmptyStringException() {
        try {
            Book book = new RegularBook("", "Author A");
            fail();
        } catch (EmptyStringException e) {}
        try {
            Book book = new RegularBook("Book A", "");
            fail();
        } catch (EmptyStringException e) {}
    }

    @Test
    void testBookNothingThrown() {
        try {
            Book book = new RegularBook("Book A", "Author A");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testGetTitle() {
        assertEquals("Book A", regularBookA.getTitle());
    }

    @Test
    void testGetAuthor() {
        assertEquals("Author A", regularBookA.getAuthor());
    }

    @Test
    void testIsAvailable() {
        try {
            assertTrue(regularBookA.isAvailable());
            regularBookA.beLoaned(borrower);
            assertFalse(regularBookA.isAvailable());
            regularBookA.beReturned(borrower);
            assertTrue(regularBookA.isAvailable());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testGetBorrower() {
        try {
            assertEquals(null, regularBookA.getBorrower());
            regularBookA.beLoaned(borrower);
            assertEquals(borrower, regularBookA.getBorrower());
            regularBookA.beReturned(borrower);
            assertEquals(null, regularBookA.getBorrower());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testBeLoanedNothingThrown() {
        try {
            regularBookA.beLoaned(borrower);
            assertEquals(borrower, regularBookA.getBorrower());
            assertFalse(regularBookA.isAvailable());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testBeLoanedExpectBookNotAvailableException() {
        try {
            regularBookA.beLoaned(borrower);
            regularBookA.beLoaned(borrower);
            fail();
        } catch (NullPersonException e) {
            fail();
        } catch (BookNotAvailableException e) {
        } catch (NullBookException e) {
            fail();
        }
    }

    @Test
    void testBeLoanedExpectNullPersonException() {
        try {
            regularBookA.beLoaned(null);
            fail();
        } catch (NullPersonException e) {
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullBookException e) {
            fail();
        }
    }

    @Test
    void testBeReturnedNothingThrown() {
        try {
            regularBookA.beLoaned(borrower);
            regularBookA.beReturned(borrower);
            assertEquals(null, regularBookA.getBorrower());
            assertTrue(regularBookA.isAvailable());
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void testBeReturnedExpectBookNotAvailableException() {
        try {
            regularBookA.beReturned(borrower);
            fail();
        } catch (BookNotAvailableException e) {
        } catch (NullBookException e) {
            fail();
        } catch (NullPersonException e) {
            fail();
        }
    }

    @Test
    void testBeReturnedExpectNullPersonException() {
        try {
            regularBookA.beReturned(null);
            fail();
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullBookException e) {
            fail();
        } catch (NullPersonException e) {
        }
    }

    @Test
    void testToStringRegular() {
        try {
            assertEquals("Title: Book A\nAuthor: Author A\nThis book is available.\n", regularBookA.toString());
            regularBookA.beLoaned(borrower);
            assertEquals("Title: Book A\nAuthor: Author A\nThis book is loaned.\n", regularBookA.toString());
            regularBookA.beReturned(borrower);
            assertEquals("Title: Book A\nAuthor: Author A\nThis book is available.\n", regularBookA.toString());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testToStringRare() {
        try {
            assertEquals("Title: Book B\nAuthor: Author B\n" +
                    "This is a rare book.\nThis book is available.\n", rareBookB.toString());
            rareBookB.beLoaned(borrower);
            assertEquals("Title: Book B\nAuthor: Author B\n" +
                    "This is a rare book.\nThis book is loaned.\n", rareBookB.toString());
            rareBookB.beReturned(borrower);
            assertEquals("Title: Book B\nAuthor: Author B\n" +
                    "This is a rare book.\nThis book is available.\n", rareBookB.toString());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testLoadAndSaveAvailable() throws IOException {
        inFile = new Scanner(new FileInputStream("data/testBookAvailableLoad.txt"));
        regularBookA.load(inFile);
        rareBookB.load(inFile);
        inFile.close();
        assertTrue(regularBookA.getTitle().equals("Book A"));
        assertTrue(regularBookA.getAuthor().equals("Author A"));
        assertTrue(regularBookA.isAvailable());
        assertEquals(null, regularBookA.getBorrower());

        outFile = new FileWriter(new File("data/testSave.txt"));
        regularBookA.save(outFile);
        rareBookB.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("data/testSave.txt"));
        assertTrue(inFile.nextLine().equals(Book.REGULAR_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals(Book.AVAILABLE_CODE));
        assertTrue(inFile.nextLine().equals(Book.RARE_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book B"));
        assertTrue(inFile.nextLine().equals("Author B"));
        assertTrue(inFile.nextLine().equals(Book.AVAILABLE_CODE));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testLoadAndSaveLoaned() throws IOException {
        inFile = new Scanner(new FileInputStream("data/testBookLoanedLoad.txt"));
        regularBookA.load(inFile);
        rareBookB.load(inFile);
        inFile.close();
        assertTrue(regularBookA.getTitle().equals("Book A"));
        assertTrue(regularBookA.getAuthor().equals("Author A"));
        assertFalse(regularBookA.isAvailable());
        assertFalse(regularBookA.getBorrower() == null);

        outFile = new FileWriter(new File("data/testSave.txt"));
        regularBookA.save(outFile);
        rareBookB.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("data/testSave.txt"));
        assertTrue(inFile.nextLine().equals(Book.REGULAR_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals(Book.NOT_AVAILABLE_CODE));
        assertTrue(inFile.nextLine().equals(Person.REGULAR_PERSON_CODE));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertTrue(inFile.nextLine().equals(Book.RARE_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book B"));
        assertTrue(inFile.nextLine().equals("Author B"));
        assertTrue(inFile.nextLine().equals(Book.NOT_AVAILABLE_CODE));
        assertTrue(inFile.nextLine().equals(Person.FRIEND_CODE));
        assertTrue(inFile.nextLine().equals("Goku"));
        assertTrue(inFile.nextLine().equals("987654321"));
        assertTrue(inFile.nextLine().equals("aaaaaa@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testEquals() {
        assertFalse(regularBookA.equals(null));
        assertTrue(regularBookA.equals(regularBookA));
        assertFalse(regularBookA.equals(borrower));
        assertFalse(regularBookA.equals(rareBookB));
        try {
            Book anotherBookA = new RareBook("Book A", "Author");
            assertTrue(regularBookA.equals(anotherBookA));
        } catch (Exception e) {
            fail();
        }
    }

}
