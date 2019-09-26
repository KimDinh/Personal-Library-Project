import model.Book;
import model.Library;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestLibrary {
    private Library library;
    private Book bookA;
    private Book bookB;
    Person borrower;

    @BeforeEach
    void runBefore() {
        library = new Library();
        bookA = new Book("Book A", "Author A");
        bookB = new Book("Book B", "Author B");
        borrower = new Person();
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
        library.addBook(bookB);
        assertEquals(2, library.numOfBooks());
        assertEquals(0, library.numOfLoaned());
        assertTrue(library.loanBook("Book A", borrower));
        assertEquals(1, library.numOfAvailable());
        assertEquals(1, library.numOfLoaned());
        assertEquals(bookA, library.findInLoaned("Book A"));
        assertFalse(library.loanBook("Book A", borrower));
        assertEquals(1, library.numOfAvailable());
        assertEquals(1, library.numOfLoaned());
    }

    @Test
    void testReturnBook() {
        library.addBook(bookA);
        library.addBook(bookB);
        library.loanBook("Book A", borrower);
        library.loanBook("Book B" ,borrower);
        assertEquals(2, library.numOfBooks());
        assertEquals(2, library.numOfLoaned());
        assertTrue(library.returnBook("Book A"));
        assertEquals(1, library.numOfLoaned());
        assertEquals(1, library.numOfAvailable());
        assertEquals(bookA, library.findInAvailable("Book A"));
        assertFalse(library.returnBook("Book C"));
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
        library.loanBook("Book A", borrower);
        assertEquals(bookA, library.findInLoaned("Book A"));
    }
}
