package ui;

import exceptions.*;
import model.*;
import network.WeatherInfo;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.Clock;
import java.util.*;
import java.util.List;

public class Main extends JFrame implements ActionListener {
    private Library library;
    private WeatherInfo weather;
    private JPanel panelContainer;
    private HomePanel homePanel;
    private AddBookPanel addBookPanel;
    private LoanBookPanel loanBookPanel;
    private ReturnBookPanel returnBookPanel;
    private FindBookPanel findBookPanel;
    private PrintStatPanel printStatPanel;

    public static void main(String[] args) throws IOException {
        Main libraryApp = new Main();
        libraryApp.getWeatherInfo();
        libraryApp.sayHello();
    }

    public Main() throws IOException {
        super("My Library");
        library = new Library();
        weather = new WeatherInfo();
        loadFromFile();
        initGUI();
        saveToFile();
    }

    private void initGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 600));
        initAllPanel();
        add(panelContainer);
        panelContainer.add(addBookPanel, PanelName.ADD_BOOK_PANEL.getName());
        pack();
        showPanel("HomePanel");
        setVisible(true);
        setResizable(false);
    }

    private void initAllPanel() {
        panelContainer = new JPanel(new CardLayout());
        initHomePanel();
        panelContainer.add(homePanel, PanelName.HOME_PANEL.getName());
        initAddBookPanel();
        panelContainer.add(addBookPanel, PanelName.ADD_BOOK_PANEL.getName());
        initLoanBookPanel();
        panelContainer.add(loanBookPanel, PanelName.LOAN_BOOK_PANEL.getName());
        initReturnBookPanel();
        panelContainer.add(returnBookPanel, PanelName.RETURN_BOOK_PANEL.getName());
        initFindBookPanel();
        panelContainer.add(findBookPanel, PanelName.FIND_BOOK_PANEL.getName());
        initPrintStatPanel();
        panelContainer.add(printStatPanel, PanelName.PRINT_STAT_PANEL.getName());
    }

    private void initAddBookPanel() {
        addBookPanel = new AddBookPanel();
        setButtonListened(addBookPanel.getButtons());
    }

    private void initLoanBookPanel() {
        loanBookPanel = new LoanBookPanel();
        setButtonListened(loanBookPanel.getButtons());
    }

    private void initReturnBookPanel() {
        returnBookPanel = new ReturnBookPanel();
        setButtonListened(returnBookPanel.getButtons());
    }

    private void initFindBookPanel() {
        findBookPanel = new FindBookPanel();
        setButtonListened(findBookPanel.getButtons());
    }

    private void initPrintStatPanel() {
        printStatPanel = new PrintStatPanel();
        setButtonListened(printStatPanel.getButtons());
    }

    private void initHomePanel() {
        homePanel = new HomePanel();
        setButtonListened(homePanel.getButtons());
    }

    private void setButtonListened(List<JButton> buttons) {
        for (JButton button : buttons) {
            button.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        clickSound();
        if (command.equals(ButtonAction.ADD_BOOK.getAction())) {
            addBook();
        } else if (command.equals(ButtonAction.LOAN_BOOK.getAction())) {
            loanBook();
        } else if (command.equals(ButtonAction.RETURN_BOOK.getAction())) {
            returnBook();
        } else if (command.equals(ButtonAction.FIND_BOOK.getAction())) {
            findBook();
        } else {
            changePanel(command);
        }
    }

    private void changePanel(String command) {
        if (command.equals(ButtonAction.SHOW_ADD_BOOK_PANEL.getAction())) {
            showPanel(PanelName.ADD_BOOK_PANEL.getName());
        } else if (command.equals(ButtonAction.SHOW_LOAN_BOOK_PANEL.getAction())) {
            showPanel(PanelName.LOAN_BOOK_PANEL.getName());
        } else if (command.equals(ButtonAction.SHOW_RETURN_BOOK_PANEL.getAction())) {
            showPanel(PanelName.RETURN_BOOK_PANEL.getName());
        } else if (command.equals(ButtonAction.SHOW_FIND_BOOK_PANEL.getAction())) {
            showPanel(PanelName.FIND_BOOK_PANEL.getName());
        } else if (command.equals(ButtonAction.SHOW_PRINT_ALL_BOOKS_PANEL.getAction())) {
            showPanel(PanelName.PRINT_STAT_PANEL.getName());
            printAllBooks();
        } else if (command.equals(ButtonAction.SHOW_PRINT_RECORD_PANEL.getAction())) {
            showPanel(PanelName.PRINT_STAT_PANEL.getName());
            printActivityRecord();
        } else if (command.equals(ButtonAction.BACK.getAction())) {
            resetTextDisplay();
            showPanel(PanelName.HOME_PANEL.getName());
        }
    }

    private void resetTextDisplay() {
        addBookPanel.getTextDisplay().setText("");
        loanBookPanel.getTextDisplay().setText("");
        returnBookPanel.getTextDisplay().setText("");
        findBookPanel.getTextDisplay().setText("");
        printStatPanel.getTextDisplay().setText("");
    }

    private void showPanel(String panelName) {
        CardLayout cardLayout = (CardLayout) panelContainer.getLayout();
        cardLayout.show(panelContainer, panelName);
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

    private void sayHello() {
        System.out.println("Hello! This is a personal library application.");
    }

    // EFFECTS: print the activity record of the library
    private void printActivityRecord() {
        ActivityRecord record = library.getActivityRecord();
        Set<Calendar> dateSet = record.getSetOfDate();
        if (dateSet.size() == 0) {
            printStatPanel.getTextDisplay().setText("No activity has been recorded");
            return;
        }
        String toPrint = "";
        for (Calendar date : dateSet) {
            toPrint = toPrint + ActivityRecord.DATE_FORMAT.format(date.getTime()) + ":\n";
            List<String> dateRecord = record.getActivityByDate(date);
            for (String s : dateRecord) {
                toPrint = toPrint + "    " + s + "\n";
            }
            toPrint += "\n";
        }
        printStatPanel.getTextDisplay().setText(convertMultiline(toPrint));
    }

    // EFFECTS: add a new book to availableBooks
    private void addBook() {
        try {
            Book newBook = getBookInfo(addBookPanel.getTitleField(), addBookPanel.getAuthorField(),
                    addBookPanel.getRareBookCheckBox());
            library.addBook(newBook, Clock.systemDefaultZone());
            addBookPanel.getTextDisplay().setText("This book has successfully been added.");
        } catch (EmptyStringException e) {
            addBookPanel.getTextDisplay().setText("Title or author cannot be empty.");
        } catch (NullBookException e) {
            addBookPanel.getTextDisplay().setText("No book has been specified.");
        }
    }

    private Book getBookInfo(JTextField titleField, JTextField authorField, JCheckBox rareBookCheckbox)
            throws EmptyStringException {
        String title = titleField.getText();
        String author = authorField.getText();
        boolean isRareBook = rareBookCheckbox.isSelected();
        titleField.setText("");
        authorField.setText("");
        rareBookCheckbox.setSelected(false);
        Book newBook;
        if (isRareBook) {
            newBook = new RareBook(title, author);
        } else {
            newBook = new RegularBook(title, author);
        }
        return newBook;
    }

    // EFFECTS: if the inputted title matches the title of an available book,
    // get the borrower's info and loan the book; otherwise do nothing
    private void loanBook() {
        String title = loanBookPanel.getTitleField().getText();
        loanBookPanel.getTitleField().setText("");
        try {
            Person borrower = getBorrowerInfo(loanBookPanel.getNameField(), loanBookPanel.getPhoneNumberField(),
                    loanBookPanel.getEmailField(), loanBookPanel.getFriendCheckBox());
            library.loanBook(title, borrower, Clock.systemDefaultZone());
            loanBookPanel.getTextDisplay().setText("The book has successfully been loaned.");
        } catch (EmptyStringException e) {
            loanBookPanel.getTextDisplay().setText("Name, phone number and email cannot be empty.");
        } catch (NullPersonException e) {
            loanBookPanel.getTextDisplay().setText("No borrower has been specified.");
        } catch (BookNotAvailableException e) {
            loanBookPanel.getTextDisplay().setText("This book is not available.");
        } catch (NullBookException e) {
            loanBookPanel.getTextDisplay().setText("No book has been specified.");
        } catch (AlreadyBorrowException e) {
            loanBookPanel.getTextDisplay().setText("This person has already borrowed a book.");
        }
    }

    private void returnBook() {
        String title = returnBookPanel.getTitleField().getText();
        returnBookPanel.getTitleField().setText("");
        try {
            Person borrower = getBorrowerInfo(returnBookPanel.getNameField(), returnBookPanel.getPhoneNumberField(),
                    returnBookPanel.getEmailField(), returnBookPanel.getFriendCheckBox());
            library.returnBook(title, borrower, Clock.systemDefaultZone());
            returnBookPanel.getTextDisplay().setText("The book has successfully been returned.");
        } catch (EmptyStringException e) {
            returnBookPanel.getTextDisplay().setText("Name, phone number and email cannot be empty.");
        } catch (NullPersonException e) {
            returnBookPanel.getTextDisplay().setText("No borrower has been specified.");
        } catch (BookNotAvailableException e) {
            returnBookPanel.getTextDisplay().setText("This book cannot be returned.");
        } catch (NullBookException e) {
            returnBookPanel.getTextDisplay().setText("No book has been specified.");
        }
    }

    // EFFECTS: print the information of all the books in the library
    private void printAllBooks() {
        if (library.numOfBooks() == 0) {
            printStatPanel.getTextDisplay().setText("There is no book in your library.");
            return;
        }
        String toPrint = "";
        List<Book> books = library.getAvailableBooks();
        for (Book book : books) {
            toPrint = toPrint + book.toString() + "\n";
        }
        books = library.getLoanedBooks();
        for (Book book : books) {
            toPrint = toPrint + book.toString() + "\n";
        }
        printStatPanel.getTextDisplay().setText(convertMultiline(toPrint));
    }

    // EFFECTS: print all the books whose titles matches the inputted title
    private void findBook() {
        String title = findBookPanel.getTitleField().getText();
        findBookPanel.getTitleField().setText("");
        Book bookAvailable = library.findInAvailable(title);
        Book bookLoaned = library.findInLoaned(title);
        if (bookAvailable == null && bookLoaned == null) {
            findBookPanel.getTextDisplay().setText("This book is not in the library.");
        } else if (bookAvailable != null) {
            findBookPanel.getTextDisplay().setText(convertMultiline(bookAvailable.toString()));
        } else {
            findBookPanel.getTextDisplay().setText(convertMultiline(bookLoaned.toString()
                    + "\n\nBorrower's information:\n" + bookLoaned.getBorrower().toString()));
        }
    }

    // EFFECTS: return a new Person with the valid inputted info of the borrower;
    // otherwise return null
    private Person getBorrowerInfo(JTextField nameField, JTextField phoneNumberField, JTextField emailField,
                                   JCheckBox friendCheckbox) throws EmptyStringException {
        String name = nameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String email = emailField.getText();
        boolean isFriend = friendCheckbox.isSelected();
        nameField.setText("");
        phoneNumberField.setText("");
        emailField.setText("");
        friendCheckbox.setSelected(false);
        Person newPerson;
        if (isFriend) {
            newPerson = new Friend(name, phoneNumber, email);
        } else {
            newPerson = new RegularPerson(name, phoneNumber, email);
        }
        return newPerson;
    }

    private String convertMultiline(String s) {
        return "<html>" + s.replaceAll("\n", "<br>") + "</html>";
    }

    private void clickSound() {
        try {
            File clickSoundFile = new File("data/click_sound.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(clickSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
