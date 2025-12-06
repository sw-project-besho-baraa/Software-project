package Presentation;

import Entity.User;
import Enum.UserRole;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController
{

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label statusLabel;

    @FXML
    private void onLoginClick()
    {
        String email = emailField.getText();
        String password = passwordField.getText();
        UserRole role = null;
        if (email.equals("admin@mobo.com") && password.equals("1234"))
        {
            role = UserRole.Admin;
        } else if (email.equals("librarian@mobo.com") && password.equals("1234"))
        {
            role = UserRole.Librarian;
        } else if (email.equals("user@mobo.com") && password.equals("1234"))
        {
            role = UserRole.User;
        }

        if (role == null)
        {
            statusLabel.setText("Invalid email or password ");
            return;
        }

        User user = new User("Static User", email, password);
        user.setUserRole(role);
        statusLabel.setText("Login successful  (" + role + ")");
        openDashboardForRole(role);
    }

    private void openDashboardForRole(UserRole role)
    {
        String fxmlPath;
        String title;

        switch (role) {
            case Admin -> {
                fxmlPath = "/AdminDashboard.fxml";
                title = "Admin Dashboard";
            }
            case Librarian -> {
                fxmlPath = "/LibrarianDashboard.fxml";
                title = "Librarian Dashboard";
            }
            case User -> {
                fxmlPath = "/UserDashboard.fxml";
                title = "User Dashboard";
            }
            default -> {
                statusLabel.setText("Unknown role ");
                return;
            }
        }

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();

        } catch (IOException e)
        {
            e.printStackTrace();
            statusLabel.setText("Failed to open dashboard");
        }
    }
}
