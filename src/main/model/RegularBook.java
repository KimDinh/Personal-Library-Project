package model;

import exceptions.EmptyStringException;

import java.io.FileWriter;
import java.io.IOException;

public class RegularBook extends Book {
    private static final int MAXIMUM_LOAN_DAYS_FOR_REGULAR_PERSON = 7;
    private static final int MAXIMUM_LOAN_DAYS_FOR_FRIEND = 10;

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
        outFile.write("0\n");
        super.save(outFile);
    }
}
