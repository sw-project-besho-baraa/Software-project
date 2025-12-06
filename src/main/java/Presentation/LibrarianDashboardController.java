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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LibrarianDashboardController implements Initializable
{

    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnScanner;
    @FXML
    private Button btnOverdueList;
    @FXML
    private Button btnLogout;
    @FXML
    private VBox paneDashboard;
    @FXML
    private VBox paneScanner;
    @FXML
    private VBox paneOverdueList;
    @FXML
    private Label lblTotalBooks;
    @FXML
    private Label lblBorrowedCount;
    @FXML
    private Label lblOverdueCount;
    @FXML
    private Label lblLastRun;
    @FXML
    private Label lblScannerSummary;
    @FXML
    private TableView<OverdueItem> tblOverdue;
    @FXML
    private TableColumn<OverdueItem, String> colType;
    @FXML
    private TableColumn<OverdueItem, String> colTitle;
    @FXML
    private TableColumn<OverdueItem, String> colUser;
    @FXML
    private TableColumn<OverdueItem, String> colDueDate;
    @FXML
    private TableColumn<OverdueItem, String> colStatus;

    private final ObservableList<MediaItem> allMedia = FXCollections.observableArrayList();
    private final ObservableList<OverdueItem> overdueItems = FXCollections.observableArrayList();

    private final String demoUserEmail = "student@mobo-library.com";

    @Override
    public void initialize(URL location,ResourceBundle resources)
    {
        initPanesVisibility();
        initDemoMediaData();
        initOverdueTable();
        updateDashboardStats();
    }

    private void initPanesVisibility()
    {
        showOnly(paneDashboard);
    }

    private void initDemoMediaData()
    {

        allMedia.addAll(
                new MediaItem("Book", "Clean Code", "Robert C. Martin", "9780132350884", "Available", "-",
                        demoUserEmail),
                new MediaItem("Book", "Operating System Concepts", "Silberschatz", "9781118063330", "Borrowed",
                        "2025-11-28", demoUserEmail),
                new MediaItem("Book", "Digital Image Processing", "Gonzalez", "9780133002324", "Borrowed", "2025-11-20",
                        demoUserEmail),
                new MediaItem("CD", "Java Master Course", "MOBO Library", "CD-001", "Available", "-", demoUserEmail),
                new MediaItem("CD", "Algorithms Practice", "MOBO Library", "CD-002", "Borrowed", "2025-11-15",
                        demoUserEmail));
    }

    private void initOverdueTable()
    {
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colUser.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        colDueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblOverdue.setItems(overdueItems);
    }

    private void updateDashboardStats()
    {
        int totalBooks = (int) allMedia.stream().filter(m -> "Book".equalsIgnoreCase(m.getType())).count();

        int borrowedCount = (int) allMedia.stream().filter(m -> !"Available".equalsIgnoreCase(m.getStatus())).count();

        int overdueCount = (int) allMedia.stream().filter(m -> "Overdue".equalsIgnoreCase(m.getStatus())).count();

        lblTotalBooks.setText("Total books: " + totalBooks);
        lblBorrowedCount.setText("Borrowed items: " + borrowedCount);
        lblOverdueCount.setText("Overdue items: " + overdueCount);
    }

    private void showOnly(Pane target)
    {
        List<Pane> panes = Arrays.asList(paneDashboard,paneScanner,paneOverdueList);
        for (Pane p : panes)
        {
            boolean visible = (p == target);
            p.setVisible(visible);
            p.setManaged(visible);
        }
    }

    @FXML
    private void onShowDashboard()
    {
        showOnly(paneDashboard);
    }

    @FXML
    private void onShowScanner()
    {
        showOnly(paneScanner);
    }

    @FXML
    private void onShowOverdueList()
    {
        showOnly(paneOverdueList);
    }

    @FXML
    private void onRunOverdueDetection()
    {

        int before = (int) allMedia.stream().filter(m -> "Overdue".equalsIgnoreCase(m.getStatus())).count();

        for (MediaItem m : allMedia)
        {
            if ("Borrowed".equalsIgnoreCase(m.getStatus()) && !"-".equals(m.getDueDate()))
            {
                m.setStatus("Overdue");
            }
        }

        int after = (int) allMedia.stream().filter(m -> "Overdue".equalsIgnoreCase(m.getStatus())).count();

        rebuildOverdueList();
        updateDashboardStats();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        lblLastRun.setText("Last run: " + LocalDateTime.now().format(fmt));
        lblScannerSummary.setText("Overdue found: " + after + " item(s)");

        info("Overdue Detection","Overdue scan completed (demo).\nNew overdue count: " + after);
    }

    private void rebuildOverdueList()
    {
        overdueItems.clear();
        for (MediaItem m : allMedia)
        {
            if ("Overdue".equalsIgnoreCase(m.getStatus()))
            {
                overdueItems.add(
                        new OverdueItem(m.getType(), m.getTitle(), m.getUserEmail(), m.getDueDate(), m.getStatus()));
            }
        }
    }

    @FXML
    private void onLogout()
    {
        try
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnDashboard.getScene().getWindow();
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
        private String userEmail;

        public MediaItem(String type, String title, String author, String isbn, String status, String dueDate,
                String userEmail)
        {
            this.type = type;
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.status = status;
            this.dueDate = dueDate;
            this.userEmail = userEmail;
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

        public String getUserEmail()
        {
            return userEmail;
        }

        public void setUserEmail(String userEmail)
        {
            this.userEmail = userEmail;
        }
    }

    public static class OverdueItem
    {
        private final String type;
        private final String title;
        private final String userEmail;
        private final String dueDate;
        private final String status;

        public OverdueItem(String type, String title, String userEmail, String dueDate, String status)
        {
            this.type = type;
            this.title = title;
            this.userEmail = userEmail;
            this.dueDate = dueDate;
            this.status = status;
        }

        public String getType()
        {
            return type;
        }

        public String getTitle()
        {
            return title;
        }

        public String getUserEmail()
        {
            return userEmail;
        }

        public String getDueDate()
        {
            return dueDate;
        }

        public String getStatus()
        {
            return status;
        }
    }
}
