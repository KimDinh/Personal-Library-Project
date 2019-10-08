package ui;

import model.*;

import java.io.*;
import java.util.*;

public class Main {
    private Scanner scanner;
    private Library library;

    public Main() {
        scanner = new Scanner(System.in);
        library = new Library();
    }

    public static void main(String[] args) throws IOException {
        Main libraryApp = new Main();
        libraryApp.sayHello();
        libraryApp.loadFromFile();
        libraryApp.getUserCommand();
        libraryApp.saveToFile();
    }

    private void loadFromFile() throws IOException {
        Scanner inFile = new Scanner(new FileInputStream("library.txt"));
        library.load(inFile);
        inFile.close();
    }

    private void saveToFile() throws IOException {
        FileWriter outFile = new FileWriter(new File("library.txt"));
        library.save(outFile);
        outFile.close();
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
        System.out.println("[4] See a book");
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
            case "4":
                findBook();
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
        System.out.print("Is it a rare book? Press 'y' for yes. Press any other key for no. ");
        String rare = scanner.nextLine();
        Book newBook;
        if (rare.toLowerCase().equals("y")) {
            newBook = new RareBook(title, author);
        } else {
            newBook = new RegularBook(title, author);
        }
        library.addBook(newBook);
        System.out.println("You have successfully added the book.\n");
    }

    // EFFECTS: if the inputted title matches the title of an available book,
    // get the borrower's info and loan the book; otherwise do nothing
    private void loanBook() {
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        Book book = library.findInAvailable(title);
        if (book == null) {
            System.out.println("This book is not available.\n");
            return;
        }
        Person borrower = getBorrowerInfo();
        library.loanBook(book, borrower);
        System.out.println("The book has successfully been loaned.\n");
    }

    // EFFECTS: print the information of all the books in the library
    private void printAllBooks() {
        if (library.numOfBooks() == 0) {
            System.out.println("There is no book in your library.\n");
            return;
        }
        library.printAvailableBooks();
        library.printLoanedBooks();
    }

    // EFFECTS: print all the books whose titles matches the inputted title
    private void findBook() {
        System.out.println("Enter the book's title: ");
        String title = scanner.nextLine();
        Book bookAvailable = library.findInAvailable(title);
        Book bookLoaned = library.findInLoaned(title);
        if (bookAvailable == null && bookLoaned == null) {
            System.out.println("This book is not in the library.\n");
        } else if (bookAvailable != null) {
            printBook(bookAvailable);
        } else {
            printBook(bookLoaned);
        }
    }

    private void printBook(Book book) {
        System.out.println(book);
        if (!book.isAvailable()) {
            System.out.println("Press [1] to see borrower's info.");
            System.out.println("Press any other key to return.");
            String command = scanner.nextLine();
            if (command.equals(("1"))) {
                System.out.println(book.getBorrower());
            }
        }
    }

    // EFFECTS: return a new Person with the inputted info of the borrower
    private Person getBorrowerInfo() {
        System.out.print("Enter the information of the borrower:\nName: ");
        String name = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        return new Person(name, phoneNumber, email);
    }
}
