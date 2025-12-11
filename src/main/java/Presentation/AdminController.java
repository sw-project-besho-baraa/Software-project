package Presentation;

import Entity.Book;
import Entity.Cd;
import Entity.MediaItem;
import Service.AdminBroadcastNotifierService.AdminBroadcastNotifier;
import Service.BookService.BooksService;
import Service.CDService.AllCdService;
import Service.CDService.CdCountService;
import Service.LogoutService;
import Service.MediaItem.MediaItemSearchService;
import Service.MediaItem.MediaItemService;
import Service.OverdueBorrowNotifier.OverdueBorrowNotifier;
import Service.UserService.AddUserService;
import Service.UserService.GetAllUsersService;
import Service.UserService.UnregisterUserService;
import Service.UserService.UserCountService;
import Session.LocalSessionManager;
import Util.FxmlNavigator.FxmlNavigator;
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
import Enum.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static Enum.UserRole.User;

/**
 * Controller for the admin dashboard.
 * <p>
 * Handles navigation, user management, media items, counts and notifications.
 */
@Component
public class AdminController
{

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

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

    @FXML
    private TableColumn<MediaItem, Integer> viewId;

    @FXML
    private TableColumn<MediaItem, String> viewTitle;

    @FXML
    private TableColumn<MediaItem, Boolean> viewIsBorrowd;

    @FXML
    private TableColumn<MediaItem, String> viewUser;

    @FXML
    private TableColumn<MediaItem, LocalDateTime> viewBorrowedDate;

    @FXML
    private TableColumn<MediaItem, LocalDateTime> viewDueToDate;

    @FXML
    private TableColumn<MediaItem, String> viewAuthor;

    @FXML
    private TableColumn<MediaItem, String> viewIsbn;

    @FXML
    private TableColumn<MediaItem, String> viewType;

    @FXML
    private TableView<MediaItem> itemTable;

    private final LogoutService logoutService;
    private final FxmlNavigator fxmlNavigator;

    private BooksService bookCountService;
    private CdCountService cdCountService;
    private UserCountService userCountService;
    private AddUserService addUserService;
    private LocalSessionManager sessionManager;
    private MediaItemService mediaItemService;
    private AllCdService allCdService;
    private OverdueBorrowNotifier overdueBorrowNotifier;
    private AdminBroadcastNotifier adminBroadcastNotifier;
    private MediaItemSearchService mediaItemSearchService;
    private UnregisterUserService unregisterUserService;
    private GetAllUsersService getAllUsersService;

    /**
     * Creates the admin controller with required services.
     *
     * @param logoutService
     *            handles logout operations
     * @param fxmlNavigator
     *            used to switch between FXML views
     * @param bookCountService
     *            service to count books
     * @param cdCountService
     *            service to count CDs
     * @param userCountService
     *            service to count users by role
     * @param addUserService
     *            service to add new users
     * @param sessionManager
     *            manages the current logged-in user
     * @param mediaItemService
     *            service for managing media items
     * @param allCdService
     *            service to load all CDs
     * @param overdueBorrowNotifier
     *            sends overdue notifications
     * @param adminBroadcastNotifier
     *            sends admin broadcast messages
     * @param mediaItemSearchService
     *            service to search media items
     * @param unregisterUserService
     *            service to unregister users
     * @param getAllUsersService
     *            service to fetch all users
     */
    @Autowired
    public AdminController(LogoutService logoutService, FxmlNavigator fxmlNavigator, BooksService bookCountService,
            CdCountService cdCountService, UserCountService userCountService, AddUserService addUserService,
            LocalSessionManager sessionManager, MediaItemService mediaItemService, AllCdService allCdService,
            OverdueBorrowNotifier overdueBorrowNotifier, AdminBroadcastNotifier adminBroadcastNotifier,
            MediaItemSearchService mediaItemSearchService, UnregisterUserService unregisterUserService,
            GetAllUsersService getAllUsersService)
    {
        this.logoutService = logoutService;
        this.fxmlNavigator = fxmlNavigator;
        this.bookCountService = bookCountService;
        this.cdCountService = cdCountService;
        this.userCountService = userCountService;
        this.addUserService = addUserService;
        this.sessionManager = sessionManager;
        this.mediaItemService = mediaItemService;
        this.allCdService = allCdService;
        this.overdueBorrowNotifier = overdueBorrowNotifier;
        this.adminBroadcastNotifier = adminBroadcastNotifier;
        this.mediaItemSearchService = mediaItemSearchService;
        this.unregisterUserService = unregisterUserService;
        this.getAllUsersService = getAllUsersService;
    }

    /**
     * Initializes the admin view after FXML is loaded.
     */
    @FXML
    private void initialize()
    {
        updateBookCount();
        updateCDCount();
        updateCustomersCount();
        setUserNameDisplay();
        setupViewTable();
        if (searchList != null)
        {
            searchList.setItems(FXCollections.observableArrayList("Title (Books & CDs)","Book Author","Book ISBN"));
            searchList.setValue("Title (Books & CDs)");
        }
    }

