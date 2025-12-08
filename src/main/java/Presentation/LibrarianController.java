package Presentation;

import Entity.Book;
import Entity.MediaItem;
import Service.BookService.AllBookService;
import Service.BookService.BookCountService;
import Service.CDService.AllCdService;
import Service.CDService.CdCountService;
import Service.LogoutService;
import Service.MediaItem.MediaItemSearchService;
import Service.MediaItem.OverDueCountService;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueItemDetector;
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

import java.util.ArrayList;
import java.util.Date;

import static Enum.UserRole.User;

@Component
public class LibrarianController {

    private final LogoutService logoutService;
    private final FxmlNavigator fxmlNavigator;
    private final LocalSessionManager sessionManager;
    private final BookCountService bookCountService;
    private final CdCountService cdCountService;
    private final AllBookService allBookService;
    private final AllCdService allCdService;
    private final MediaItemSearchService mediaItemSearchService;
    private final UserCountService userCountService;
    private final OverDueCountService overDueCountService;
    private final OverdueItemDetector overdueItemDetector;

    @Autowired
    public LibrarianController(LogoutService logoutService,
                               FxmlNavigator fxmlNavigator,
                               LocalSessionManager sessionManager,
                               BookCountService bookCountService,
                               CdCountService cdCountService,
                               AllBookService allBookService,
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
        this.allBookService = allBookService;
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
    private TableColumn<MediaItem, Date> viewBorrowedDate;

    @FXML
    private TableColumn<MediaItem, Date> viewDueToDate;

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

    private void setUserNameDisplay() {
        if (sessionManager != null && sessionManager.getUser() != null) {
            userNameTestField.setText(sessionManager.getUser().getName());
        }
    }

    private void updateBookCount() {
        long bookCount = bookCountService.countBooks();
        numberOfBooks.setText(String.valueOf(bookCount));
    }

    private void updateOverdueCount() {
        long overdue = overDueCountService.countOverdueItems();
        numberOfOverDue.setText(String.valueOf(overdue));
    }

    private void updateCustomersCount() {
        long userCount = userCountService.countUsersByRole(User);
        numberOfCustomers.setText(String.valueOf(userCount));
    }

    private void setupSearchList() {
        if (searchList != null) {
            searchList.setItems(FXCollections.observableArrayList(
                    "Title (Books & CDs)",
                    "Book Author",
                    "Book ISBN"
            ));
            searchList.setValue("Title (Books & CDs)");
        }
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
    }

    private void showHomePage() {
        if (homePage != null) homePage.setVisible(true);
        if (searchPage != null) searchPage.setVisible(false);
    }

    private void showSearchPage() {
        if (homePage != null) homePage.setVisible(false);
        if (searchPage != null) searchPage.setVisible(true);
    }

    @FXML
    void detectOverDue(ActionEvent event) {
        var detected = overdueItemDetector.detectUsersWithOverdueBooks();
        var overdueItems = detected.stream()
                .flatMap(d -> d.items().stream())
                .map(OverdueBorrowedItem::item)
                .toList();
        itemTable.setItems(FXCollections.observableArrayList(overdueItems));
    }

    @FXML
    void homeButtonClick(ActionEvent event) {
        showHomePage();
        updateBookCount();
        updateOverdueCount();
        updateCustomersCount();
    }

    @FXML
    void logoutButton(ActionEvent event) {
        logoutService.logout();
        fxmlNavigator.logout(
                (javafx.stage.Stage) logoutButton.getScene().getWindow(),
                "/fxml/Login.fxml"
        );
    }

    @FXML
    void searchButton(ActionEvent event) {
        showSearchPage();
    }

    @FXML
    void searchByButton(ActionEvent event) {
        String keyword = searchBar.getText();
        String mode = searchList.getValue();
        var items = mediaItemSearchService.searchByMode(mode, keyword);
        itemTable.setItems(FXCollections.observableArrayList(items));
    }

    @FXML
    void viewAllButton(ActionEvent event) {
        var allItems = new ArrayList<MediaItem>();
        allItems.addAll(allBookService.getAllBooks());
        allItems.addAll(allCdService.getAllCds());
        itemTable.setItems(FXCollections.observableArrayList(allItems));
    }

    @FXML
    void applyFineOnItem(ActionEvent event) {

    }
}
