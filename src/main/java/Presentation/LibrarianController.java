package Presentation;

import Entity.Book;
import Entity.MediaItem;
import Service.MediaItem.MediaItemService;
import Service.BookService.BooksService;
import Service.CDService.AllCdService;
import Service.CDService.CdCountService;
import Service.LogoutService;
import Service.MediaItem.MediaItemSearchService;
import Service.MediaItem.OverDueCountService;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Service.UserService.UserCountService;
import Session.LocalSessionManager;
import Util.FxmlNavigator.FxmlNavigator;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static Enum.UserRole.User;

/**
 * Controller for the librarian dashboard.
 * <p>
 * Handles viewing media items, detecting overdue items, and performing searches.
 */
@Component
public class LibrarianController {

    private final LogoutService logoutService;
    private final FxmlNavigator fxmlNavigator;
    private final LocalSessionManager sessionManager;
    private final BooksService bookCountService;
    private final CdCountService cdCountService;
    private final MediaItemService mediaItemService;
    private final AllCdService allCdService;
    private final MediaItemSearchService mediaItemSearchService;
    private final UserCountService userCountService;
    private final OverDueCountService overDueCountService;
    private final OverdueItemDetector overdueItemDetector;

    /**
     * Creates the librarian controller with injected services.
     */
    @Autowired
    public LibrarianController(LogoutService logoutService,
                               FxmlNavigator fxmlNavigator,
                               LocalSessionManager sessionManager,
                               BooksService bookCountService,
                               CdCountService cdCountService,
                               MediaItemService mediaItemService,
                               AllCdService allCdService,
                               MediaItemSearchService mediaItemSearchService,
                               UserCountService userCountService,
                               OverDueCountService overDueCountService,
                               OverdueItemDetector overdueItemDetector) {
        this.logoutService = logoutService;
        this.fxmlNavigator = fxmlNavigator;
        this.sessionManager = sessionManager;
        this.bookCountService = bookCountService;
        this.cdCountService = cdCountService;
        this.mediaItemService = mediaItemService;
        this.allCdService = allCdService;
        this.mediaItemSearchService = mediaItemSearchService;
        this.userCountService = userCountService;
        this.overDueCountService = overDueCountService;
        this.overdueItemDetector = overdueItemDetector;
    }

    @FXML
    private Label numberOfCustomers;
    @FXML
    private AnchorPane homePage;
    @FXML
    private Button homePageButton;
    @FXML
    private TableView<MediaItem> itemTable;
    @FXML
    private Button logoutButton;
    @FXML
    private Label numberOfBooks;
    @FXML
    private Label numberOfOverDue;
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> searchList;
    @FXML
    private AnchorPane searchPage;
    @FXML
    private Button searchPageButton;
    @FXML
    private Label userNameTestField;
    @FXML
    private TableColumn<MediaItem, String> viewAuthor;
    @FXML
    private TableColumn<MediaItem, LocalDateTime> viewBorrowedDate;
    @FXML
    private TableColumn<MediaItem, LocalDateTime> viewDueToDate;
    @FXML
    private TableColumn<MediaItem, Integer> viewId;
    @FXML
    private TableColumn<MediaItem, Boolean> viewIsBorrowd;
    @FXML
    private TableColumn<MediaItem, String> viewIsbn;
    @FXML
    private TableColumn<MediaItem, String> viewTitle;
    @FXML
    private TableColumn<MediaItem, String> viewType;
    @FXML
    private TableColumn<MediaItem, String> viewUser;

    /**
     * Initializes the controller and loads initial data.
     */
    @FXML
    private void initialize() {
        setUserNameDisplay();
        updateBookCount();
        updateOverdueCount();
        updateCustomersCount();
        setupViewTable();
        setupSearchList();
        showHomePage();
    }

    /**
     * Sets the librarian's name label.
     */
    private void setUserNameDisplay() {
        if (sessionManager != null && sessionManager.getUser() != null)
            userNameTestField.setText(sessionManager.getUser().getName());
    }

    /**
     * Updates the book count label.
     */
    private void updateBookCount() {
        long bookCount = bookCountService.countBooks();
        numberOfBooks.setText(String.valueOf(bookCount));
    }

