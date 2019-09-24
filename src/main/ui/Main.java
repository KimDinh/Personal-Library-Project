package ui;

import model.Book;
import model.Person;

import java.util.*;

public class Main {
    private Scanner scanner;
    private ArrayList<Book> availableBooks;
    private ArrayList<Book> loanedBooks;

    public Main() {
        availableBooks = new ArrayList<>();
        loanedBooks = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main libraryApp = new Main();
        libraryApp.sayHello();
        libraryApp.getUserCommand();
    }

    // EFFECTS: get the command from user until quit
    private void getUserCommand() {
        while (true) {
            helpMenu();
            String command = scanner.nextLine();
            if (command.equals("q")) {
                break;
            }
            processCommand(command);
        }
    }

    private void sayHello() {
        System.out.println("Hello! This is a personal library application.");
    }

    // EFFECTS: print a help menu with functionality that the user can choose
    private void helpMenu() {
        System.out.println("Please enter what you would like to do:");
        System.out.println("[1] Add a book");
        System.out.println("[2] Loan a book");
        System.out.println("[3] See all the books");
        System.out.println("[q] Quit");
    }

    // EFFECTS: call the appropriate functionality corresponding to the command passed in
    private void processCommand(String command) {
        switch (command) {
            case "1":
                addBook();
                break;
            case "2":
                loanBook();
                break;
            case "3":
                printAllBooks();
                break;
            default:
                System.out.println("You have entered an invalid command.\n");
                break;
        }
    }

    // EFFECTS: add a new book to availableBooks
    private void addBook() {
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the book's author: ");
        String author = scanner.nextLine();
        Book newBook = new Book(title, author);
        availableBooks.add(newBook);
        System.out.println("You have successfully added the book.\n");
    }

    // EFFECTS: if the inputted title is found in a book in availableBooks,
    // move the book from availableBooks to loanedBooks; otherwise do nothing
    private void loanBook() {
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        for (Book book : availableBooks) {
            if (book.getTitle().equals(title)) {
                availableBooks.remove(book);
                System.out.println("Enter the information of the borrower:");
                Person borrower = getBorrowerInfo();
                book.beLoaned(borrower);
                loanedBooks.add(book);
                System.out.println("The book has successfully been loaned.\n");
                return;
            }
        }
        System.out.println("This book is not available.\n");
    }

    // EFFECTS: print the information of all the books in the library
    private void printAllBooks() {
        if (availableBooks.size() == 0 && loanedBooks.size() == 0) {
            System.out.println("There is no book in your library.\n");
            return;
        }
        for (Book book : availableBooks) {
            System.out.println(book);
        }
        for (Book book : loanedBooks) {
            System.out.println(book);
        }
    }

    // EFFECTS: return a new Person with the inputted info of the borrower
    private Person getBorrowerInfo() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        return new Person(name, phoneNumber, email);
    }
}
