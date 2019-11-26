package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class RegularBook extends Book {

    public RegularBook() {}

    public RegularBook(String title, String author) throws EmptyStringException {
        super(title, author);
    }

    // EFFECTS: return a String that display this book's information
    @Override
    public String toString() {
        return ("Title: " + title
                + "\nAuthor: " + author
                + "\nThis book is " + ((available) ? "available.\n" : "loaned.\n"));
    }

    // EFFECTS: save this book's information to file
    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(REGULAR_BOOK_CODE + "\n");
        super.save(outFile);
    }
}
