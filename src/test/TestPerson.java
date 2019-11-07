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

public class TestPerson {
    private Person regularPerson;
    private Person friend;
    private Book book;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() throws EmptyStringException {
        regularPerson = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
        friend = new Friend("Goku", "987654321", "aaaaaa@gmail.com");
        book = new RegularBook("Book A", "Author A");
    }

    @Test
    void testPersonExpectEmptyStringException() {
        try {
            Person person = new RegularPerson("", "123456789", "abcdef@gmail.com");
            fail();
        } catch (EmptyStringException e) {}
        try {
            Person person = new RegularPerson("Kim", "", "abcdef@gmail.com");
            fail();
        } catch (EmptyStringException e) {}
        try {
            Person person = new RegularPerson("Kim", "123456789", "");
            fail();
        } catch (EmptyStringException e) {}
    }

    @Test
    void testPersonNothingThrown() {
        try {
            Person person = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
        } catch (EmptyStringException e) {
            fail();
        }
    }

    @Test
    void testGetName() {
        assertEquals("Kim", regularPerson.getName());
    }

    @Test
    void testGetPhoneNumber() {
        assertEquals("123456789", regularPerson.getPhoneNumber());
    }

    @Test
    void testGetEmail() {
        assertEquals("abcdef@gmail.com", regularPerson.getEmail());
    }


    @Test
    void testUpdateNameNothingThrown() {
        try {
            regularPerson.updateName("Goku");
        } catch (EmptyStringException e) {
            fail();
        }
        assertEquals("Goku", regularPerson.getName());
    }

    @Test
    void testUpdateNameExpectEmptyStringException() {
        try {
            regularPerson.updateName("");
            fail();
        } catch (EmptyStringException e) {}
        assertEquals("Kim", regularPerson.getName());
    }

    @Test
    void testUpdatePhoneNumberNothingThrown() {
        try {
            regularPerson.updatePhoneNumber("987654321");
        } catch (EmptyStringException e) {
            fail();
        }
        assertEquals("987654321", regularPerson.getPhoneNumber());
    }

    @Test
    void testUpdatePhoneNumberExpectEmptyStringException() {
        try {
            regularPerson.updatePhoneNumber("");
            fail();
        } catch (EmptyStringException e) {}
        assertEquals("123456789", regularPerson.getPhoneNumber());
    }

    @Test
    void testUpdateEmailNothingThrown() {
        try {
            regularPerson.updateEmail("aaaaaa@gmail.com");
        } catch (EmptyStringException e) {
            fail();
        }
        assertEquals("aaaaaa@gmail.com", regularPerson.getEmail());
    }

    @Test
    void testUpdateEmailExpectEmptyStringException() {
        try {
            regularPerson.updateEmail("");
            fail();
        } catch (EmptyStringException e) {}
        assertEquals("abcdef@gmail.com", regularPerson.getEmail());
    }

    @Test
    void testBorrowBookNothingThrown() {
        try {
            regularPerson.borrowBook(book);
            assertEquals(book, regularPerson.getBorrowedBook());
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullPersonException e) {
            fail();
        } catch (NullBookException e) {
            fail();
        }
    }

    @Test
    void testBorrowBookExpectBookNotAvailableException() {
        try {
            regularPerson.borrowBook(book);
            friend.borrowBook(book);
            fail();
        } catch (BookNotAvailableException e) {
        } catch (NullPersonException e) {
            fail();
        } catch (NullBookException e) {
            fail();
        }
    }

    @Test
    void testBorrowBookExpectNullBookException() {
        try {
            regularPerson.borrowBook(null);
            fail();
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullPersonException e) {
            fail();
        } catch (NullBookException e) {
        }
    }

    @Test
    void testReturnBookNothingThrown() {
        try {
            regularPerson.borrowBook(book);
            regularPerson.returnBook(book);
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullPersonException e) {
            fail();
        } catch (NullBookException e) {
            fail();
        }
    }

    @Test
    void testReturnBookExpectBookNotAvailableException() {
        try {
            regularPerson.borrowBook(book);
            friend.returnBook(book);
            fail();
        } catch (BookNotAvailableException e) {
        } catch (NullPersonException e) {
            fail();
        } catch (NullBookException e) {
            fail();
        }
    }

    @Test
    void testReturnBookExpectNullBookException() {
        try {
            regularPerson.returnBook(null);
            fail();
        } catch (BookNotAvailableException e) {
            fail();
        } catch (NullBookException e) {
        } catch (NullPersonException e) {
            fail();
        }
    }

    @Test
    void testToString() {
        assertEquals("Name: Kim\nPhone Number: 123456789\n" +
                "Email: abcdef@gmail.com\n", regularPerson.toString());
        assertEquals("Name: Goku\nPhone Number: 987654321\n" +
                "Email: aaaaaa@gmail.com\nThis person is a friend.\n", friend.toString());
    }

    @Test
    void testLoadAndSave() throws IOException {
        inFile = new Scanner(new FileInputStream("data/testPersonLoad.txt"));
        regularPerson = new RegularPerson();
        friend = new Friend();
        regularPerson.load(inFile);
        friend.load(inFile);
        inFile.close();
        assertTrue(regularPerson.getName().equals("Kim"));
        assertTrue(regularPerson.getPhoneNumber().equals("123456789"));
        assertTrue(regularPerson.getEmail().equals("abcdef@gmail.com"));

        outFile = new FileWriter(new File("data/testSave.txt"));
        regularPerson.save(outFile);
        friend.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("data/testSave.txt"));
        assertTrue(inFile.nextLine().equals(Person.REGULAR_PERSON_CODE));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertTrue(inFile.nextLine().equals(Person.FRIEND_CODE));
        assertTrue(inFile.nextLine().equals("Goku"));
        assertTrue(inFile.nextLine().equals("987654321"));
        assertTrue(inFile.nextLine().equals("aaaaaa@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

    @Test
    void testEquals() {
        assertFalse(regularPerson.equals(null));
        assertFalse(regularPerson.equals(book));
        assertFalse(regularPerson.equals(friend));
        assertTrue(regularPerson.equals(regularPerson));
        try {
            Person samePerson = new Friend("Kim", "111111111", "aaaaaa@gmail.com");
            assertTrue(regularPerson.equals(samePerson));
        } catch (Exception e) {
            fail();
        }
    }

}
