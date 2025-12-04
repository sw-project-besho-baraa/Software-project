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
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {

    @FXML private Button btnDashboard;
    @FXML private Button btnMedia;
    @FXML private Button btnOverdue;
    @FXML private Button btnUsersFines;
    @FXML private Button btnReminders;
    @FXML private VBox paneDashboard;
    @FXML private VBox paneMedia;
    @FXML private VBox paneOverdue;
    @FXML private VBox paneUsersFines;
    @FXML private VBox paneReminders;
    @FXML private Label lblTotalBooks;
    @FXML private Label lblBorrowedCount;
    @FXML private Label lblOverdueCount;
    @FXML private ComboBox<String> cmbSearchType;
    @FXML private TextField txtSearchQuery;
    @FXML private TableView<MediaItem> tblMedia;
    @FXML private TableColumn<MediaItem, String> colType;
    @FXML private TableColumn<MediaItem, String> colTitle;
    @FXML private TableColumn<MediaItem, String> colAuthor;
    @FXML private TableColumn<MediaItem, String> colIsbn;
    @FXML private TableColumn<MediaItem, String> colStatus;
    @FXML private TableColumn<MediaItem, String> colDueDate;
    @FXML private TableView<OverdueRow> tblOverdue;
    @FXML private TableColumn<OverdueRow, String> colOverType;
    @FXML private TableColumn<OverdueRow, String> colOverTitle;
    @FXML private TableColumn<OverdueRow, String> colOverUser;
    @FXML private TableColumn<OverdueRow, String> colOverDueDate;
    @FXML private TableColumn<OverdueRow, String> colOverStatus;
    @FXML private TextField txtUserEmail;
    @FXML private Label lblUserStatus;
    @FXML private Label lblUserFine;
    @FXML private TextField txtFinePayment;
    @FXML private TextArea txtRemindersLog;
    private final ObservableList<MediaItem> allMedia = FXCollections.observableArrayList();
    private final ObservableList<MediaItem> filteredMedia = FXCollections.observableArrayList();
    private final ObservableList<OverdueRow> overdueRows = FXCollections.observableArrayList();
    private double currentFine = 25.0;
    private String currentUserEmail = "student@mobo-library.com";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPanesVisibility();
        initSearchTypeCombo();
        initMediaTable();
        initOverdueTable();
        loadDemoMediaData();
        refreshDashboardStats();
        initUserDemo();
    }

    private void initPanesVisibility() {
        showOnly(paneDashboard);
    }

    private void initSearchTypeCombo() {
        cmbSearchType.setItems(FXCollections.observableArrayList(
                "Title", "Author", "ISBN", "Media Type"
        ));
        cmbSearchType.getSelectionModel().selectFirst();
    }

    private void initMediaTable() {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        tblMedia.setItems(filteredMedia);
    }

    private void initOverdueTable() {
        colOverType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colOverTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colOverUser.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        colOverDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colOverStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblOverdue.setItems(overdueRows);
    }

    private void loadDemoMediaData() {
        allMedia.addAll(
                new MediaItem("Book", "Clean Code", "Robert C. Martin",
                        "9780132350884", "Available", "-"),
                new MediaItem("Book", "Operating System Concepts", "Silberschatz",
                        "9781118063330", "Borrowed", "2025-11-28"),
                new MediaItem("Book", "Digital Image Processing", "Gonzalez",
                        "9780133002324", "Borrowed", "2025-11-20"),
                new MediaItem("CD", "Java Master Course", "MOBO Library",
                        "CD-001", "Available", "-"),
                new MediaItem("CD", "Algorithms Practice", "MOBO Library",
                        "CD-002", "Borrowed", "2025-11-15")
        );
        filteredMedia.setAll(allMedia);
    }

    private void refreshDashboardStats() {
        int totalBooks = (int) allMedia.stream()
                .filter(m -> "Book".equalsIgnoreCase(m.getType()))
                .count();

        int borrowedCount = (int) allMedia.stream()
                .filter(m -> !"Available".equalsIgnoreCase(m.getStatus()))
                .count();

        int overdueCount = (int) allMedia.stream()
                .filter(m -> "Overdue".equalsIgnoreCase(m.getStatus()))
                .count();

        lblTotalBooks.setText("Total books: " + totalBooks);
        lblBorrowedCount.setText("Borrowed items: " + borrowedCount);
        lblOverdueCount.setText("Overdue items: " + overdueCount);
    }

    private void initUserDemo() {
        txtUserEmail.setText(currentUserEmail);
        lblUserStatus.setText("Status: Has overdue items (demo)");
        updateFineLabel();
    }

    private void updateFineLabel() {
        lblUserFine.setText(String.format(Locale.ENGLISH,
                "Current fine: %.2f NIS", currentFine));
    }



    private void showOnly(Pane target) {
        List<Pane> panes = Arrays.asList(
                paneDashboard, paneMedia, paneOverdue, paneUsersFines, paneReminders
        );
        for (Pane p : panes) {
            boolean visible = (p == target);
            p.setVisible(visible);
            p.setManaged(visible);
        }
    }

    @FXML
    private void onShowDashboard() { showOnly(paneDashboard); }

    @FXML
    private void onShowMedia() { showOnly(paneMedia); }

    @FXML
    private void onShowOverdue() { showOnly(paneOverdue); }

    @FXML
    private void onShowUsersFines() { showOnly(paneUsersFines); }

    @FXML
    private void onShowReminders() { showOnly(paneReminders); }


    @FXML
    private void onSearchMedia() {
        String q = txtSearchQuery.getText();
        if (q == null) q = "";
        String query = q.toLowerCase(Locale.ENGLISH);

        String type = cmbSearchType.getSelectionModel().getSelectedItem();
        if (type == null) type = "Title";
        String searchType = type.toLowerCase(Locale.ENGLISH);

        final String finalQuery = query;

        filteredMedia.setAll(allMedia.filtered(m -> {
            if (finalQuery.isBlank()) return true;
            switch (searchType) {
                case "title":
                    return m.getTitle().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                case "author":
                    return m.getAuthor().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                case "isbn":
                    return m.getIsbn().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                case "media type":
                    return m.getType().toLowerCase(Locale.ENGLISH).contains(finalQuery);
                default:
                    return true;
            }
        }));
    }

    @FXML
    private void onClearSearch() {
        txtSearchQuery.clear();
        filteredMedia.setAll(allMedia);
        tblMedia.getSelectionModel().clearSelection();
    }

    @FXML
    private void onAddBook() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Book");
        dialog.setHeaderText("Enter new book title");
        dialog.setContentText("Title:");

        Optional<String> result = dialog.showAndWait();
        if (result.isEmpty()) {
            return;
        }

        String title = result.get().trim();
        if (title.isBlank()) {
            info("Add Book", "Title cannot be empty.");
            return;
        }

        // author
        TextInputDialog dialogAuthor = new TextInputDialog("Unknown");
        dialogAuthor.setTitle("Add Book");
        dialogAuthor.setHeaderText("Enter book author");
        dialogAuthor.setContentText("Author:");
        Optional<String> resultAuthor = dialogAuthor.showAndWait();
        String author = resultAuthor.orElse("Unknown").trim();
        if (author.isBlank()) author = "Unknown";

        // isbn
        String defaultIsbn = "NEW-" + (allMedia.size() + 1);
        TextInputDialog dialogIsbn = new TextInputDialog(defaultIsbn);
        dialogIsbn.setTitle("Add Book");
        dialogIsbn.setHeaderText("Enter ISBN");
        dialogIsbn.setContentText("ISBN:");
        Optional<String> resultIsbn = dialogIsbn.showAndWait();
        String isbn = resultIsbn.orElse(defaultIsbn).trim();
        if (isbn.isBlank()) isbn = defaultIsbn;

        MediaItem newBook = new MediaItem(
                "Book",
                title,
                author,
                isbn,
                "Available",
                "-"
        );
        allMedia.add(newBook);
        filteredMedia.setAll(allMedia);
        tblMedia.refresh();
        refreshDashboardStats();
        info("Add Book", "Book added (demo).");
    }



    @FXML
    private void onDetectOverdue() {
        for (MediaItem m : allMedia) {
            if ("Borrowed".equalsIgnoreCase(m.getStatus())
                    && !"2025-12-20".equals(m.getDueDate())
                    && !"2025-12-22".equals(m.getDueDate())) {
                m.setStatus("Overdue");
            }
        }
        tblMedia.refresh();
        refreshDashboardStats();
        rebuildOverdueTable();
        info("Overdue", "Overdue detection done (demo).");
    }

    private void rebuildOverdueTable() {
        overdueRows.clear();
        for (MediaItem m : allMedia) {
            if ("Overdue".equalsIgnoreCase(m.getStatus())) {
                overdueRows.add(new OverdueRow(
                        m.getType(),
                        m.getTitle(),
                        currentUserEmail,
                        m.getDueDate(),
                        m.getStatus()
                ));
            }
        }
    }



    @FXML
    private void onLoadUser() {
        String email = txtUserEmail.getText();
        if (email == null || email.isBlank()) {
            info("Load User", "Please enter an email.");
            return;
        }
        currentUserEmail = email;
        lblUserStatus.setText("Status: Loaded user (demo)");
        updateFineLabel();
    }

    @FXML
    private void onPayFine() {
        String text = txtFinePayment.getText();
        if (text == null || text.isBlank()) {
            info("Pay Fine", "Enter payment amount.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            info("Pay Fine", "Invalid amount.");
            return;
        }

        if (amount <= 0) {
            info("Pay Fine", "Amount must be positive.");
            return;
        }
        if (amount > currentFine) {
            info("Pay Fine", "Amount greater than current fine (demo).");
            return;
        }

        currentFine -= amount;
        txtFinePayment.clear();
        updateFineLabel();

        if (currentFine == 0.0) {
            lblUserStatus.setText("Status: OK to borrow");
        }

        info("Pay Fine", "Payment applied (demo).");
    }

    @FXML
    private void onUnregisterUser() {
        if (currentFine > 0.0) {
            info("Unregister User",
                    "Cannot unregister: user still has unpaid fines (demo).");
            return;
        }
        lblUserStatus.setText("Status: Unregistered (demo)");
        info("Unregister User", "User unregistered (demo).");
    }



    @FXML
    private void onSendReminders() {
        long count = allMedia.stream()
                .filter(m -> "Overdue".equalsIgnoreCase(m.getStatus()))
                .count();
        String msg = "Sent reminders to users with " + count + " overdue item(s) (demo).";
        txtRemindersLog.appendText(msg + "\n");
        info("Reminders", msg);
    }



    @FXML
    private void onLogout() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnDashboard.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("MOBO Library · Login");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Logout Error");
            a.setHeaderText(null);
            a.setContentText("Failed to return to login screen.");
            a.showAndWait();
        }
    }



    private void info(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }



    public static class MediaItem {
        private String type;
        private String title;
        private String author;
        private String isbn;
        private String status;
        private String dueDate;

        public MediaItem(String type, String title, String author,
                         String isbn, String status, String dueDate) {
            this.type = type;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.status = status;
            this.dueDate = dueDate;
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }

        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getDueDate() { return dueDate; }
        public void setDueDate(String dueDate) { this.dueDate = dueDate; }
    }

    public static class OverdueRow {
        private final String type;
        private final String title;
        private final String userEmail;
        private final String dueDate;
        private final String status;

        public OverdueRow(String type, String title, String userEmail,
                          String dueDate, String status) {
            this.type = type;
            this.title = title;
            this.userEmail = userEmail;
            this.dueDate = dueDate;
            this.status = status;
        }

        public String getType() { return type; }
        public String getTitle() { return title; }
        public String getUserEmail() { return userEmail; }
        public String getDueDate() { return dueDate; }
        public String getStatus() { return status; }
    }
}
