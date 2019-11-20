package ui;

import exceptions.*;
import model.*;
import network.WeatherInfo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.Clock;
import java.util.*;
import java.util.List;

public class Main implements ActionListener {
    private Scanner scanner;
    private Library library;
    private WeatherInfo weather;
    private JFrame frame;
    private JButton addButton;
    private JButton loanButton;
    private JButton returnButton;
    private JButton printAllButton;
    private JButton findButton;
    private JButton printRecordButton;

    public static void main(String[] args) throws IOException {
        Main libraryApp = new Main();
        libraryApp.getWeatherInfo();
        libraryApp.sayHello();
        libraryApp.loadFromFile();
        libraryApp.getUserCommand();
        libraryApp.saveToFile();
    }

    public Main() {
        scanner = new Scanner(System.in);
        library = new Library();
        weather = new WeatherInfo();
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("Your library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 600));
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,2));
        panel.setBorder(new EmptyBorder(new Insets(200,100,200,100)));
        initButton();
        panel.add(addButton);
        panel.add(loanButton);
        panel.add(returnButton);
        panel.add(printAllButton);
        panel.add(findButton);
        panel.add(printRecordButton);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void initButton() {
        initButton(addButton, "Add a book", "addBook");
        initButton(loanButton, "Loan a book", "loanBook");
        initButton(returnButton, "Return a book", "returnBook");
        initButton(printAllButton, "See all the books", "printAllBooks");
        initButton(findButton, "See a book", "findBook");
        initButton(printRecordButton, "See activity record", "printActivityRecord");
    }

    private void initButton(JButton button, String buttonLable, String actionCommand) {
        button = new JButton(buttonLable);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
    }

    private void getWeatherInfo() {
        weather.displayWeather();
    }

    private void loadFromFile() throws IOException {
        Scanner inFile = new Scanner(new FileInputStream("data/library.txt"));
        library.load(inFile);
        inFile.close();
    }

    private void saveToFile() throws IOException {
        FileWriter outFile = new FileWriter(new File("data/library.txt"));
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
        System.out.println("[3] Return a book");
        System.out.println("[4] See all the books");
        System.out.println("[5] See a book");
        System.out.println("[6] See activity record");
        System.out.println("[q] Quit");
    }

    // EFFECTS: call the appropriate functionality corresponding to the command passed in
    private void processCommand(String command) {
        if (command.equals("1")) {
            addBook();
        } else if (command.equals("2")) {
            loanBook();
        } else if (command.equals("3")) {
            returnBook();
        } else if (command.equals("4")) {
            printAllBooks();
        } else if (command.equals("5")) {
            findBook();
        } else if (command.equals("6")) {
            printActivityRecord();
        } else {
            System.out.println("You have entered an invalid command.\n");
        }
    }

    // EFFECTS: print the activity record of the library
    private void printActivityRecord() {
        ActivityRecord record = library.getActivityRecord();
        Set<Calendar> dateSet = record.getSetOfDate();
        if (dateSet.size() == 0) {
            System.out.println("No activity has been recorded");
            return;
        }
        for (Calendar date : dateSet) {
            System.out.println(ActivityRecord.DATE_FORMAT.format(date.getTime()) + ":");
            List<String> dateRecord = record.getActivityByDate(date);
            for (String s : dateRecord) {
                System.out.println("    " + s);
            }
        }
        System.out.print("\n");
    }

    // EFFECTS: add a new book to availableBooks
    private void addBook() {
        Book newBook = getBookInfo();
        try {
            library.addBook(newBook, Clock.systemDefaultZone());
            System.out.println("This book has successfully been added.\n");
        } catch (NullBookException e) {
            System.out.println("No book has been specified.\n");
        }
    }

    // EFFECTS: return a new Book with the valid inputted info of the book;
    // otherwise return null
    private Book getBookInfo() {
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the book's author: ");
        String author = scanner.nextLine();
        System.out.print("Is it a rare book? Press 'y' for yes. Press any other key for no. ");
        Book book = null;
        try {
            if (scanner.nextLine().equals("y")) {
                book = new RareBook(title, author);
            } else {
                book = new RegularBook(title, author);
            }
        } catch (EmptyStringException e) {
            System.out.println("Title or author cannot be empty.");
        } finally {
            return book;
        }
    }

    // EFFECTS: if the inputted title matches the title of an available book,
    // get the borrower's info and loan the book; otherwise do nothing
    private void loanBook() {
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        Person borrower = getBorrowerInfo();
        try {
            library.loanBook(title, borrower, Clock.systemDefaultZone());
            System.out.println("The book has successfully been loaned.\n");
        } catch (NullPersonException e) {
            System.out.println("No borrower has been specified.\n");
        } catch (BookNotAvailableException e) {
            System.out.println("This book is not available.\n");
        } catch (NullBookException e) {
            System.out.println("No book has been specified.\n");
        } catch (AlreadyBorrowException e) {
            System.out.println("This person has already borrowed a book.\n");
        }
    }

    private void returnBook() {
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        Person borrower = getBorrowerInfo();
        try {
            library.returnBook(title, borrower, Clock.systemDefaultZone());
            System.out.println("The book has successfully been returned.\n");
        } catch (NullPersonException e) {
            System.out.println("No borrower has been specified.\n");
        } catch (BookNotAvailableException e) {
            System.out.println("This book cannot be returned.\n");
        } catch (NullBookException e) {
            System.out.println("No book has been specified.\n");
        }
    }

    // EFFECTS: print the information of all the books in the library
    private void printAllBooks() {
        if (library.numOfBooks() == 0) {
            System.out.println("There is no book in your library.\n");
            return;
        }
        List<Book> books = library.getAvailableBooks();
        for (Book book : books) {
            System.out.println(book);
        }
        books = library.getLoanedBooks();
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // EFFECTS: print all the books whose titles matches the inputted title
    private void findBook() {
        System.out.print("Enter the book's title: ");
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

    // EFFECTS: return a new Person with the valid inputted info of the borrower;
    // otherwise return null
    private Person getBorrowerInfo() {
        System.out.print("Enter the information of the borrower:\nName: ");
        String name = scanner.nextLine();
        System.out.print("Phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Is this person a friend? Press 'y' for yes. Press any other key for no. ");
        try {
            if (scanner.nextLine().toLowerCase().equals("y")) {
                return new Friend(name, phoneNumber, email);
            } else {
                return new RegularPerson(name, phoneNumber, email);
            }
        } catch (EmptyStringException e) {
            System.out.println("Name, phone number or email cannot be empty.");
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
