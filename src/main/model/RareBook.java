package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class RareBook extends Book {
    private static final int MAXIMUM_LOAN_DAYS = 4;

    public RareBook() {}

    public RareBook(String title, String author) throws EmptyStringException {
        super(title, author);
    }

    @Override
    public String toString() {
        return ("Title: " + title
                + "\nAuthor: " + author
                + "\nThis is a rare book."
                + "\nThis book is " + ((available) ? "available.\n" : "loaned.\n"));
    }

    @Override
    public void save(FileWriter outFile) throws IOException {
        outFile.write("1\n");
        super.save(outFile);
    }
}
