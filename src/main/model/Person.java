package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Person implements Loadable, Saveable {
    private String name;
    private String phoneNumber;
    private String email;

    public Person() {

    }

    // REQUIRES: name must be a non-empty string
    // EFFECTS: initialize name, gender, phoneNumber, email of this person
    // to the parameters passed in
    public Person(String name, String phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // EFFECTS: return the name of this person
    public String getName() {
        return name;
    }

    // EFFECTS: return the phone number of this person
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // EFFECTS: return the email address of this person
    public String getEmail() {
        return email;
    }

    // REQUIRES: newName must be a non-empty string
    // MODIFIES: this
    // EFFECTS: change this person's name to newName
    public void updateName(String newName) {
        name = newName;
    }

    // MODIFIES: this
    // EFFECTS: change this person's phone number to newPhoneNumber
    public void updatePhoneNumber(String newPhoneNumber) {
        phoneNumber = newPhoneNumber;
    }

    // MODIFIES: this
    // EFFECTS: change this person's email address to newEmail
    public void updateEmail(String newEmail) {
        email = newEmail;
    }

    // EFFECTS: return a string that displays the information of this person
    @Override
    public String toString() {
        return ("Name: " + name
                + "\nPhone Number: " + phoneNumber
                + "\nEmail: " + email + "\n");
    }

    @Override
    public void load(Scanner inFile) {
        name = inFile.nextLine();
        phoneNumber = inFile.nextLine();
        email = inFile.nextLine();
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(name + "\n" + phoneNumber + "\n" + email + "\n");
    }
}
