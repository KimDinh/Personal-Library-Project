package model;

import java.io.FileWriter;
import java.io.IOException;

public class Friend extends Person {

    public Friend() {}

    public Friend(String name, String phoneNumber, String email) {
        super(name, phoneNumber, email);
    }

    public String toString() {
        return super.toString() + "This person is a friend.\n";
    }

    public void save(FileWriter outFile) throws IOException {
        outFile.write("1\n");
        super.save(outFile);
    }
}
