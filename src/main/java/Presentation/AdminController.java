package Presentation;

import Entity.Book;
import Entity.Cd;
import Entity.MediaItem;
import Entity.User;
import Repository.BookRepository;
import Repository.CdRepository;
import Repository.UserRepository;
import Service.BookService.AddBookService;
import Service.BookService.BookCountService;
import Service.CDService.AddCdService;
import Service.CDService.CdCountService;
import Service.LogoutService;
import Service.MediaItem.OverdueBorrowNotifier.OverdueBorrowNotifier;
import Service.UserService.AddUserService;
import Service.UserService.UserCountService;
import Session.ISessionManager;
import Session.LocalSessionManager;
import Util.FxmlNavigator.FxmlNavigator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import Enum.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import Enum.MediaItemType;

import java.util.Date;

@Component
public class AdminController {

    @FXML
    private AnchorPane addProduct;

    @FXML
    private AnchorPane addUserPage;

    @FXML
    private AnchorPane adminMainPage;

    @FXML
    private AnchorPane addBookPage;

    @FXML
    private AnchorPane addCdPage;

    @FXML
    private Label adminNameDisplay;

    @FXML
    private AnchorPane sendEmailPage;

    @FXML
    private AnchorPane viewItemPage;

    @FXML
    private TextField addUserEmail;
    @FXML
    private TextField addUserName;
    @FXML
    private TextField addUserPassword;

    @FXML
    private TextField addBookIsbn;
    @FXML
    private TextField addBookAuthor;
    @FXML
    private TextField addBookTitle;



    @FXML
    private TextField addUserRole;

    @FXML
    private TextField emailMessage;

    @FXML
    private TableColumn<?, ?> employeeFirstNameColumn;

    @FXML
    private TableColumn<?, ?> employeeFirstNameColumn1;

    @FXML
    private TableColumn<?, ?> employeeIdColumn;

    @FXML
    private TextField itemSearch;

    @FXML
    private TableView<MediaItem> itemTable;

    @FXML
    private TableColumn<MediaItem, Integer> idColumn;

    @FXML
    private TableColumn<MediaItem, String> titleColumn;

    @FXML
    private TableColumn<MediaItem, Boolean> borrowedColumn;

    @FXML
    private TableColumn<MediaItem, String> borrowerColumn;

    @FXML
    private TableColumn<MediaItem, Date> borrowedDateColumn;

    @FXML
    private TableColumn<MediaItem, Date> dueDateColumn;

    @FXML
    private TableColumn<MediaItem, String> mediaTypeColumn;

    @FXML
    private Label numberOfBooks;

    @FXML
    private Label numberOfCds;

    @FXML
    private Label numberOfCustomers;

    @FXML
    private Button pageHome;
    @FXML
    private TextField addCdTitle;
    @FXML
    private Label errorLabelAddBook;
    @FXML
    private Label errorLabelAddCd;
    @FXML
    private ComboBox<?> searchList;

    @FXML
    private TableColumn<?, ?> selectEmployeeComlumn;

    private final LogoutService logoutService;
    private final FxmlNavigator fxmlNavigator;

    private BookCountService bookCountService;
    private CdCountService cdCountService;
    private UserCountService userCountService;
    private AddUserService addUserService;
    private LocalSessionManager sessionManager;
    private AddCdService addCdService;
    private AddBookService addBookService;

    @Autowired
    public AdminController(LogoutService logoutService, FxmlNavigator fxmlNavigator,
                           BookCountService bookCountService, CdCountService cdCountService,
                           UserCountService userCountService,
                           AddUserService addUserService,
                           LocalSessionManager sessionManager,
                           AddBookService addBookService,
                           AddCdService addCdService



    )
    {
        this.logoutService = logoutService;
        this.fxmlNavigator = fxmlNavigator;
        this.bookCountService = bookCountService;
        this.cdCountService = cdCountService;
        this.userCountService = userCountService;
        this.addUserService = addUserService;
        this.sessionManager = sessionManager;
        this.addBookService = addBookService;
        this.addCdService = addCdService;


    }






