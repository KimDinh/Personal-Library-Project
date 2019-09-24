import model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPerson {
    private Person person;

    @BeforeEach
    void runBefore() {
        person = new Person("Kim Dinh", "123456789", "hoangkimdinh1608@gmail.com");
    }

    @Test
    void testGetName() {
        assertEquals("Kim Dinh", person.getName());
    }

    @Test
    void testGetPhoneNumber() {
        assertEquals("123456789", person.getPhoneNumber());
    }

    @Test
    void testGetEmail() {
        assertEquals("hoangkimdinh1608@gmail.com", person.getEmail());
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
        person.updateEmail("abcdef@gmail.com");
        assertEquals("abcdef@gmail.com", person.getEmail());
    }

    @Test
    void testToString() {
        assertEquals("Name: Kim Dinh\nPhone Number: 123456789\n" +
                "Email: hoangkimdinh1608@gmail.com\n", person.toString());
    }
}
