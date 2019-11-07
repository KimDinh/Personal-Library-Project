package model;

import exceptions.BookNotAvailableException;
import exceptions.EmptyStringException;
import exceptions.NullBookException;
import exceptions.NullPersonException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class Person implements Loadable, Saveable {
    public static final String REGULAR_PERSON_CODE = "Regular person";
    public static final String FRIEND_CODE = "Friend";
    protected String name;
    protected String phoneNumber;
    protected String email;
    protected Book borrowedBook;

    public Person() {}

    // EFFECTS: initialize name, gender, phoneNumber, email of this person
    // to the parameters passed in
    public Person(String name, String phoneNumber, String email) throws EmptyStringException {
        if (name.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()) {
            throw new EmptyStringException();
        }
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        borrowedBook = null;
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

    // EFFECTS: return the book that this person is borrowing
    public Book getBorrowedBook() {
        return borrowedBook;
    }

    // MODIFIES: this
    // EFFECTS: change this person's name to newName
    public void updateName(String newName) throws EmptyStringException {
        if (newName.isEmpty()) {
            throw new EmptyStringException();
        }
        name = newName;
    }

    // MODIFIES: this
    // EFFECTS: change this person's phone number to newPhoneNumber
    public void updatePhoneNumber(String newPhoneNumber) throws EmptyStringException {
        if (newPhoneNumber.isEmpty()) {
            throw new EmptyStringException();
        }
        phoneNumber = newPhoneNumber;
    }

    // MODIFIES: this
    // EFFECTS: change this person's email address to newEmail
    public void updateEmail(String newEmail) throws EmptyStringException {
        if (newEmail.isEmpty()) {
            throw new EmptyStringException();
        }
        email = newEmail;
    }

    // MODIFIES: this
    // EFFECTS: this person borrows the book
    public void borrowBook(Book book) throws NullPersonException, BookNotAvailableException, NullBookException {
        if (book == null) {
            throw new NullBookException();
        }
        if (book.getBorrower() != null && book.getBorrower() != this) {
            throw new BookNotAvailableException();
        }
        borrowedBook = book;
        if (book.getBorrower() != this) {
            book.beLoaned(this);
        }
    }

    // MODIFIES: this
    // EFFECTS: this person returns the borrowed book
    public void returnBook(Book book) throws BookNotAvailableException, NullPersonException, NullBookException {
        if (book == null) {
            throw new NullBookException();
        }
        if (book.getBorrower() != null && book.getBorrower() != this) {
            throw new BookNotAvailableException();
        }
        borrowedBook = null;
        if (book.getBorrower() == this) {
            book.beReturned(this);
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || (!(o instanceof RegularPerson) && !(o instanceof Friend) && getClass() != o.getClass())) {
            return false;
        }
        Person person = (Person) o;
        return this.name.equals(person.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
