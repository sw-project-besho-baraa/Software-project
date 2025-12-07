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

public class AdminController {

    @FXML
    private AnchorPane addProduct;

    @FXML
    private AnchorPane addUserPage;

    @FXML
    private AnchorPane adminMainPage;

    @FXML
    private Label adminNameDisplay;

    @FXML
    private AnchorPane sendEmailPage;

    @FXML
    private AnchorPane viewItemPage;

    @FXML
    private TextField addUserEmail;

    @FXML
    private TextField addUserPassword;

    @FXML
    private TextField addUserRole;

    @FXML
    private TextField emailMessage;

    @FXML
    private TableColumn<?, ?> employeeFirstNameColumn;

    @FXML
    private TableColumn<?, ?> employeeFirstNameColumn1;

    @FXML
    private TableColumn<?, ?> employeeIdColumn;

    @FXML
    private TextField itemSearch;

    @FXML
    private TableView<?> itemTable;

    @FXML
    private Label numberOfBooks;

    @FXML
    private Label numberOfCds;

    @FXML
    private Label numberOfCustomers;

    @FXML
    private Button pageHome;

    @FXML
    private ComboBox<?> searchList;

    @FXML
    private TableColumn<?, ?> selectEmployeeComlumn;

    @FXML
    void searchButton(ActionEvent event) {

    }

    @FXML
    void addBookPageButton(ActionEvent event) {

    }

    @FXML
    void addUserPageButton(ActionEvent event) {

    }

    @FXML
    void homePageButton(ActionEvent event) {

    }

    @FXML
    void itemSearchMethod(ActionEvent event) {

    }

    @FXML
    void logoutButton(ActionEvent event) {

    }

    @FXML
    void saveUserButton(ActionEvent event) {

    }

    @FXML
    void sendEmail(ActionEvent event) {

    }

    @FXML
    void sendEmailPageButton(ActionEvent event) {

    }

    @FXML
    void sendOverDueButton(ActionEvent event) {

    }

    @FXML
    void viewAllButton(ActionEvent event) {

    }

    @FXML
    void viewItemButton(ActionEvent event) {

    }

}
