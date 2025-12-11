package Presentation;

import Entity.Book;
import Entity.MediaItem;
import Entity.User;
import Repository.UserRepository;
import Service.BorrowService;
import Service.Fine.FineService;
import Service.ReturnItemService;
import Service.LogoutService;
import Service.MediaItem.MediaItemService;
import Service.CDService.AllCdService;
import Service.MediaItem.MediaItemSearchService;
import Service.UserService.GetUserBalanceService;
import Service.UserService.UserBorrowedItemsService;
import Session.LocalSessionManager;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import Util.FxmlNavigator.FxmlNavigator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Controller for the user dashboard.
 * <p>
 * Handles borrowing, returning, searching items, paying fines, and viewing user info.
 */
@Component
public class UserController {

    @FXML
    private Label feinInDollar, fine, numberOfBooks, numberOfOverDue, errorPayLable, userEmailField, userNameTestField;

    @FXML
    private Button homeButton, logoutButton, payFine, search;

    @FXML
    private TextField searchBar, payValueTextField;

    @FXML
    private ComboBox<String> searchList;

    @FXML
    private AnchorPane homePage, searchPage, payFinePage;

    @FXML
    private TableView<MediaItem> itemTable;

    @FXML
    private TableColumn<MediaItem, Integer> viewId;

    @FXML
    private TableColumn<MediaItem, String> viewTitle, viewAuthor, viewIsbn, viewType, viewUser;

    @FXML
    private TableColumn<MediaItem, Boolean> viewIsBorrowd;

    @FXML
    private TableColumn<MediaItem, LocalDateTime> viewBorrowedDate, viewDueToDate;

    private final LogoutService logoutService;
    private final FxmlNavigator fxmlNavigator;
    private final MediaItemSearchService mediaItemSearchService;
    private final MediaItemService mediaItemService;
    private final AllCdService allCdService;
    private final LocalSessionManager sessionManager;
    private final BorrowService borrowService;
    private final ReturnItemService returnService;
    private final UserBorrowedItemsService userBorrowedItemsService;
    private final UserRepository userRepository;
    private final GetUserBalanceService getUserBalanceService;
    private final FineService fineService;

    /**
     * Creates the user controller with required services.
     *
     * @param logoutService           service used to log out the current user
     * @param fxmlNavigator           navigator utility for switching between FXML scenes
     * @param mediaItemSearchService  service for searching media items
     * @param mediaItemService        service for general media item operations
     * @param allCdService            service to retrieve all CDs
     * @param sessionManager          manager for the current user session
     * @param borrowService           service handling borrow operations
     * @param returnService           service handling return operations
     * @param userBorrowedItemsService service for querying user borrowed items
     * @param userRepository          repository for user entities
     * @param getUserBalanceService   service to read user fine balance
     * @param fineService             service to calculate and pay fines
     */
    @Autowired
    public UserController(LogoutService logoutService,
                          FxmlNavigator fxmlNavigator,
                          MediaItemSearchService mediaItemSearchService,
                          MediaItemService mediaItemService,
                          AllCdService allCdService,
                          LocalSessionManager sessionManager,
                          BorrowService borrowService,
                          ReturnItemService returnService,
                          UserBorrowedItemsService userBorrowedItemsService,
                          UserRepository userRepository,
                          GetUserBalanceService getUserBalanceService,
                          FineService fineService) {
        this.logoutService = logoutService;
        this.fxmlNavigator = fxmlNavigator;
        this.mediaItemSearchService = mediaItemSearchService;
        this.mediaItemService = mediaItemService;
        this.allCdService = allCdService;
        this.sessionManager = sessionManager;
        this.borrowService = borrowService;
        this.returnService = returnService;
        this.userBorrowedItemsService = userBorrowedItemsService;
        this.userRepository = userRepository;
        this.getUserBalanceService = getUserBalanceService;
        this.fineService = fineService;
    }

    /**
     * Initializes user dashboard data and UI.
     */
    @FXML
    private void initialize() {
        setupViewTable();
        initSearchList();
        initUserHeader();
        updateUserStats();
        setAllVisibleFalse();
        setUserFineData();
        if (homePage != null) homePage.setVisible(true);
    }

