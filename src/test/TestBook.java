import model.Book;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestBook {
    private Book book;
    private Person borrower;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() {
        book = new Book("Book A", "Author A");
        borrower = new Person("Kim", "123456789", "abcdef@gmail.com");
    }

    @Test
    void testGetTitle() {
        assertEquals("Book A", book.getTitle());
    }

    @Test
    void testGetAuthor() {
        assertEquals("Author A", book.getAuthor());
    }

    @Test
    void testIsAvailable() {
        assertTrue(book.isAvailable());
        book.beLoaned(borrower);
        assertFalse(book.isAvailable());
        book.beReturned();
        assertTrue(book.isAvailable());
    }

    @Test
    void testGetBorrower() {
        assertEquals(null, book.getBorrower());
        book.beLoaned(borrower);
        assertEquals(borrower, book.getBorrower());
        book.beReturned();
        assertEquals(null, book.getBorrower());
    }

    @Test
    void testBeLoaned() {
        book.beLoaned(borrower);
        assertEquals(borrower, book.getBorrower());
        assertFalse(book.isAvailable());
    }

    @Test
    void testBeReturned() {
        book.beLoaned(borrower);
        book.beReturned();
        assertEquals(null, book.getBorrower());
        assertTrue(book.isAvailable());
    }

    @Test
    void testToString() {
        assertEquals("Title: Book A\nAuthor: Author A\nThis book is available.\n", book.toString());
        book.beLoaned(borrower);
        assertEquals("Title: Book A\nAuthor: Author A\nThis book is loaned.\n", book.toString());
        book.beReturned();
        assertEquals("Title: Book A\nAuthor: Author A\nThis book is available.\n", book.toString());
    }

    @Test
    void testLoadAvailable() throws FileNotFoundException {
        inFile = new Scanner(new FileInputStream("src/test/testBookAvailableLoad.txt"));
        book.load(inFile);
        inFile.close();
        assertTrue(book.getTitle().equals("Book A"));
        assertTrue(book.getAuthor().equals("Author A"));
        assertTrue(book.isAvailable());
        assertEquals(null, book.getBorrower());
    }

    @Test
    void testLoadLoaned() throws FileNotFoundException {
        inFile = new Scanner(new FileInputStream("src/test/testBookLoanedLoad.txt"));
        book.load(inFile);
        inFile.close();
        assertTrue(book.getTitle().equals("Book A"));
        assertTrue(book.getAuthor().equals("Author A"));
        assertFalse(book.isAvailable());
        assertFalse(book.getBorrower() == null);
    }

    @Test
    void testSaveAvailable() throws IOException {
        outFile = new FileWriter(new File("src/test/testBookSave.txt"));
        book.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testBookSave.txt"));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals("1"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testSaveLoaned() throws IOException {
        outFile = new FileWriter(new File("src/test/testBookSave.txt"));
        book.beLoaned(borrower);
        book.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testBookSave.txt"));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }
}
