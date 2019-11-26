package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class Friend extends Person {

    public Friend() {}

    public Friend(String name, String phoneNumber, String email) throws EmptyStringException {
        super(name, phoneNumber, email);
    }

    // EFFECTS: return a String that displays the information of friend
    @Override
    public String toString() {
        return super.toString() + "This person is a friend.\n";
    }

    // EFFECTS: save friend's information to file
    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(FRIEND_CODE + "\n");
        super.save(outFile);
    }
}