    /**
     * Updates the overdue items count label.
     */
    private void updateOverdueCount() {
        long overdue = overDueCountService.countOverdueItems();
        numberOfOverDue.setText(String.valueOf(overdue));
    }

    /**
     * Updates the number of registered customers.
     */
    private void updateCustomersCount() {
        long userCount = userCountService.countUsersByRole(User);
        numberOfCustomers.setText(String.valueOf(userCount));
    }

    /**
     * Sets up the search options combo box.
     */
    private void setupSearchList() {
        if (searchList != null) {
            searchList.setItems(FXCollections.observableArrayList("Title (Books & CDs)", "Book Author", "Book ISBN"));
            searchList.setValue("Title (Books & CDs)");
        }
    }

    /**
     * Configures the table view columns for displaying media items.
     */
    private void setupViewTable() {
        if (itemTable == null)
            return;

        viewId.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        viewTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));

        if (viewAuthor != null) {
            viewAuthor.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book) {
                    return new SimpleStringProperty(book.getAuthor() != null ? book.getAuthor() : "");
                }
                return new SimpleStringProperty("");
            });
        }

        if (viewIsbn != null) {
            viewIsbn.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book) {
                    return new SimpleStringProperty(book.getIsbn() != null ? book.getIsbn() : "");
                }
                return new SimpleStringProperty("");
            });
        }

        viewIsBorrowd.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isBorrowed()));
        viewUser.setCellValueFactory(cellData -> {
            var borrower = cellData.getValue().getBorrower();
            return new SimpleStringProperty(borrower != null ? borrower.getName() : "");
        });

        viewBorrowedDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getBorrowedDate()));
        viewDueToDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDueDate()));

        if (viewType != null)
            viewType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMediaType().name()));
    }

    /**
     * Shows the home page.
     */
    private void showHomePage() {
        if (homePage != null)
            homePage.setVisible(true);
        if (searchPage != null)
            searchPage.setVisible(false);
    }

    /**
     * Shows the search page.
     */
    private void showSearchPage() {
        if (homePage != null)
            homePage.setVisible(false);
        if (searchPage != null)
            searchPage.setVisible(true);
    }

    /**
     * Detects overdue borrowed items and displays them.
     *
     * @param event button click event
     * @see OverdueItemDetector
     */
    @FXML
    void detectOverDue(ActionEvent event) {
        var detected = overdueItemDetector.detectUsersWithOverdueBooks();
        var overdueItems = detected.stream()
                .flatMap(d -> d.items().stream())
                .map(OverdueBorrowedItem::item)
                .toList();
        itemTable.setItems(FXCollections.observableArrayList(overdueItems));
    }

    /**
     * Returns to home and refreshes counts.
     *
     * @param event button click event
     */
    @FXML
    void homeButtonClick(ActionEvent event) {
        showHomePage();
        updateBookCount();
        updateOverdueCount();
        updateCustomersCount();
    }

    /**
     * Logs out the librarian.
     *
     * @param event button click event
     */
    @FXML
    void logoutButton(ActionEvent event) {
        logoutService.logout();
        fxmlNavigator.logout((javafx.stage.Stage) logoutButton.getScene().getWindow(), "/fxml/Login.fxml");
    }

    /**
     * Opens the search page.
     *
     * @param event button click event
     */
    @FXML
    void searchButton(ActionEvent event) {
        showSearchPage();
    }

    /**
     * Performs search by mode and keyword.
     *
     * @param event button click event
     */
    @FXML
    void searchByButton(ActionEvent event) {
        String keyword = searchBar.getText();
        String mode = searchList.getValue();
        var items = mediaItemSearchService.searchByMode(mode, keyword);
        itemTable.setItems(FXCollections.observableArrayList(items));
    }

    /**
     * Displays all media items.
     *
     * @param event button click event
     */
    @FXML
    void viewAllButton(ActionEvent event) {
        var allItems = mediaItemService.getAllItems();
        itemTable.setItems(FXCollections.observableArrayList(allItems));
    }

    /**
     * Placeholder for applying a fine on an item.
     *
     * @param event button click event
     */
    @FXML
    void applyFineOnItem(ActionEvent event) {

    }
}
