package Presentation;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LibrarianController {



    @FXML
    private TableColumn<?, ?> customerBirthDateColumn;

    @FXML
    private TableColumn<?, ?> customerCityColumn;

    @FXML
    private TableColumn<?, ?> customerFirstNameColumn;

    @FXML
    private TableColumn<?, ?> customerGenderColumn;

    @FXML
    private TableColumn<?, ?> customerIdColumn;

    @FXML
    private Label fine;

    @FXML
    private AnchorPane homePage;

    @FXML
    private TableView<?> itemTable;

    @FXML
    private Button logoutButton;

    @FXML
    private Label numberOfBooks;

    @FXML
    private Label numberOfOverDue;

    @FXML
    private TextField searchBar;

    @FXML
    private ComboBox<?> searchList;

    @FXML
    private AnchorPane searchPage;

    @FXML
    private TableColumn<?, ?> selectCustomerColumn;

    @FXML
    private Label userNameTestField;

    @FXML
    void detectOverDue(ActionEvent event) {

    }

    @FXML
    void homeButtonClick(ActionEvent event) {

    }

    @FXML
    void logoutButton(ActionEvent event) {

    }

    @FXML
    void searchButton(ActionEvent event) {

    }

    @FXML
    void searchByButton(ActionEvent event) {

    }

    @FXML
    void viewAllButton(ActionEvent event) {

    }

}
