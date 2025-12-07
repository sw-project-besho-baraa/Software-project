package Util;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class SpringFXMLLoader {

    private static final ApplicationContext applicationContext;

    static {
        // Assuming the Spring application context is already initialized.
        applicationContext = ApplicationContextProvider.getApplicationContext();
    }

    public static FXMLLoader load(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();

        // Set controller factory to Spring for dependency injection
        loader.setControllerFactory(applicationContext::getBean);

        loader.setLocation(SpringFXMLLoader.class.getResource(fxmlPath));
        return loader;
    }
}