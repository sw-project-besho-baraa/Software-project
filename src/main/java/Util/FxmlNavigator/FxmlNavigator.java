package Util.FxmlNavigator;

import Util.FxmlMapper.RoleToFxmlMapper;
import Util.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Handles navigation between different FXML views in the application.
 * <p>
 * Provides methods to switch scenes, open new windows, and handle logout transitions.
 */
@Component
public class FxmlNavigator {

    private final RoleToFxmlMapper roleToFxmlMapper;

    /**
     * Creates a new navigator for switching between FXML views.
     *
     * @param roleToFxmlMapper mapper used for role-based FXML resolution
     */
    public FxmlNavigator(RoleToFxmlMapper roleToFxmlMapper) {
        this.roleToFxmlMapper = roleToFxmlMapper;
    }

    /**
     * Navigates to a given FXML file in the current stage.
     *
     * @param stage     the target stage
     * @param fxmlPath  the path to the FXML file
     * @param title     the window title
     */
    public void navigateTo(Stage stage, String fxmlPath, String title) {
        try {
            FXMLLoader loader = SpringFXMLLoader.load(fxmlPath);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }

    /**
     * Logs out by navigating back to the login screen.
     *
     * @param stage          the current stage
     * @param loginFxmlPath  path to the login FXML file
     * @see #navigateTo(Stage, String, String)
     */
    public void logout(Stage stage, String loginFxmlPath) {
        navigateTo(stage, loginFxmlPath, "Login");
    }

    /**
     * Opens a new window with the given FXML view.
     *
     * @param fxmlPath the FXML file path
     * @param title    the window title
     * @param <T>      the controller type
     * @return the controller of the loaded FXML, or null if loading fails
     */
    public <T> T openInNewWindow(String fxmlPath, String title) {
        try {
            FXMLLoader loader = SpringFXMLLoader.load(fxmlPath);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
            return loader.getController();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }
}
