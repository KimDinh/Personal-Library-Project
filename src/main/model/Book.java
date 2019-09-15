package model;

public class Book {
    private String title;
    private String author;
    private boolean available;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void changeAvalability() {
        available = !available;
    }

    @Override
    public String toString() {
        return ("Title: " + title + "\nAuthor(s): " + author + "\nThis book is "
                + ((available) ? "available.\n" : "loaned.\n"));
    }
}
