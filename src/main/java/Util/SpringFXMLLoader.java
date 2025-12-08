package Util;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.io.InputStream;

public class SpringFXMLLoader {

    private static final ApplicationContext applicationContext;

    static {
        applicationContext = ApplicationContextProvider.getApplicationContext();
    }

    public static FXMLLoader load(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);

        loader.setLocation(SpringFXMLLoader.class.getResource(fxmlPath));
        return loader;
    }
}