    /**
     * Configures table columns for media items.
     */
    private void setupViewTable() {
        if (itemTable == null) return;

        if (viewId != null)
            viewId.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getId()));

        if (viewTitle != null)
            viewTitle.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTitle()));

        if (viewAuthor != null)
            viewAuthor.setCellValueFactory(c -> {
                MediaItem item = c.getValue();
                if (item instanceof Book book)
                    return new javafx.beans.property.SimpleStringProperty(book.getAuthor() != null ? book.getAuthor() : "");
                return new javafx.beans.property.SimpleStringProperty("");
            });

        if (viewIsbn != null)
            viewIsbn.setCellValueFactory(c -> {
                MediaItem item = c.getValue();
                if (item instanceof Book book)
                    return new javafx.beans.property.SimpleStringProperty(book.getIsbn() != null ? book.getIsbn() : "");
                return new javafx.beans.property.SimpleStringProperty("");
            });

        if (viewIsBorrowd != null)
            viewIsBorrowd.setCellValueFactory(c -> new javafx.beans.property.SimpleBooleanProperty(c.getValue().isBorrowed()));

        if (viewUser != null)
            viewUser.setCellValueFactory(c -> {
                MediaItem item = c.getValue();
                if (!item.isBorrowed() || item.getBorrower() == null)
                    return new javafx.beans.property.SimpleStringProperty("");

                int borrowerId;
                try {
                    borrowerId = item.getBorrower().getId();
                } catch (Exception e) {
                    return new javafx.beans.property.SimpleStringProperty("");
                }

                User sessionUser = sessionManager.getUser();
                if (sessionUser != null && sessionUser.getId() == borrowerId)
                    return new javafx.beans.property.SimpleStringProperty(sessionUser.getName());

                return userRepository.findById(borrowerId)
                        .map(u -> new javafx.beans.property.SimpleStringProperty(u.getName()))
                        .orElseGet(() -> new javafx.beans.property.SimpleStringProperty("User #" + borrowerId));
            });

        if (viewBorrowedDate != null)
            viewBorrowedDate.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getBorrowedDate()));

        if (viewDueToDate != null)
            viewDueToDate.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getDueDate()));

        if (viewType != null)
            viewType.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getMediaType().name()));
    }

    /**
     * Initializes search combo box values.
     */
    private void initSearchList() {
        if (searchList != null) {
            searchList.setItems(FXCollections.observableArrayList("Title (Books & CDs)", "Book Author", "Book ISBN"));
            searchList.setValue("Title (Books & CDs)");
        }
    }

    /**
     * Initializes user header info (name, email, fine balance).
     */
    private void initUserHeader() {
        User user = sessionManager.getUser();
        if (user == null) return;

        if (userNameTestField != null) userNameTestField.setText(user.getName());
        if (userEmailField != null) userEmailField.setText(user.getEmail());
        fine.setText(user.getFineBalance() + " $");
    }

    /**
     * Updates the user’s borrowed item and overdue counts.
     */
    private void updateUserStats() {
        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) return;

        int borrowedCount = userBorrowedItemsService.countBorrowedItems(sessionUser);
        long overdueCount = userBorrowedItemsService.countOverdueItems(sessionUser, new CurrentLocalDateTimeResolver());

        if (numberOfBooks != null)
            numberOfBooks.setText(String.valueOf(borrowedCount));
        if (numberOfOverDue != null)
            numberOfOverDue.setText(String.valueOf(overdueCount));
        fine.setText(sessionUser.getFineBalance() + " $");
    }

    /**
     * Displays the current fine balance of the user.
     */
    private void setUserFineData() {
        User sessionUser = sessionManager.getUser();
        feinInDollar.setText(getUserBalanceService.getUserBalance(sessionUser.getId()) + "  dollar");
        userEmailField.setText(sessionUser.getEmail());
    }

    /**
     * Hides all main pages.
     */
    private void setAllVisibleFalse() {
        if (homePage != null) homePage.setVisible(false);
        if (searchPage != null) searchPage.setVisible(false);
        if (payFinePage != null) payFinePage.setVisible(false);
    }

    /**
     * Allows the user to borrow a selected item.
     *
     * @param event the action event triggered by the borrow button
     */
    @FXML
    void borrowItemButton(ActionEvent event) {
        MediaItem selected = itemTable != null ? itemTable.getSelectionModel().getSelectedItem() : null;
        if (selected == null) return;

        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) return;

        try {
            var currentBorrowed = userBorrowedItemsService.getBorrowedItems(sessionUser);
            sessionUser.setBorrowedItems(new java.util.ArrayList<>(currentBorrowed));
            borrowService.borrow(sessionUser, selected);
            updateUserStats();
            if (itemTable != null) itemTable.refresh();
        } catch (Exception e) {
            System.out.println("Failed to borrow item: " + e.getMessage());
        }
    }

    /**
     * Allows the user to return a selected item.
     *
     * @param event the action event triggered by the return button
     */
    @FXML
    void returnItemButton(ActionEvent event) {
        MediaItem selected = itemTable != null ? itemTable.getSelectionModel().getSelectedItem() : null;
        if (selected == null) return;

        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) return;

        try {
            returnService.returnItem(sessionUser, selected);
            updateUserStats();
            if (itemTable != null) {
                var items = userBorrowedItemsService.getBorrowedItems(sessionUser);
                itemTable.setItems(FXCollections.observableArrayList(items));
                itemTable.refresh();
            }
        } catch (Exception e) {
            System.out.println("Failed to return item: " + e.getMessage());
        }
    }

    /**
     * Shows the home page.
     *
     * @param event the action event triggered by the home button
     */
    @FXML
    void homeButtonClick(ActionEvent event) {
        setAllVisibleFalse();
        if (homePage != null) homePage.setVisible(true);
    }

    /**
     * Logs the user out and returns to the login screen.
     *
     * @param event the action event triggered by the logout button
     */
    @FXML
    void logoutButton(ActionEvent event) {
        logoutService.logout();
        Stage stage = (Stage) homePage.getScene().getWindow();
        fxmlNavigator.logout(stage, "/fxml/Login.fxml");
    }

    /**
     * Displays the user’s borrowed items.
     *
     * @param event the action event triggered by the "My Items" button
     */
    @FXML
    void myItemsButton(ActionEvent event) {
        setAllVisibleFalse();
        if (searchPage != null) searchPage.setVisible(true);

        User sessionUser = sessionManager.getUser();
        if (sessionUser == null || itemTable == null) return;

        var items = userBorrowedItemsService.getBorrowedItems(sessionUser);
        itemTable.setItems(FXCollections.observableArrayList(items));
    }

    /**
     * Handles fine payment submission.
     *
     * @param event the action event triggered by the pay button
     */
    @FXML
    void payButton(ActionEvent event) {
        if (errorPayLable != null) errorPayLable.setText("");
        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) {
            if (errorPayLable != null) errorPayLable.setText("No logged-in user.");
            return;
        }

        String amountText = payValueTextField != null ? payValueTextField.getText() : null;
        if (amountText == null || amountText.isBlank()) {
            if (errorPayLable != null) errorPayLable.setText("Enter an amount to pay.");
            return;
        }

        try {
            fineService.payFine(sessionUser, amountText);
            updateUserStats();
            setUserFineData();
            if (payValueTextField != null) payValueTextField.clear();
            if (errorPayLable != null) errorPayLable.setText("Payment successful.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            if (errorPayLable != null) errorPayLable.setText(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            if (errorPayLable != null)
                errorPayLable.setText(ex.getMessage() != null ? ex.getMessage() : "Payment failed.");
        }
    }

    /**
     * Opens the fine payment page.
     *
     * @param event the action event triggered by the pay fine button
     */
    @FXML
    void payFineButton(ActionEvent event) {
        setAllVisibleFalse();
        setUserFineData();
        if (payFinePage != null) payFinePage.setVisible(true);
    }

    /**
     * Opens the fine history report window.
     *
     * @param event the action event triggered by the print details button
     */
    @FXML
    void printDetails(ActionEvent event) {
        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) return;
        try {
            var controller = fxmlNavigator.openInNewWindow(
                    "/fxml/UserFineHistoryReport.fxml",
                    "Fine History for " + sessionUser.getName());
            if (controller instanceof Presentation.FineHistoryReportController reportController)
                reportController.setUser(sessionUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches media items based on the selected mode.
     *
     * @param event the action event triggered by the search-by button
     */
    @FXML
    void searchByButton(ActionEvent event) {
        if (itemTable == null) return;
        String keyword = searchBar != null ? searchBar.getText() : "";
        String mode = searchList != null ? searchList.getValue() : "Title (Books & CDs)";
        var items = mediaItemSearchService.searchByMode(mode, keyword);
        itemTable.setItems(FXCollections.observableArrayList(items));
    }

    /**
     * Opens the search page.
     *
     * @param event the action event triggered by the search button
     */
    @FXML
    void searchButton(ActionEvent event) {
        setAllVisibleFalse();
        if (searchPage != null) searchPage.setVisible(true);
    }

    /**
     * Displays all available media items.
     *
     * @param event the action event triggered by the view-all button
     */
    @FXML
    void viewAllButton(ActionEvent event) {
        setAllVisibleFalse();
        if (searchPage != null) searchPage.setVisible(true);
        if (itemTable == null) return;
        var allItems = mediaItemService.getAllItems();
        itemTable.setItems(FXCollections.observableArrayList(allItems));
    }
}
