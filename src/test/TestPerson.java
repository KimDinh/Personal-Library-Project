import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestPerson {
    private Person person;
    Scanner inFile;
    FileWriter outFile;

    @BeforeEach
    void runBefore() {
        person = new Person("Kim", "123456789", "abcdef@gmail.com");
    }

    @Test
    void testGetName() {
        assertEquals("Kim", person.getName());
    }

    @Test
    void testGetPhoneNumber() {
        assertEquals("123456789", person.getPhoneNumber());
    }

    @Test
    void testGetEmail() {
        assertEquals("abcdef@gmail.com", person.getEmail());
    }

    @Test
    void testUpdateName() {
        person.updateName("Goku");
        assertEquals("Goku", person.getName());
    }

    @Test
    void testUpdatePhoneNumber() {
        person.updatePhoneNumber("987654321");
        assertEquals("987654321", person.getPhoneNumber());
    }

    @Test
    void testUpdateEmail() {
        person.updateEmail("aaaaaa@gmail.com");
        assertEquals("aaaaaa@gmail.com", person.getEmail());
    }

    @Test
    void testToString() {
        assertEquals("Name: Kim\nPhone Number: 123456789\n" +
                "Email: abcdef@gmail.com\n", person.toString());
    }

    @Test
    void testLoad() throws FileNotFoundException {
        inFile = new Scanner(new FileInputStream("src/test/testPersonLoad.txt"));
        person.load(inFile);
        inFile.close();
        assertTrue(person.getName().equals("Kim"));
        assertTrue(person.getPhoneNumber().equals("123456789"));
        assertTrue(person.getEmail().equals("abcdef@gmail.com"));
    }

    @Test
    void testSave() throws IOException {
        outFile = new FileWriter(new File("src/test/testPersonSave.txt"));
        person.save(outFile);
        outFile.close();
        inFile = new Scanner(new FileInputStream("src/test/testPersonSave.txt"));
        assertTrue(inFile.nextLine().equals("Kim"));
        assertTrue(inFile.nextLine().equals("123456789"));
        assertTrue(inFile.nextLine().equals("abcdef@gmail.com"));
        assertFalse(inFile.hasNext());
        inFile.close();
    }
}
