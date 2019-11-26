package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class RegularPerson extends Person {

    public RegularPerson() {}

    public RegularPerson(String name, String phoneNumber, String email) throws EmptyStringException {
        super(name, phoneNumber, email);
    }

    // EFFECTS: return a String that displays this person's information
    @Override
    public String toString() {
        return super.toString();
    }

    // EFFECTS: save this person's information to file
    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(REGULAR_PERSON_CODE + "\n");
        super.save(outFile);
    }
}
