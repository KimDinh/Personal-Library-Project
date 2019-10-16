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

public class TestLibrary {
    private Library library;
    private Book regularBookA;
    private Book rareBookB;
    Person borrower;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() throws EmptyStringException {
        library = new Library();
        regularBookA = new RegularBook("Book A", "Author A");
        rareBookB = new RareBook("Book B", "Author B");
        borrower = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
    }

    @Test
    void testAddBookNothingThrown() {
        assertEquals(0, library.numOfAvailable());
        assertEquals(0, library.numOfBooks());
        try {
            library.addBook(regularBookA);
        } catch (NullBookException e) {
            fail();
        }
        assertEquals(1, library.numOfAvailable());
        assertEquals(1, library.numOfBooks());
        assertEquals(regularBookA, library.findInAvailable("Book A"));
        try {
            library.addBook(rareBookB);
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
            library.addBook(null);
            fail();
        } catch (NullBookException e) {}
    }

    @Test
    void testLoanBookNothingThrown() throws NullBookException {
        library.addBook(regularBookA);
        assertEquals(1, library.numOfBooks());
        assertEquals(1, library.numOfAvailable());
        assertEquals(0, library.numOfLoaned());
        try {
            library.loanBook("Book A", borrower);
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullPersonException e) {
            fail();
        }
        assertEquals(0, library.numOfAvailable());
        assertEquals(1, library.numOfLoaned());
        assertEquals(regularBookA, library.findInLoaned("Book A"));
    }

    @Test
    void testLoanBookExpectBookNotAvailableException() {
        try {
            library.loanBook("Book A", borrower);
            fail();
        } catch (BookNotAvailableException e) {
        } catch (NullPersonException e) {
            fail();
        }
    }

    @Test
    void testLoanBookExpectNullPersonException() throws NullBookException {
        library.addBook(regularBookA);
        try {
            library.loanBook("Book A", null);
            fail();
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullPersonException e) {}
        assertEquals(regularBookA, library.findInAvailable("Book A"));
    }

    @Test
    void testReturnBookNothingThrown() throws NullPersonException, BookNotAvailableException, NullBookException {
        library.addBook(regularBookA);
        library.loanBook("Book A", borrower);
        assertEquals(1, library.numOfBooks());
        assertEquals(1, library.numOfLoaned());
        try{
            library.returnBook("Book A");
        } catch (BookNotAvailableException e) {
            fail();
        }
        assertEquals(0, library.numOfLoaned());
        assertEquals(1, library.numOfAvailable());
        assertEquals(regularBookA, library.findInAvailable("Book A"));
    }

    @Test
    void testReturnBookExpectBookNotAvailableException() {
        try {
            library.returnBook("Book A");
            fail();
        } catch (BookNotAvailableException e) {}
    }

    @Test
    void testFindInAvailable() throws NullBookException {
        assertEquals(null ,library.findInAvailable("Book A"));
        library.addBook(regularBookA);
        assertEquals(regularBookA, library.findInAvailable("Book A"));
        assertEquals(null, library.findInAvailable("Book B"));
    }

    @Test
    void testFindInLoaned() throws NullPersonException, BookNotAvailableException, NullBookException {
        assertEquals(null, library.findInLoaned("Book A"));
        library.addBook(regularBookA);
        library.loanBook("Book A", borrower);
        assertEquals(regularBookA, library.findInLoaned("Book A"));
        assertEquals(null, library.findInLoaned("Book B"));
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
        outFile.close();
        inFile = new Scanner(new FileInputStream("data/testSave.txt"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Book B"));
        assertTrue(inFile.nextLine().equals("Author B"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Book C"));
        assertTrue(inFile.nextLine().equals("Author C"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Goku"));
        assertTrue(inFile.nextLine().equals("987654321"));
        assertTrue(inFile.nextLine().equals("aaaaaa@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }
}
