package Presentation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class UserDashboardController implements Initializable
{

    @FXML
    private Button btnHome;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnBorrowed;
    @FXML
    private Button btnFines;
    @FXML
    private Button btnLogout;

    @FXML
    private VBox paneHome;
    @FXML
    private VBox paneSearch;
    @FXML
    private VBox paneBorrowed;
    @FXML
    private VBox paneFines;

    @FXML
    private Label lblUserEmail;
    @FXML
    private Label lblBorrowStatus;
    @FXML
    private Label lblHomeFine;

    @FXML
    private ComboBox<String> cmbSearchType;
    @FXML
    private TextField txtSearchQuery;
    @FXML
    private TableView<MediaItem> tblSearchResults;
    @FXML
    private TableColumn<MediaItem, String> colResultType;
    @FXML
    private TableColumn<MediaItem, String> colResultTitle;
    @FXML
    private TableColumn<MediaItem, String> colResultAuthor;
    @FXML
    private TableColumn<MediaItem, String> colResultIsbn;
    @FXML
    private TableColumn<MediaItem, String> colResultAvailability;

    @FXML
    private TableView<MediaItem> tblBorrowed;
    @FXML
    private TableColumn<MediaItem, String> colBorrowedType;
    @FXML
    private TableColumn<MediaItem, String> colBorrowedTitle;
    @FXML
    private TableColumn<MediaItem, String> colBorrowedDueDate;
    @FXML
    private TableColumn<MediaItem, String> colBorrowedStatus;
    @FXML
    private Label lblBorrowedCount;

    @FXML
    private Label lblFineTotal;
    @FXML
    private Label lblOverdueBooks;
    @FXML
    private Label lblOverdueCds;
    @FXML
    private TextField txtFinePayment;
    @FXML
    private TextArea txtFineHistory;

    private final ObservableList<MediaItem> allMedia = FXCollections.observableArrayList();
    private final ObservableList<MediaItem> searchResults = FXCollections.observableArrayList();
    private final ObservableList<MediaItem> borrowedItems = FXCollections.observableArrayList();

    private final String currentUserEmail = "student@mobo-library.com";
    private double currentFine = 30.0; // demo
    private final int borrowLimit = 3;

    @Override
    public void initialize(URL location,ResourceBundle resources)
    {
        initPanesVisibility();
        initSearchTypeCombo();
        initTables();
        initDemoData();
        initUserInfo();
        updateAllStatusLabels();
    }

    private void initPanesVisibility()
    {
        showOnly(paneHome);
    }

    private void initSearchTypeCombo()
    {
        cmbSearchType.setItems(FXCollections.observableArrayList("Title","Author","ISBN","Media Type"));
        cmbSearchType.getSelectionModel().selectFirst();
    }

    private void initTables()
    {

        colResultType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colResultTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colResultAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colResultIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colResultAvailability.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblSearchResults.setItems(searchResults);

        colBorrowedType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colBorrowedTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colBorrowedDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colBorrowedStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblBorrowed.setItems(borrowedItems);
    }

    private void initDemoData()
    {
        // available + borrowed + overdue for demo
        allMedia.addAll(new MediaItem("Book", "Clean Code", "Robert C. Martin", "9780132350884", "Available", "-"),
                new MediaItem("Book", "Operating System Concepts", "Silberschatz", "9781118063330", "Borrowed",
                        "2025-12-20"),
                new MediaItem("Book", "Digital Image Processing", "Gonzalez", "9780133002324", "Overdue", "2025-11-20"),
                new MediaItem("CD", "Java Master Course", "MOBO Library", "CD-001", "Available", "-"),
                new MediaItem("CD", "Algorithms Practice", "MOBO Library", "CD-002", "Overdue", "2025-11-15"));

        for (MediaItem m : allMedia)
        {
            if (!"Available".equalsIgnoreCase(m.getStatus()))
            {
                borrowedItems.add(m);
            }
        }

        searchResults.setAll(allMedia);
    }

    private void initUserInfo()
    {
        lblUserEmail.setText(currentUserEmail);
    }

    private void updateAllStatusLabels()
    {
        int borrowedCount = borrowedItems.size();
        lblBorrowStatus.setText("Borrowed: " + borrowedCount + " / " + borrowLimit + " items");
        lblBorrowedCount.setText("You have " + borrowedCount + " items borrowed.");

        lblHomeFine.setText(String.format(Locale.ENGLISH,"Total fines: %.2f NIS",currentFine));
        lblFineTotal.setText(String.format(Locale.ENGLISH,"Total fines: %.2f NIS",currentFine));

        long overBooks = borrowedItems.stream().filter(m -> "Book".equalsIgnoreCase(m.getType()))
                .filter(m -> "Overdue".equalsIgnoreCase(m.getStatus())).count();

        long overCds = borrowedItems.stream().filter(m -> "CD".equalsIgnoreCase(m.getType()))
                .filter(m -> "Overdue".equalsIgnoreCase(m.getStatus())).count();

        lblOverdueBooks.setText("Overdue books: " + overBooks);
        lblOverdueCds.setText("Overdue CDs: " + overCds);
    }

    private void showOnly(Pane target)
    {
        List<Pane> panes = Arrays.asList(paneHome,paneSearch,paneBorrowed,paneFines);
        for (Pane p : panes)
        {
            boolean visible = (p == target);
            p.setVisible(visible);
            p.setManaged(visible);
        }
    }

    @FXML
    private void onShowHome()
    {
        showOnly(paneHome);
    }

    @FXML
    private void onShowSearch()
    {
        showOnly(paneSearch);
    }

    @FXML
    private void onShowBorrowed()
    {
        showOnly(paneBorrowed);
    }

    @FXML
    private void onShowFines()
    {
        showOnly(paneFines);
    }

    @FXML
    private void onSearchMedia()
    {
        String q = txtSearchQuery.getText();
        if (q == null)
            q = "";
        String query = q.toLowerCase(Locale.ENGLISH);

        String type = cmbSearchType.getSelectionModel().getSelectedItem();
        if (type == null)
            type = "Title";
        String searchType = type.toLowerCase(Locale.ENGLISH);

        final String finalQuery = query;

        searchResults.setAll(allMedia.filtered(m -> {
            if (finalQuery.isBlank())
                return true;
            switch (searchType) {
                case "title" :
                    return m.getTitle().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                case "author" :
                    return m.getAuthor().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                case "isbn" :
                    return m.getIsbn().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                case "media type" :
                    return m.getType().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                default :
                    return true;
            }
        }));
    }

    @FXML
    private void onClearSearch()
    {
        txtSearchQuery.clear();
        searchResults.setAll(allMedia);
        tblSearchResults.getSelectionModel().clearSelection();
    }

    @FXML
    private void onBorrowBook()
    {
        MediaItem selected = tblSearchResults.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            info("Borrow Book","Please select a media item.");
            return;
        }
        if (!"Book".equalsIgnoreCase(selected.getType()))
        {
            info("Borrow Book","Selected item is not a book.");
            return;
        }
        if (!"Available".equalsIgnoreCase(selected.getStatus()))
        {
            info("Borrow Book","Item is not available.");
            return;
        }
        if (borrowedItems.size() >= borrowLimit)
        {
            info("Borrow Book","You reached your borrow limit (" + borrowLimit + ").");
            return;
        }

        selected.setStatus("Borrowed");
        selected.setDueDate("2025-12-25"); // demo due date
        borrowedItems.add(selected);
        tblSearchResults.refresh();
        tblBorrowed.refresh();
        updateAllStatusLabels();
        info("Borrow Book","Book borrowed successfully (demo).");
    }

    @FXML
    private void onBorrowCD()
    {
        MediaItem selected = tblSearchResults.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            info("Borrow CD","Please select a media item.");
            return;
        }
        if (!"CD".equalsIgnoreCase(selected.getType()))
        {
            info("Borrow CD","Selected item is not a CD.");
            return;
        }
        if (!"Available".equalsIgnoreCase(selected.getStatus()))
        {
            info("Borrow CD","Item is not available.");
            return;
        }
        if (borrowedItems.size() >= borrowLimit)
        {
            info("Borrow CD","You reached your borrow limit (" + borrowLimit + ").");
            return;
        }

        selected.setStatus("Borrowed");
        selected.setDueDate("2025-12-27"); // demo due date
        borrowedItems.add(selected);
        tblSearchResults.refresh();
        tblBorrowed.refresh();
        updateAllStatusLabels();
        info("Borrow CD","CD borrowed successfully (demo).");
    }

    @FXML
    private void onReturnItem()
    {
        MediaItem selected = tblBorrowed.getSelectionModel().getSelectedItem();
        if (selected == null)
        {
            info("Return Item","Please select a borrowed item.");
            return;
        }

        selected.setStatus("Available");
        selected.setDueDate("-");
        borrowedItems.remove(selected);
        tblBorrowed.refresh();
        tblSearchResults.refresh();
        updateAllStatusLabels();
        info("Return Item","Item returned (demo).");
    }

    @FXML
    private void onPayFine()
    {
        String text = txtFinePayment.getText();
        if (text == null || text.isBlank())
        {
            info("Pay Fine","Enter payment amount.");
            return;
        }

        double amount;
        try
        {
            amount = Double.parseDouble(text);
        } catch (NumberFormatException ex)
        {
            info("Pay Fine","Invalid amount.");
            return;
        }

        if (amount <= 0)
        {
            info("Pay Fine","Amount must be positive.");
            return;
        }

        if (amount > currentFine)
        {
            info("Pay Fine","Amount greater than current fine (demo).");
            return;
        }

        currentFine -= amount;
        txtFinePayment.clear();
        txtFineHistory
                .appendText(String.format(Locale.ENGLISH,"Paid %.2f NIS. Remaining: %.2f NIS%n",amount,currentFine));
        updateAllStatusLabels();
        info("Pay Fine","Payment applied (demo).");
    }

    @FXML
    private void onLogout()
    {
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnHome.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("MOBO Library · Login");
            stage.show();
        } catch (IOException e)
        {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Logout Error");
            a.setHeaderText(null);
            a.setContentText("Failed to return to login screen.");
            a.showAndWait();
        }
    }

    private void info(String title,String message)
    {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    public static class MediaItem
    {
        private String type;
        private String title;
        private String author;
        private String isbn;
        private String status;
        private String dueDate;

        public MediaItem(String type, String title, String author, String isbn, String status, String dueDate)
        {
            this.type = type;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.status = status;
            this.dueDate = dueDate;
        }

        public String getType()
        {
            return type;
        }

        public void setType(String type)
        {
            this.type = type;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getAuthor()
        {
            return author;
        }

        public void setAuthor(String author)
        {
            this.author = author;
        }

        public String getIsbn()
        {
            return isbn;
        }

        public void setIsbn(String isbn)
        {
            this.isbn = isbn;
        }

        public String getStatus()
        {
            return status;
        }

        public void setStatus(String status)
        {
            this.status = status;
        }

        public String getDueDate()
        {
            return dueDate;
        }

        public void setDueDate(String dueDate)
        {
            this.dueDate = dueDate;
        }
    }
}
