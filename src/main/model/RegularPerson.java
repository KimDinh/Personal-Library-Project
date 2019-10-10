package model;

import java.io.FileWriter;
import java.io.IOException;

public class RegularPerson extends Person {

    public RegularPerson() {}

    public RegularPerson(String name, String phoneNumber, String email) {
        super(name, phoneNumber, email);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void save(FileWriter outFile) throws IOException {
        outFile.write("0\n");
        super.save(outFile);
    }
}
