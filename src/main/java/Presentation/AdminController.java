package Presentation;

import Entity.Book;
import Entity.Cd;
import Entity.MediaItem;
import Entity.User;
import Repository.BookRepository;
import Repository.CdRepository;
import Repository.UserRepository;
import Service.AdminBroadcastNotifierService.AdminBroadcastNotifier;
import Service.BookService.AddBookService;
import Service.BookService.AllBookService;
import Service.BookService.BookCountService;
import Service.BroadcastMessage.AdminBroadcastMessageData;
import Service.CDService.AddCdService;
import Service.CDService.AllCdService;
import Service.CDService.CdCountService;
import Service.LogoutService;
import Service.MediaItem.MediaItemSearchService;
import Service.MediaItem.OverdueBorrowNotifier.OverdueBorrowNotifier;
import Service.UserService.AddUserService;
import Service.UserService.GetAllUsersService;
import Service.UserService.UnregisterUserService;
import Service.UserService.UserCountService;
import Session.ISessionManager;
import Session.LocalSessionManager;
import Util.FxmlNavigator.FxmlNavigator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import Enum.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static Enum.UserRole.User;

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
    private Label errorMessagesForSendEmail;

    @FXML
    private AnchorPane sendEmailMassagePage;

    @FXML
    private AnchorPane viewItemPage;
    @FXML
    private AnchorPane unregisterPage;
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
    private TextField itemSearch;


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
    private Label unregisterLable;

    @FXML
    private ComboBox<String> searchList;
    @FXML
    private ComboBox<String> usersNamesCombo;

    @FXML private TableColumn<MediaItem, Integer> viewId;
    @FXML private TableColumn<MediaItem, String> viewTitle;
    @FXML private TableColumn<MediaItem, Boolean> viewIsBorrowd;
    @FXML private TableColumn<MediaItem, String> viewUser;
    @FXML private TableColumn<MediaItem, java.util.Date> viewBorrowedDate;
    @FXML private TableColumn<MediaItem, java.util.Date> viewDueToDate;
    @FXML private TableColumn<MediaItem, String> viewAuthor;
    @FXML private TableColumn<MediaItem, String> viewIsbn;
    @FXML private TableColumn<MediaItem, String> viewType;
    @FXML
    private TableView<MediaItem> itemTable;

    private final LogoutService logoutService;
    private final FxmlNavigator fxmlNavigator;

    private BookCountService bookCountService;
    private CdCountService cdCountService;
    private UserCountService userCountService;
    private AddUserService addUserService;
    private LocalSessionManager sessionManager;
    private AddCdService addCdService;
    private AddBookService addBookService;
    private AllBookService allBookService;
    private AllCdService allCdService;
    private OverdueBorrowNotifier overdueBorrowNotifier;
    private AdminBroadcastNotifier adminBroadcastNotifier;
    private MediaItemSearchService mediaItemSearchService;
    private UnregisterUserService unregisterUserService;
    private GetAllUsersService getAllUsersService;
    @Autowired
    public AdminController(LogoutService logoutService, FxmlNavigator fxmlNavigator,
                           BookCountService bookCountService, CdCountService cdCountService,
                           UserCountService userCountService,
                           AddUserService addUserService,
                           LocalSessionManager sessionManager,
                           AddBookService addBookService,
                           AddCdService addCdService,
                            AllBookService allBookService,
                            AllCdService allCdService,
                            OverdueBorrowNotifier overdueBorrowNotifier,
                            AdminBroadcastNotifier adminBroadcastNotifier,
                            MediaItemSearchService mediaItemSearchService,
                            UnregisterUserService unregisterUserService,
                            GetAllUsersService getAllUsersService
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
        this.allBookService = allBookService;
        this.allCdService = allCdService;
        this.overdueBorrowNotifier = overdueBorrowNotifier;
        this.adminBroadcastNotifier = adminBroadcastNotifier;
        this.mediaItemSearchService = mediaItemSearchService;
        this.unregisterUserService = unregisterUserService;
        this.getAllUsersService = getAllUsersService;



    }






    @FXML
    private void initialize() {
        updateBookCount();
        updateCDCount();
        updateCustomersCount();
        setUserNameDisplay ();
        setupViewTable();
        if (searchList != null) {
            searchList.setItems(FXCollections.observableArrayList(
                    "Title (Books & CDs)",
                    "Book Author",
                    "Book ISBN"
            ));
            searchList.setValue("Title (Books & CDs)");
        }
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
        long userCount = userCountService.countUsersByRole(User);
        numberOfCustomers.setText(String.valueOf(userCount));
    }


    private void setupViewTable() {
        if (itemTable == null) return;

        viewId.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getId()));

        viewTitle.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));

        if (viewAuthor != null) {
            viewAuthor.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book) {
                    String author = book.getAuthor();
                    return new SimpleStringProperty(author != null ? author : "");
                }
                return new SimpleStringProperty("");
            });
        }
        if (viewIsbn != null) {
            viewIsbn.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book) {
                    String isbn = book.getIsbn();
                    return new SimpleStringProperty(isbn != null ? isbn : "");
                }
                return new SimpleStringProperty("");
            });
        }

        viewIsBorrowd.setCellValueFactory(cellData ->
                new SimpleBooleanProperty(cellData.getValue().isBorrowed()));

        viewUser.setCellValueFactory(cellData -> {
            var borrower = cellData.getValue().getBorrower();
            String name = (borrower != null) ? borrower.getName() : "";
            return new SimpleStringProperty(name);
        });

        viewBorrowedDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getBorrowedDate()));

        viewDueToDate.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getDueDate()));

        if (viewType != null) {
            viewType.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getMediaType().name()));
        }

        if (usersNamesCombo != null) {
            var users = getAllUsersService.getAllUsers()
                    .stream()
                    .filter(u -> u.getUserRole() == UserRole.User)
                    .map(Entity.User::getName)
                    .toList();
            usersNamesCombo.setItems(FXCollections.observableArrayList(users));
        }

    }

















        void setAllVisibleFalse(){
        addUserPage.setVisible(false);
        adminMainPage.setVisible(false);
        sendEmailMassagePage.setVisible(false);
        viewItemPage.setVisible(false);
        addBookPage.setVisible(false);
        addCdPage.setVisible(false);
        unregisterPage.setVisible(false);

    }


    @FXML
    void searchButton(ActionEvent event) {
        String keyword = itemSearch.getText();
        String mode = searchList.getValue();

        var items = mediaItemSearchService.searchByMode(mode, keyword);
        itemTable.setItems(FXCollections.observableArrayList(items));
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
        String message = emailMessage.getText();
        System.out.println("Email message to be sent: " + message);
        errorMessagesForSendEmail.setText("Broadcast email sent successfully!");
        adminBroadcastNotifier.sendToAll(message);
        emailMessage.clear();
        System.out.println("Broadcast email sent.");
    }

    @FXML
    void sendEmailMassagePageButton(ActionEvent event) {
        setAllVisibleFalse();
        sendEmailMassagePage.setVisible(true);
    }

    @FXML
    void sendOverDueButton(ActionEvent event) {
        overdueBorrowNotifier.send();
        System.out.println("Overdue notifications sent.");
    }

    @FXML
    void viewAllButton(ActionEvent event) {
        setAllVisibleFalse();
        viewItemPage.setVisible(true);
        var allItems = new java.util.ArrayList<MediaItem>();
        allItems.addAll(allBookService.getAllBooks());
        allItems.addAll(allCdService.getAllCds());
        ObservableList<MediaItem> observableItems = FXCollections.observableArrayList(allItems);
        itemTable.setItems(observableItems);

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
    @FXML
    void unregisterUserPageButton (ActionEvent event) {
        setAllVisibleFalse();
        unregisterPage.setVisible(true);

    }
    @FXML
    void unregisterButton(ActionEvent event) {
        Object selected = usersNamesCombo.getValue();
        if (selected == null) {
            unregisterLable.setText("Please select a user to unregister.");
            unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
            return;
        }

        String userName = selected.toString();
        try {
            var userOpt = getAllUsersService.getAllUsers().stream()
                    .filter(u -> u.getName().equalsIgnoreCase(userName))
                    .findFirst();

            if (userOpt.isEmpty()) {
                unregisterLable.setText("User not found: " + userName);
                unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                return;
            }

            unregisterUserService.unregisterUser(userOpt.get());
            unregisterLable.setText("User '" + userName + "' unregistered successfully!");
            unregisterLable.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
            usersNamesCombo.getItems().remove(userName);

        } catch (IllegalStateException e) {
            unregisterLable.setText("Cannot unregister user: " + e.getMessage());
            unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
        } catch (Exception e) {
            unregisterLable.setText("Unexpected error occurred while unregistering.");
            unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
            e.printStackTrace();
        }
    }




}
