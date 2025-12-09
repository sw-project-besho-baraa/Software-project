package Presentation;

import Entity.Book;
import Entity.MediaItem;
import Entity.User;
import Repository.UserRepository;
import Service.BorrowService;
import Service.Fine.FineService;
import Service.ReturnService;
import Service.LogoutService;
import Service.BookService.AllBookService;
import Service.CDService.AllCdService;
import Service.MediaItem.MediaItemSearchService;
import Service.UserService.GetUserBalanceService;
import Service.UserService.UserBorrowedItemsService;
import Session.LocalSessionManager;
import Util.FxmlNavigator.FxmlNavigator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class UserController {

    @FXML
    private Label feinInDollar;

    @FXML
    private Label fine;

    @FXML
    private Button homeButton;

    @FXML
    private AnchorPane homePage;

    @FXML
    private TableView<MediaItem> itemTable;

    @FXML
    private Button logoutButton;

    @FXML
    private Label numberOfBooks;

    @FXML
    private Label numberOfOverDue;

    @FXML
    private Label errorPayLable;

    @FXML
    private Button payFine;

    @FXML
    private AnchorPane payFinePage;

    @FXML
    private TextField payValueTextField;

    @FXML
    private Button search;

    @FXML
    private TextField searchBar;

    @FXML
    private ComboBox<String> searchList;

    @FXML
    private AnchorPane searchPage;

    @FXML
    private Label userEmailField;

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

    private final LogoutService logoutService;
    private final FxmlNavigator fxmlNavigator;
    private final MediaItemSearchService mediaItemSearchService;
    private final AllBookService allBookService;
    private final AllCdService allCdService;
    private final LocalSessionManager sessionManager;
    private final BorrowService borrowService;
    private final ReturnService returnService;
    private final UserBorrowedItemsService userBorrowedItemsService;
    private final UserRepository userRepository;
    private final GetUserBalanceService getUserBalanceService;
    private final FineService fineService;

    @Autowired
    public UserController(LogoutService logoutService,
                          FxmlNavigator fxmlNavigator,
                          MediaItemSearchService mediaItemSearchService,
                          AllBookService allBookService,
                          AllCdService allCdService,
                          LocalSessionManager sessionManager,
                          BorrowService borrowService,
                          ReturnService returnService,
                          UserBorrowedItemsService userBorrowedItemsService,
                          UserRepository userRepository,
                          GetUserBalanceService getUserBalanceService,
                          FineService fineService
    ) {
        this.logoutService = logoutService;
        this.fxmlNavigator = fxmlNavigator;
        this.mediaItemSearchService = mediaItemSearchService;
        this.allBookService = allBookService;
        this.allCdService = allCdService;
        this.sessionManager = sessionManager;
        this.borrowService = borrowService;
        this.returnService = returnService;
        this.userBorrowedItemsService = userBorrowedItemsService;
        this.userRepository = userRepository;
        this.getUserBalanceService=getUserBalanceService;
        this.fineService=fineService;
    }

    @FXML
    private void initialize() {
        setupViewTable();
        initSearchList();
        initUserHeader();
        updateUserStats();
        setAllVisibleFalse();
        setUserFineData();
        if (homePage != null) {
            homePage.setVisible(true);
        }
    }

    private void setupViewTable() {
        if (itemTable == null) return;

        if (viewId != null) {
            viewId.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getId()));
        }

        if (viewTitle != null) {
            viewTitle.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        }

        if (viewAuthor != null) {
            viewAuthor.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book) {
                    String author = book.getAuthor();
                    return new javafx.beans.property.SimpleStringProperty(author != null ? author : "");
                }
                return new javafx.beans.property.SimpleStringProperty("");
            });
        }

        if (viewIsbn != null) {
            viewIsbn.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (item instanceof Book book) {
                    String isbn = book.getIsbn();
                    return new javafx.beans.property.SimpleStringProperty(isbn != null ? isbn : "");
                }
                return new javafx.beans.property.SimpleStringProperty("");
            });
        }

        if (viewIsBorrowd != null) {
            viewIsBorrowd.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleBooleanProperty(cellData.getValue().isBorrowed()));
        }

        if (viewUser != null) {
            viewUser.setCellValueFactory(cellData -> {
                MediaItem item = cellData.getValue();
                if (!item.isBorrowed() || item.getBorrower() == null) {
                    return new javafx.beans.property.SimpleStringProperty("");
                }

                int borrowerId;
                try {
                    borrowerId = item.getBorrower().getId();
                } catch (Exception e) {
                    return new javafx.beans.property.SimpleStringProperty("");
                }

                User sessionUser = sessionManager.getUser();
                if (sessionUser != null && sessionUser.getId() == borrowerId) {
                    return new javafx.beans.property.SimpleStringProperty(sessionUser.getName());
                }

                return userRepository.findById(borrowerId)
                        .map(u -> new javafx.beans.property.SimpleStringProperty(u.getName()))
                        .orElseGet(() -> new javafx.beans.property.SimpleStringProperty("User #" + borrowerId));
            });
        }

        if (viewBorrowedDate != null) {
            viewBorrowedDate.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getBorrowedDate()));
        }

        if (viewDueToDate != null) {
            viewDueToDate.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDueDate()));
        }

        if (viewType != null) {
            viewType.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getMediaType().name()
                    ));
        }
    }

    private void initSearchList() {
        if (searchList != null) {
            searchList.setItems(FXCollections.observableArrayList(
                    "Title (Books & CDs)",
                    "Book Author",
                    "Book ISBN"
            ));
            searchList.setValue("Title (Books & CDs)");
        }
    }

    private void initUserHeader() {
        User user = sessionManager.getUser();
        if (user == null) return;

        if (userNameTestField != null) {
            userNameTestField.setText(user.getName());
        }
        if (userEmailField != null) {
            userEmailField.setText(user.getEmail());
        }
        fine.setText(user.getFineBalance()+" $");
    }

    private void updateUserStats() {
        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) return;

        int borrowedCount = userBorrowedItemsService.countBorrowedItems(sessionUser);
        long overdueCount = userBorrowedItemsService.countOverdueItems(sessionUser);

        if (numberOfBooks != null) {
            numberOfBooks.setText(String.valueOf(borrowedCount));
        }
        if (numberOfOverDue != null) {
            numberOfOverDue.setText(String.valueOf(overdueCount));
        }
        fine.setText(sessionUser.getFineBalance()+" $");
    }


    private void setUserFineData(){
        User sessionUser = sessionManager.getUser();
        feinInDollar.setText(getUserBalanceService.getUserBalance(sessionUser.getId())+"  dollar");
        userEmailField.setText(sessionUser.getEmail());
    }

    private void setAllVisibleFalse() {
        if (homePage != null) homePage.setVisible(false);
        if (searchPage != null) searchPage.setVisible(false);
        if (payFinePage != null) payFinePage.setVisible(false);

    }

    @FXML
    void borrowItemButton(ActionEvent event) {
        MediaItem selected = itemTable != null ? itemTable.getSelectionModel().getSelectedItem() : null;
        if (selected == null) {
            System.out.println("No item selected to borrow.");
            return;
        }

        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) {
            System.out.println("No logged-in user in session.");
            return;
        }

        try {
            var currentBorrowed = userBorrowedItemsService.getBorrowedItems(sessionUser);
            sessionUser.setBorrowedItems(new ArrayList<>(currentBorrowed));
            borrowService.borrow(sessionUser, selected);
            System.out.println("Item borrowed successfully: " + selected.getTitle());
            updateUserStats();
            if (itemTable != null) {
                itemTable.refresh();
            }
        } catch (Exception e) {
            System.out.println("Failed to borrow item: " + e.getMessage());
        }
    }

    @FXML
    void returnItemButton(ActionEvent event) {
        MediaItem selected = itemTable != null ? itemTable.getSelectionModel().getSelectedItem() : null;
        if (selected == null) {
            System.out.println("No item selected to return.");
            return;
        }

        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) {
            System.out.println("No logged-in user in session.");
            return;
        }

        try {
            returnService.returnItem(sessionUser, selected);
            System.out.println("Item returned successfully: " + selected.getTitle());
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

    @FXML
    void homeButtonClick(ActionEvent event) {
        setAllVisibleFalse();
        if (homePage != null) {
            homePage.setVisible(true);
        }
    }

    @FXML
    void logoutButton(ActionEvent event) {
        logoutService.logout();
        Stage stage = (Stage) homePage.getScene().getWindow();
        fxmlNavigator.logout(stage, "/fxml/Login.fxml");
    }

    @FXML
    void myItemsButton(ActionEvent event) {
        setAllVisibleFalse();
        if (searchPage != null) {
            searchPage.setVisible(true);
        }

        User sessionUser = sessionManager.getUser();
        if (sessionUser == null || itemTable == null) {
            return;
        }

        var items = userBorrowedItemsService.getBorrowedItems(sessionUser);
        itemTable.setItems(FXCollections.observableArrayList(items));
    }

    @FXML
    void payButton(ActionEvent event) {
        if (errorPayLable != null) {
            errorPayLable.setText("");
        }

        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) {
            if (errorPayLable != null) {
                errorPayLable.setText("No logged-in user.");
            }
            return;
        }

        String amountText = payValueTextField != null ? payValueTextField.getText() : null;
        if (amountText == null || amountText.isBlank()) {
            if (errorPayLable != null) {
                errorPayLable.setText("Enter an amount to pay.");
            }
            return;
        }

        try {
            fineService.payFine(sessionUser, amountText);
            updateUserStats();
            setUserFineData();
            if (payValueTextField != null) {
                payValueTextField.clear();
            }
            if (errorPayLable != null) {
                errorPayLable.setText("Payment successful.");
            }
        } catch (IllegalArgumentException | IllegalStateException ex) {
            if (errorPayLable != null) {
                errorPayLable.setText(ex.getMessage());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            if (errorPayLable != null) {
                errorPayLable.setText(ex.getMessage() != null ? ex.getMessage() : "Payment failed.");
            }
        }
    }


    @FXML
    void payFineButton(ActionEvent event) {
        setAllVisibleFalse();
        setUserFineData();
        if (payFinePage != null) {
            payFinePage.setVisible(true);
        }

    }

    @FXML
    void printDetails(ActionEvent event) {
        User sessionUser = sessionManager.getUser();
        if (sessionUser == null) {
            System.out.println("No logged-in user.");
            return;
        }
        try {
            var controller = fxmlNavigator.openInNewWindow("/fxml/UserFineHistoryReport.fxml", "Fine History for " + sessionUser.getName()
            );
            if (controller != null && controller instanceof Presentation.FineHistoryReportController reportController) {
                reportController.setUser(sessionUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void searchByButton(ActionEvent event) {
        if (itemTable == null) return;

        String keyword = searchBar != null ? searchBar.getText() : "";
        String mode = searchList != null ? searchList.getValue() : "Title (Books & CDs)";

        var items = mediaItemSearchService.searchByMode(mode, keyword);
        itemTable.setItems(FXCollections.observableArrayList(items));
    }

    @FXML
    void searchButton(ActionEvent event) {
        setAllVisibleFalse();
        if (searchPage != null) {
            searchPage.setVisible(true);
        }
    }

    @FXML
    void viewAllButton(ActionEvent event) {
        setAllVisibleFalse();
        if (searchPage != null) {
            searchPage.setVisible(true);
        }

        if (itemTable == null) return;

        var allItems = new java.util.ArrayList<MediaItem>();
        allItems.addAll(allBookService.getAllBooks());
        allItems.addAll(allCdService.getAllCds());
        itemTable.setItems(FXCollections.observableArrayList(allItems));
    }
}
