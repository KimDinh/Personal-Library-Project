import model.Friend;
import model.Person;
import model.RegularPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestPerson {
    private Person regularPerson;
    private Person friend;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() {
        regularPerson = new RegularPerson("Kim", "123456789", "abcdef@gmail.com");
        friend = new Friend("Goku", "987654321", "aaaaaa@gmail.com");
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
    void testUpdateName() {
        regularPerson.updateName("Goku");
        assertEquals("Goku", regularPerson.getName());
    }

    @Test
    void testUpdatePhoneNumber() {
        regularPerson.updatePhoneNumber("987654321");
        assertEquals("987654321", regularPerson.getPhoneNumber());
    }

    @Test
    void testUpdateEmail() {
        regularPerson.updateEmail("aaaaaa@gmail.com");
        assertEquals("aaaaaa@gmail.com", regularPerson.getEmail());
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
        inFile = new Scanner(new FileInputStream("src/test/testPersonLoad.txt"));
        regularPerson = new RegularPerson();
        friend = new Friend();
        regularPerson.load(inFile);
        friend.load(inFile);
        inFile.close();
        assertTrue(regularPerson.getName().equals("Kim"));
        assertTrue(regularPerson.getPhoneNumber().equals("123456789"));
        assertTrue(regularPerson.getEmail().equals("abcdef@gmail.com"));

        outFile = new FileWriter(new File("src/test/testSave.txt"));
        regularPerson.save(outFile);
        friend.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testSave.txt"));
        assertTrue(inFile.nextLine().equals("0"));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertTrue(inFile.nextLine().equals("1"));
        assertTrue(inFile.nextLine().equals("Goku"));
        assertTrue(inFile.nextLine().equals("987654321"));
        assertTrue(inFile.nextLine().equals("aaaaaa@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }

}
