import model.Book;
import model.Library;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestLibrary {
    private Library library;
    private Book bookA;
    private Book bookB;
    Person borrower;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() {
        library = new Library();
        bookA = new Book("Book A", "Author A");
        bookB = new Book("Book B", "Author B");
        borrower = new Person("Kim", "123456789", "abcdef@gmail.com");
    }

    @Test
    void testAddBook() {
        assertEquals(0, library.numOfAvailable());
        assertEquals(0, library.numOfBooks());
        library.addBook(bookA);
        assertEquals(1, library.numOfAvailable());
        assertEquals(1, library.numOfBooks());
        assertEquals(bookA, library.findInAvailable("Book A"));
        library.addBook(bookB);
        assertEquals(2, library.numOfAvailable());
        assertEquals(2, library.numOfBooks());
        assertEquals(bookB, library.findInAvailable("Book B"));
    }

    @Test
    void testLoanBook() {
        library.addBook(bookA);
        assertEquals(1, library.numOfBooks());
        assertEquals(1, library.numOfAvailable());
        assertEquals(0, library.numOfLoaned());
        library.loanBook(bookA, borrower);
        assertEquals(0, library.numOfAvailable());
        assertEquals(1, library.numOfLoaned());
        assertEquals(bookA, library.findInLoaned("Book A"));
    }

    @Test
    void testReturnBook() {
        library.addBook(bookA);
        library.loanBook(bookA, borrower);
        assertEquals(1, library.numOfBooks());
        assertEquals(1, library.numOfLoaned());
        library.returnBook(bookA);
        assertEquals(0, library.numOfLoaned());
        assertEquals(1, library.numOfAvailable());
        assertEquals(bookA, library.findInAvailable("Book A"));
    }

    @Test
    void testFindInAvailable() {
        assertEquals(null ,library.findInAvailable("Book A"));
        library.addBook(bookA);
        assertEquals(bookA, library.findInAvailable("Book A"));
        assertEquals(null, library.findInAvailable("Book B"));
    }

    @Test
    void testFindInLoaned() {
        assertEquals(null, library.findInLoaned("Book A"));
        library.addBook(bookA);
        library.loanBook(bookA, borrower);
        assertEquals(bookA, library.findInLoaned("Book A"));
    }

    @Test
    void testLoadEmpty() throws FileNotFoundException {
        inFile = new Scanner(new FileInputStream("src/test/testLibraryLoadEmpty.txt"));
        library.load(inFile);
        inFile.close();
        assertEquals(0, library.numOfBooks());
        assertEquals(0, library.numOfAvailable());
        assertEquals(0, library.numOfLoaned());
    }

    @Test
    void testLoadMany() throws FileNotFoundException {
        inFile = new Scanner(new FileInputStream("src/test/testLibraryLoadMany.txt"));
        library.load(inFile);
        inFile.close();
        assertEquals(3, library.numOfBooks());
        assertEquals(1, library.numOfAvailable());
        assertEquals(2, library.numOfLoaned());
    }

    @Test
    void testSaveEmpty() throws IOException {
        outFile = new FileWriter(new File("src/test/testLibrarySave.txt"));
        library.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testLibrarySave.txt"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testSaveMany() throws IOException {
        outFile = new FileWriter(new File("src/test/testLibrarySave.txt"));
        library.addBook(bookA);
        library.addBook(bookB);
        library.loanBook(bookB, borrower);
        library.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testLibrarySave.txt"));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Book B"));
        assertTrue(inFile.nextLine().equals("Author B"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }
}
