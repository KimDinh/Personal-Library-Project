package ui;

import model.Book;
import java.util.*;

public class Main {
    private Scanner scanner;
    private ArrayList<Book> availableBooks;
    private ArrayList<Book> loanedBooks;

    public Main(){
        availableBooks =  new ArrayList<>();
        loanedBooks = new ArrayList<>();
        scanner = new Scanner(System.in);
        takeUserCommand();
    }

    private void takeUserCommand(){
        sayHello();
        while(true){
            helpMenu();
            String command = scanner.nextLine();
            if(command.equals("q"))
                break;
            processCommand(command);
        }
    }

    private void sayHello(){
        System.out.println("Hello! This is a personal library application.");
    }

    private void helpMenu(){
        System.out.println("Please enter what you would like to do:");
        System.out.println("[1] Add a book");
        System.out.println("[2] Loan a book");
        System.out.println("[3] See all the books");
        System.out.println("[q] Quit");
    }

    private void processCommand(String command){
        switch (command){
            case "1":   addBook();
                        break;
            case "2":   loanBook();
                        break;
            case "3":   printAllBooks();
                        break;
            default:    System.out.println("You have entered an invalid command.\n");
                        break;
        }
    }

    private void addBook(){
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the book's author: ");
        String author = scanner.nextLine();
        Book newBook = new Book(title, author);
        availableBooks.add(newBook);
        System.out.println("You have successfully added the book.\n");
    }

    private void loanBook(){
        System.out.print("Enter the book's title: ");
        String title = scanner.nextLine();
        for(Book book : availableBooks){
            if(book.getTitle().equals(title)){
                availableBooks.remove(book);
                book.changeAvalability();
                loanedBooks.add(book);
                System.out.println("The book has successfully been loaned.\n");
                return;
            }
        }
        System.out.println("This book is not available.\n");
    }

    private void printAllBooks(){
        if(availableBooks.size()==0 && loanedBooks.size()==0){
            System.out.println("There is no book in your library.\n");
        }
        for(int i=0; i<availableBooks.size(); i++)
            System.out.println(availableBooks.get(i));
        for(int i=0; i<loanedBooks.size(); i++)
            System.out.println(loanedBooks.get(i));
    }

    public static void main(String[] args) { new Main(); }
}
