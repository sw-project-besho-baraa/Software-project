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
import org.springframework.stereotype.Component;

@Component
public class UserController {



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
    private Label feinInDollar;

    @FXML
    private Label fine;

    @FXML
    private Button homeButton;

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
    private ComboBox<?> searchList;

    @FXML
    private AnchorPane searchPage;

    @FXML
    private TableColumn<?, ?> selectCustomerColumn;

    @FXML
    private Label userEmailField;

    @FXML
    private Label userNameTestField;




    void setAllVisibleFalse(){
        homePage.setVisible(false);
        searchPage.setVisible(false);
        payFinePage.setVisible(false);
    }


    @FXML
    void printDetails(ActionEvent event) {

    }

    @FXML
    void borrowItemButton(ActionEvent event) {

    }

    @FXML
    void homeButtonClick(ActionEvent event) {
        setAllVisibleFalse();
        homePage.setVisible(true);

    }

    @FXML
    void logoutButton(ActionEvent event) {
        System.out.println("Logout button clicked");



    }

    @FXML
    void myItemsButton(ActionEvent event) {

    }

    @FXML
    void payButton(ActionEvent event) {

    }

    @FXML
    void payFineButton(ActionEvent event) {
        setAllVisibleFalse();
        payFinePage.setVisible(true);
    }

    @FXML
    void returnItemButton(ActionEvent event) {

    }

    @FXML
    void searchButton(ActionEvent event) {
        setAllVisibleFalse();
        searchPage.setVisible(true);
    }

    @FXML
    void searchByButton(ActionEvent event) {

    }

    @FXML
    void viewAllButton(ActionEvent event) {

    }


}
