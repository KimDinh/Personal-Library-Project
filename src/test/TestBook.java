import model.Book;
import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestBook {
    private Book book;
    private Person borrower;

    @BeforeEach
    void runBefore() {
        book = new Book("Book A", "Author A");
        borrower = new Person("Borrower", "", "");
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

}
