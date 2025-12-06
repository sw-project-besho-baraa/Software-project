package Presentation;

import DTO.UserDTO.UserCredentialsDTO;
import Service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signInButton;

    @FXML
    private Label wrongUsernameOrPassword;

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @FXML
    void onSignIn(ActionEvent event) {
        String email = emailTextField.getText() != null ? emailTextField.getText().trim() : "";
        String password = passwordTextField.getText() != null ? passwordTextField.getText() : "";

        if (email.isEmpty() || password.isEmpty()) {
            showError("Please enter both email and password.");
            return;
        }

        UserCredentialsDTO credentials = new UserCredentialsDTO(email, password);

        boolean success = loginService.login(credentials);

        if (!success) {
            showError("Wrong email or password.");
            return;
        }

        openUserScreen();
    }

    private void showError(String message) {
        if (wrongUsernameOrPassword != null) {
            wrongUsernameOrPassword.setText(message);
            wrongUsernameOrPassword.setVisible(true);
        }
    }

    private void openUserScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/User.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) signInButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("User Dashboard");
            stage.centerOnScreen();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error loading user screen.");
        }
    }
}
