package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class RegularBook extends Book {

    public RegularBook() {}

    public RegularBook(String title, String author) throws EmptyStringException {
        super(title, author);
    }

    @Override
    public String toString() {
        return ("Title: " + title
                + "\nAuthor: " + author
                + "\nThis book is " + ((available) ? "available.\n" : "loaned.\n"));
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write(REGULAR_BOOK_CODE + "\n");
        super.save(outFile);
    }
}
