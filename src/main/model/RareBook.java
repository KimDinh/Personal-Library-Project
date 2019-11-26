package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class RareBook extends Book {

    public RareBook() {}

    public RareBook(String title, String author) throws EmptyStringException {
        super(title, author);
    }

    // EFFECTS: return a String that display the information of this rare book
    @Override
    public String toString() {
        return ("Title: " + title
                + "\nAuthor: " + author
                + "\nThis is a rare book."
                + "\nThis book is " + ((available) ? "available.\n" : "loaned.\n"));
    }

    // EFFECTS: save this book's information to file
    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(RARE_BOOK_CODE + "\n");
        super.save(outFile);
    }
}
