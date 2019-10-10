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
    void runBefore() {
        regularBookA = new RegularBook("Book A", "Author A");
        rareBookB = new RareBook("Book B", "Author B");
        borrower = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
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
        assertTrue(regularBookA.isAvailable());
        regularBookA.beLoaned(borrower);
        assertFalse(regularBookA.isAvailable());
        regularBookA.beReturned();
        assertTrue(regularBookA.isAvailable());
    }

    @Test
    void testGetBorrower() {
        assertEquals(null, regularBookA.getBorrower());
        regularBookA.beLoaned(borrower);
        assertEquals(borrower, regularBookA.getBorrower());
        regularBookA.beReturned();
        assertEquals(null, regularBookA.getBorrower());
    }

    @Test
    void testBeLoaned() {
        regularBookA.beLoaned(borrower);
        assertEquals(borrower, regularBookA.getBorrower());
        assertFalse(regularBookA.isAvailable());
    }

    @Test
    void testBeReturned() {
        regularBookA.beLoaned(borrower);
        regularBookA.beReturned();
        assertEquals(null, regularBookA.getBorrower());
        assertTrue(regularBookA.isAvailable());
    }

    @Test
    void testToStringRegular() {
        assertEquals("Title: Book A\nAuthor: Author A\nThis book is available.\n", regularBookA.toString());
        regularBookA.beLoaned(borrower);
        assertEquals("Title: Book A\nAuthor: Author A\nThis book is loaned.\n", regularBookA.toString());
        regularBookA.beReturned();
        assertEquals("Title: Book A\nAuthor: Author A\nThis book is available.\n", regularBookA.toString());
    }

    @Test
    void testToStringRare() {
        assertEquals("Title: Book B\nAuthor: Author B\n" +
                "This is a rare book.\nThis book is available.\n", rareBookB.toString());
        rareBookB.beLoaned(borrower);
        assertEquals("Title: Book B\nAuthor: Author B\n" +
                "This is a rare book.\nThis book is loaned.\n", rareBookB.toString());
        rareBookB.beReturned();
        assertEquals("Title: Book B\nAuthor: Author B\n" +
                "This is a rare book.\nThis book is available.\n", rareBookB.toString());
    }

    @Test
    void testLoadAndSaveAvailable() throws IOException {
        inFile = new Scanner(new FileInputStream("src/test/testBookAvailableLoad.txt"));
        regularBookA.load(inFile);
        rareBookB.load(inFile);
        inFile.close();
        assertTrue(regularBookA.getTitle().equals("Book A"));
        assertTrue(regularBookA.getAuthor().equals("Author A"));
        assertTrue(regularBookA.isAvailable());
        assertEquals(null, regularBookA.getBorrower());

        outFile = new FileWriter(new File("src/test/testSave.txt"));
        regularBookA.save(outFile);
        rareBookB.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testSave.txt"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Book B"));
        assertTrue(inFile.nextLine().equals("Author B"));
        assertTrue(inFile.nextLine().equals("1"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testLoadAndSaveLoaned() throws IOException {
        inFile = new Scanner(new FileInputStream("src/test/testBookLoanedLoad.txt"));
        regularBookA.load(inFile);
        rareBookB.load(inFile);
        inFile.close();
        assertTrue(regularBookA.getTitle().equals("Book A"));
        assertTrue(regularBookA.getAuthor().equals("Author A"));
        assertFalse(regularBookA.isAvailable());
        assertFalse(regularBookA.getBorrower() == null);

        outFile = new FileWriter(new File("src/test/testSave.txt"));
        regularBookA.save(outFile);
        rareBookB.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testSave.txt"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Book A"));
        assertTrue(inFile.nextLine().equals("Author A"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Book B"));
        assertTrue(inFile.nextLine().equals("Author B"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Goku"));
        assertTrue(inFile.nextLine().equals("987654321"));
        assertTrue(inFile.nextLine().equals("aaaaaa@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

}
