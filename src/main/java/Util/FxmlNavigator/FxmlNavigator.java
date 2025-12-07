package Util.FxmlNavigator;

import Util.FxmlMapper.RoleToFxmlMapper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class FxmlNavigator {

    private final RoleToFxmlMapper roleToFxmlMapper;

    public FxmlNavigator(RoleToFxmlMapper roleToFxmlMapper) {
        this.roleToFxmlMapper = roleToFxmlMapper;
    }


    public void navigateTo(Stage stage, String fxmlPath, String title) {
        try {
            URL fxmlResource = getClass().getResource(fxmlPath);
            if (fxmlResource == null) {
                throw new IllegalArgumentException("FXML file not found: " + fxmlPath);
            }
            FXMLLoader loader = new FXMLLoader(fxmlResource);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error during navigation: " + fxmlPath);
            e.printStackTrace();
        }
    }
    public void logout(Stage stage, String loginFxmlPath) {
        navigateTo(stage, loginFxmlPath, "Login");
    }
}