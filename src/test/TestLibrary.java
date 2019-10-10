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
    void runBefore() {
        library = new Library();
        regularBookA = new RegularBook("Book A", "Author A");
        rareBookB = new RareBook("Book B", "Author B");
        borrower = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
    }

    @Test
    void testAddBook() {
        assertEquals(0, library.numOfAvailable());
        assertEquals(0, library.numOfBooks());
        library.addBook(regularBookA);
        assertEquals(1, library.numOfAvailable());
        assertEquals(1, library.numOfBooks());
        assertEquals(regularBookA, library.findInAvailable("Book A"));
        library.addBook(rareBookB);
        assertEquals(2, library.numOfAvailable());
        assertEquals(2, library.numOfBooks());
        assertEquals(rareBookB, library.findInAvailable("Book B"));
    }

    @Test
    void testLoanBook() {
        library.addBook(regularBookA);
        assertEquals(1, library.numOfBooks());
        assertEquals(1, library.numOfAvailable());
        assertEquals(0, library.numOfLoaned());
        library.loanBook(regularBookA, borrower);
        assertEquals(0, library.numOfAvailable());
        assertEquals(1, library.numOfLoaned());
        assertEquals(regularBookA, library.findInLoaned("Book A"));
    }

    @Test
    void testReturnBook() {
        library.addBook(regularBookA);
        library.loanBook(regularBookA, borrower);
        assertEquals(1, library.numOfBooks());
        assertEquals(1, library.numOfLoaned());
        library.returnBook(regularBookA);
        assertEquals(0, library.numOfLoaned());
        assertEquals(1, library.numOfAvailable());
        assertEquals(regularBookA, library.findInAvailable("Book A"));
    }

    @Test
    void testFindInAvailable() {
        assertEquals(null ,library.findInAvailable("Book A"));
        library.addBook(regularBookA);
        assertEquals(regularBookA, library.findInAvailable("Book A"));
        assertEquals(null, library.findInAvailable("Book B"));
    }

    @Test
    void testFindInLoaned() {
        assertEquals(null, library.findInLoaned("Book A"));
        library.addBook(regularBookA);
        library.loanBook(regularBookA, borrower);
        assertEquals(regularBookA, library.findInLoaned("Book A"));
        assertEquals(null, library.findInLoaned("Book B"));
    }

    @Test
    void testLoadAndSaveEmpty() throws IOException {
        inFile = new Scanner(new FileInputStream("src/test/testLibraryLoadEmpty.txt"));
        library.load(inFile);
        inFile.close();
        assertEquals(0, library.numOfBooks());
        assertEquals(0, library.numOfAvailable());
        assertEquals(0, library.numOfLoaned());

        outFile = new FileWriter(new File("src/test/testSave.txt"));
        library.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testSave.txt"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testLoadAndSaveMany() throws IOException {
        inFile = new Scanner(new FileInputStream("src/test/testLibraryLoadMany.txt"));
        library.load(inFile);
        inFile.close();
        assertEquals(3, library.numOfBooks());
        assertEquals(1, library.numOfAvailable());
        assertEquals(2, library.numOfLoaned());

        outFile = new FileWriter(new File("src/test/testSave.txt"));
        library.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testSave.txt"));
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
