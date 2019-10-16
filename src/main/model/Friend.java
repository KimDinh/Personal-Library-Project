package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class Friend extends Person {

    public Friend() {}

    public Friend(String name, String phoneNumber, String email) throws EmptyStringException {
        super(name, phoneNumber, email);
    }

    @Override
    public String toString() {
        return super.toString() + "This person is a friend.\n";
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write("1\n");
        super.save(outFile);
    }
}
