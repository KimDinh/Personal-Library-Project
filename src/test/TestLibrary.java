import exceptions.*;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;
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
    void testLoanBookNothingThrown() {
        try {
            library.addBook(regularBookA);
            assertEquals(1, library.numOfBooks());
            assertEquals(1, library.numOfAvailable());
            assertEquals(0, library.numOfLoaned());
            library.loanBook("Book A", borrower);
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
            library.loanBook("Book A", borrower);
            fail();
        } catch (BookNotAvailableException e) {
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void testLoanBookExpectNullPersonException() {
        try {
            library.addBook(regularBookA);
            library.loanBook("Book A", null);
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
            library.addBook(regularBookA);
            library.addBook(rareBookB);
            library.loanBook("Book A", borrower);
            library.loanBook("Book B", borrower);
        } catch (AlreadyBorrowException e) {
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testReturnBookNothingThrown() {
        try{
            library.addBook(regularBookA);
            library.loanBook("Book A", borrower);
            assertEquals(1, library.numOfBooks());
            assertEquals(1, library.numOfLoaned());
            library.returnBook("Book A", borrower);
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
            library.returnBook("Book A", borrower);
            fail();
        } catch (BookNotAvailableException e) {
        } catch (Exception e) {
            fail();
        }
        try {
            library.addBook(regularBookA);
            library.loanBook("Book A", borrower);
            Person p = new RegularPerson("Person", "123456789", "aaaaaa@gmail.com");
            library.returnBook("Book A", p);
            fail();
        } catch (BookNotAvailableException e) {
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void testFindInAvailable() {
        try {
            assertEquals(null, library.findInAvailable("Book A"));
            library.addBook(regularBookA);
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
            library.addBook(regularBookA);
            library.loanBook("Book A", borrower);
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
            library.addBook(regularBookA);
            library.addBook(rareBookB);
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
            library.addBook(regularBookA);
            library.addBook(rareBookB);
            library.loanBook("Book A", borrower);
            Person friend = new Friend("Goku", "987654321", "aaaaaa@gmail.com");
            library.loanBook("Book B", friend);
            List<Book> books = library.getLoanedBooks();
            assertEquals(2, books.size());
            assertTrue(books.contains(regularBookA));
            assertTrue(books.contains(rareBookB));
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
        assertTrue(inFile.nextLine().equals(Book.RARE_BOOK_CODE));
        assertTrue(inFile.nextLine().equals("Book C"));
        assertTrue(inFile.nextLine().equals("Author C"));
        assertTrue(inFile.nextLine().equals(Book.NOT_AVAILABLE_CODE));
        assertTrue(inFile.nextLine().equals(Person.FRIEND_CODE));
        assertTrue(inFile.nextLine().equals("Goku"));
        assertTrue(inFile.nextLine().equals("987654321"));
        assertTrue(inFile.nextLine().equals("aaaaaa@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }
}
