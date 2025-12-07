package Presentation;

import DTO.UserDTO.UserCredentialsDTO;
import Entity.User;
import Service.LoginService;
import Session.ISessionManager;
import Util.FxmlMapper.RoleToFxmlMapper;
import Util.FxmlNavigator.FxmlNavigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private final RoleToFxmlMapper roleToFxmlMapper;
    private final ISessionManager sessionManager;
    private final FxmlNavigator fxmlNavigator;

    @Autowired
    public LoginController(LoginService loginService, RoleToFxmlMapper roleToFxmlMapper, ISessionManager sessionManager, FxmlNavigator fxmlNavigator) {
        this.loginService = loginService;
        this.roleToFxmlMapper = roleToFxmlMapper;
        this.sessionManager = sessionManager;
        this.fxmlNavigator = fxmlNavigator;
    }

    @FXML
    void onSignIn(ActionEvent event) {
        System.out.println("Sign In button clicked");
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        UserCredentialsDTO userDTO = new UserCredentialsDTO(email, password);
        boolean loginSuccessful = loginService.login(userDTO);
        if (loginSuccessful) {
            wrongUsernameOrPassword.setText("Login successful!");
            User currentUser = sessionManager.getUser();
            System.out.println(currentUser.getUserRole());
            String fxmlPath = roleToFxmlMapper.getFxmlFileForRole(currentUser.getUserRole()).orElse("/fxml/Login.fxml");
            fxmlNavigator.navigateTo((Stage) signInButton.getScene().getWindow(), fxmlPath, "Main Application");
        } else {
            wrongUsernameOrPassword.setText("Wrong username or password.");
        }
    }
}