    /**
     * Updates the label showing total number of books.
     */
    private void updateBookCount()
    {
        long bookCount = bookCountService.countBooks();
        numberOfBooks.setText(String.valueOf(bookCount));
    }

    /**
     * Updates the label showing total number of CDs.
     */
    private void updateCDCount()
    {
        long cdCount = cdCountService.countCds();
        numberOfCds.setText(String.valueOf(cdCount));
    }

    /**
     * Sets the admin name label from the current session user.
     *
     * @see LocalSessionManager
     */
    private void setUserNameDisplay()
    {
        adminNameDisplay.setText(sessionManager.getUser().getName());
    }

    /**
     * Updates the label showing total number of customers.
     */
    private void updateCustomersCount()
    {
        long userCount = userCountService.countUsersByRole(User);
        numberOfCustomers.setText(String.valueOf(userCount));
    }

    /**
     * Configures the table columns and loads users for the unregister combo.
     */
    private void setupViewTable()
    {
        if (itemTable == null)
            return;

        viewId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));

        viewTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        if (viewAuthor != null)
        {
            viewAuthor.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book)
                {
                    String author = book.getAuthor();
                    return new SimpleStringProperty(author != null ? author : "");
                }
                return new SimpleStringProperty("");
            });
        }

        if (viewIsbn != null)
        {
            viewIsbn.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book)
                {
                    String isbn = book.getIsbn();
                    return new SimpleStringProperty(isbn != null ? isbn : "");
                }
                return new SimpleStringProperty("");
            });
        }

        viewIsBorrowd.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isBorrowed()));

        viewUser.setCellValueFactory(cellData -> {
            var borrower = cellData.getValue().getBorrower();
            String name = (borrower != null) ? borrower.getName() : "";
            return new SimpleStringProperty(name);
        });

        viewBorrowedDate
                .setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowedDate()));

        viewDueToDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDueDate()));

        if (viewType != null)
        {
            viewType.setCellValueFactory(
                    cellData -> new SimpleStringProperty(cellData.getValue().getMediaType().name()));
        }

        if (usersNamesCombo != null)
        {
            var users = getAllUsersService.getAllUsers().stream().filter(u -> u.getUserRole() == UserRole.User)
                    .map(Entity.User::getName).toList();
            usersNamesCombo.setItems(FXCollections.observableArrayList(users));
        }
    }

    /**
     * Hides all main content panes.
     */
    void setAllVisibleFalse()
    {
        addUserPage.setVisible(false);
        adminMainPage.setVisible(false);
        sendEmailMassagePage.setVisible(false);
        viewItemPage.setVisible(false);
        addBookPage.setVisible(false);
        addCdPage.setVisible(false);
        unregisterPage.setVisible(false);
    }

    /**
     * Searches items based on the selected mode and keyword.
     *
     * @param event
     *            action event from the search button
     */
    @FXML
    void searchButton(ActionEvent event)
    {
        String keyword = itemSearch.getText();
        String mode = searchList.getValue();
        var items = mediaItemSearchService.searchByMode(mode,keyword);
        itemTable.setItems(FXCollections.observableArrayList(items));
    }

    /**
     * Saves a new book from the add book form.
     *
     * @param event
     *            action event from the save button
     * @see Book
     */
    @FXML
    void addBookSaveButton(ActionEvent event)
    {
        String title = addBookTitle.getText();
        String author = addBookAuthor.getText();
        String isbn = addBookIsbn.getText();
        Book book = new Book(title, isbn, author);
        mediaItemService.addItem(book);
        logger.info("Book added: {}, {}, {}",title,author,isbn);
        errorLabelAddBook.setText("Book added successfully!");
        addBookTitle.clear();
        addBookAuthor.clear();
        addBookIsbn.clear();
    }

    /**
     * Shows the add book page.
     *
     * @param event
     *            action event
     */
    @FXML
    void addBookPageButton(ActionEvent event)
    {
        setAllVisibleFalse();
        addBookPage.setVisible(true);
    }

    /**
     * Shows the add user page.
     *
     * @param event
     *            action event
     */
    @FXML
    void addUserPageButton(ActionEvent event)
    {
        setAllVisibleFalse();
        addUserPage.setVisible(true);
    }

    /**
     * Shows the home page and refreshes counts.
     *
     * @param event
     *            action event
     */
    @FXML
    void homePageButton(ActionEvent event)
    {
        setAllVisibleFalse();
        updateBookCount();
        updateCDCount();
        updateCustomersCount();
        adminMainPage.setVisible(true);
    }

    /**
     * Placeholder for item search text field action.
     *
     * @param event
     *            action event
     */
    @FXML
    void itemSearchMethod(ActionEvent event)
    {
        // no-op
    }

    /**
     * Logs out the current admin user and returns to the login screen.
     *
     * @param event
     *            action event
     */
    @FXML
    void logoutButton(ActionEvent event)
    {
        logoutService.logout();
        logger.info("Logout button clicked");
        fxmlNavigator.logout((javafx.stage.Stage) pageHome.getScene().getWindow(),"/fxml/Login.fxml");
    }

    /**
     * Saves a new user based on form input.
     *
     * @param event
     *            action event
     * @see UserRole
     */
    @FXML
    void saveUserButton(ActionEvent event)
    {
        String name = addUserName.getText();
        String email = addUserEmail.getText();
        String password = addUserPassword.getText();
        String roleString = addUserRole.getText();
        UserRole role;
        try
        {
            role = UserRole.valueOf(roleString);
        } catch (IllegalArgumentException e)
        {
            logger.warn("Invalid role: {}",roleString);
            errorLabelAddBook.setText("Invalid role: " + roleString);
            return;
        }
        addUserService.addUser(name,email,password,role);
        logger.info("User added: {}, {}, {}",name,email,role);
        errorLabelAddBook.setText("User added successfully!");
        addUserName.clear();
        addUserEmail.clear();
        addUserPassword.clear();
        addUserRole.clear();
    }

    /**
     * Sends a broadcast email message to all users.
     *
     * @param event
     *            action event
     */
    @FXML
    void sendEmail(ActionEvent event)
    {
        String message = emailMessage.getText();
        logger.info("Email message to be sent: {}",message);
        errorMessagesForSendEmail.setText("Broadcast email sent successfully!");
        adminBroadcastNotifier.sendToAll(message);
        emailMessage.clear();
        logger.info("Broadcast email sent.");
    }

    /**
     * Opens the send email page.
     *
     * @param event
     *            action event
     */
    @FXML
    void sendEmailMassagePageButton(ActionEvent event)
    {
        setAllVisibleFalse();
        sendEmailMassagePage.setVisible(true);
    }

    /**
     * Sends overdue borrow notifications.
     *
     * @param event
     *            action event
     */
    @FXML
    void sendOverDueButton(ActionEvent event)
    {
        overdueBorrowNotifier.send();
        logger.info("Overdue notifications sent.");
    }

    /**
     * Shows all items in the table.
     *
     * @param event
     *            action event
     */
    @FXML
    void viewAllButton(ActionEvent event)
    {
        setAllVisibleFalse();
        viewItemPage.setVisible(true);
        var allItems = mediaItemService.getAllItems();
        ObservableList<MediaItem> observableItems = FXCollections.observableArrayList(allItems);
        itemTable.setItems(observableItems);
    }

    /**
     * Opens the view item page.
     *
     * @param event
     *            action event
     */
    @FXML
    void viewItemPageButton(ActionEvent event)
    {
        setAllVisibleFalse();
        viewItemPage.setVisible(true);
    }

    /**
     * Opens the add CD page.
     *
     * @param event
     *            action event
     */
    @FXML
    void addCdPageButton(ActionEvent event)
    {
        setAllVisibleFalse();
        addCdPage.setVisible(true);
    }

    /**
     * Saves a new CD from the add CD form.
     *
     * @param event
     *            action event
     * @see Cd
     */
    @FXML
    void addCdSaveButton(ActionEvent event)
    {
        String title = addCdTitle.getText();
        Cd cd = new Cd(title);
        mediaItemService.addItem(cd);
        logger.info("CD added: {}",title);
        errorLabelAddCd.setText("CD added successfully!");
        addCdTitle.clear();
    }

    /**
     * Opens the unregister user page.
     *
     * @param event
     *            action event
     */
    @FXML
    void unregisterUserPageButton(ActionEvent event)
    {
        setAllVisibleFalse();
        unregisterPage.setVisible(true);
    }

    /**
     * Unregisters the selected user from the system.
     *
     * @param event
     *            action event
     */
    @FXML
    void unregisterButton(ActionEvent event)
    {
        Object selected = usersNamesCombo.getValue();
        if (selected == null)
        {
            unregisterLable.setText("Please select a user to unregister.");
            unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
            return;
        }

        String userName = selected.toString();
        try
        {
            var userOpt = getAllUsersService.getAllUsers().stream().filter(u -> u.getName().equalsIgnoreCase(userName))
                    .findFirst();

            if (userOpt.isEmpty())
            {
                unregisterLable.setText("User not found: " + userName);
                unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
                return;
            }

            unregisterUserService.unregisterUser(userOpt.get());
            unregisterLable.setText("User '" + userName + "' unregistered successfully!");
            unregisterLable.setStyle("-fx-text-fill: #16a34a; -fx-font-weight: bold;");
            usersNamesCombo.getItems().remove(userName);

        } catch (IllegalStateException e)
        {
            unregisterLable.setText("Cannot unregister user: " + e.getMessage());
            unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
        } catch (Exception e)
        {
            unregisterLable.setText("Unexpected error occurred while unregistering.");
            unregisterLable.setStyle("-fx-text-fill: #dc2626; -fx-font-weight: bold;");
            e.printStackTrace();
        }
    }
}
