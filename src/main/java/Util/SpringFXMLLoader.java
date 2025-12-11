package Util;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;

import java.net.URL;

/**
 * Utility class that integrates Spring's {@link ApplicationContext}
 * with JavaFX's {@link FXMLLoader}.
 * <p>
 * This allows Spring to manage controller instances declared in FXML files,
 * enabling dependency injection in JavaFX applications.
 */
public final class SpringFXMLLoader {

    private static final ApplicationContext applicationContext =
            ApplicationContextProvider.getApplicationContext();

    private SpringFXMLLoader() {
    }

    /**
     * Creates and configures a {@link FXMLLoader} for the given FXML file path.
     *
     * @param fxmlPath the classpath-relative path to the FXML file (e.g. "/fxml/login.fxml")
     * @return a configured {@link FXMLLoader} instance ready to load
     * @throws IllegalArgumentException if the FXML resource cannot be found
     */
    public static FXMLLoader load(String fxmlPath) {
        URL resource = SpringFXMLLoader.class.getResource(fxmlPath);
        if (resource == null) {
            throw new IllegalArgumentException("FXML file not found at path: " + fxmlPath);
        }

        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(applicationContext::getBean);
        return loader;
    }
}