    @FXML
    private void initialize() {
        updateBookCount();
        updateCDCount();
        updateCustomersCount();
        setUserNameDisplay ();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        borrowedColumn.setCellValueFactory(new PropertyValueFactory<>("borrowed"));

        borrowerColumn.setCellValueFactory(cellData -> {
            User borrower = cellData.getValue().getBorrower();
            return new SimpleStringProperty(borrower != null ? borrower.getName() : "None");
        });
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        mediaTypeColumn.setCellValueFactory(cellData -> {
            MediaItemType mediaType = cellData.getValue().getMediaType();
            return new SimpleStringProperty(mediaType != null ? mediaType.name() : "Unknown");
        });
    }
    private void updateBookCount() {
        long bookCount = bookCountService.countBooks();
        numberOfBooks.setText(String.valueOf(bookCount));
    }
    private void updateCDCount(){
        long cdCount = cdCountService.countCds();
        numberOfCds.setText(String.valueOf(cdCount));

    }
    private void setUserNameDisplay (){
        adminNameDisplay.setText(sessionManager.getUser().getName());
    }
    private void updateCustomersCount(){
        long userCount = userCountService.countUsersByRole(UserRole.User);
        numberOfCustomers.setText(String.valueOf(userCount));
    }

        void setAllVisibleFalse(){
        addProduct.setVisible(false);
        addUserPage.setVisible(false);
        adminMainPage.setVisible(false);
        sendEmailPage.setVisible(false);
        viewItemPage.setVisible(false);
        addBookPage.setVisible(false);
        addCdPage.setVisible(false);

    }


    @FXML
    void searchButton(ActionEvent event) {

    }

    @FXML
    void addBookSaveButton(ActionEvent event) {
        String title = addBookTitle.getText();
        String author = addBookAuthor.getText();
        String isbn = addBookIsbn.getText();
        Book book = new Book(title, isbn, author);
        addBookService.addBook(book);
        System.out.println("Book added: " + title + ", " + author + ", " + isbn);
        errorLabelAddBook.setText("Book added successfully!");
        addBookTitle.clear();
        addBookAuthor.clear();
        addBookIsbn.clear();
    }


    @FXML
    void addBookPageButton(ActionEvent event) {
        setAllVisibleFalse();
        addBookPage.setVisible(true);

    }

    @FXML
    void addUserPageButton(ActionEvent event) {
        setAllVisibleFalse();
        addUserPage.setVisible(true);
    }

    @FXML
    void homePageButton(ActionEvent event) {
        setAllVisibleFalse();
        updateBookCount();
        updateCDCount();
        updateCustomersCount();
        adminMainPage.setVisible(true);
    }

    @FXML
    void itemSearchMethod(ActionEvent event) {

    }

    @FXML
    void logoutButton(ActionEvent event) {
        logoutService.logout();
        System.out.println("Logout button clicked");
        fxmlNavigator.logout((javafx.stage.Stage) pageHome.getScene().getWindow(), "/fxml/Login.fxml");

    }

    @FXML
    void saveUserButton(ActionEvent event) {
        String name = addUserName.getText();
        String email = addUserEmail.getText();
        String password = addUserPassword.getText();
        String roleString = addUserRole.getText();
        UserRole role;
        try {
            role = UserRole.valueOf(roleString);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role: " + roleString);
            errorLabelAddBook.setText("Invalid role: " + roleString);
            return;
        }
        addUserService.addUser(name, email, password, role);
        System.out.println("User added: " + name + ", " + email + ", " + role);
        errorLabelAddBook.setText("User added successfully!");
        addUserName.clear();
        addUserEmail.clear();
        addUserPassword.clear();
        addUserRole.clear();

    }

    @FXML
    void sendEmail(ActionEvent event) {

    }

    @FXML
    void sendEmailPageButton(ActionEvent event) {
        setAllVisibleFalse();
        sendEmailPage.setVisible(true);
    }

    @FXML
    void sendOverDueButton(ActionEvent event) {

    }

    @FXML
    void viewAllButton(ActionEvent event) {

    }

    @FXML
    void viewItemPageButton(ActionEvent event) {
        setAllVisibleFalse();
        viewItemPage.setVisible(true);
    }
    @FXML
    void addCdPageButton(ActionEvent event) {
        setAllVisibleFalse();
        addCdPage.setVisible(true);
    }
    @FXML
    void addCdSaveButton(ActionEvent event) {
        String title = addCdTitle.getText();
        Cd cd = new Cd(title);
        addCdService.addCd(cd);
        System.out.println("CD added: " + title);
        errorLabelAddCd.setText("CD added successfully!");
        addCdTitle.clear();

    }


}
