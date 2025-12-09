package Util.FxmlNavigator;

import Util.FxmlMapper.RoleToFxmlMapper;
import Util.SpringFXMLLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class FxmlNavigator
{

    private final RoleToFxmlMapper roleToFxmlMapper;

    public FxmlNavigator(RoleToFxmlMapper roleToFxmlMapper)
    {
        this.roleToFxmlMapper = roleToFxmlMapper;
    }

    public void navigateTo(Stage stage,String fxmlPath,String title)
    {
        try
        {
            FXMLLoader loader = SpringFXMLLoader.load(fxmlPath);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e)
        {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public void logout(Stage stage,String loginFxmlPath)
    {
        navigateTo(stage,loginFxmlPath,"Login");
    }

    public <T> T openInNewWindow(String fxmlPath,String title)
    {
        try
        {
            FXMLLoader loader = SpringFXMLLoader.load(fxmlPath);
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
            return loader.getController();
        } catch (IOException e)
        {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
            return null;
        }
    }
}