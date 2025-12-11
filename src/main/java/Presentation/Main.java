package Presentation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main entry point for the JavaFX + Spring Boot application.
 * <p>
 * Initializes Spring context, loads the Login.fxml, and starts the UI.
 */
@EnableScheduling
@EnableJpaRepositories(basePackages = {"Repository"})
@EntityScan(basePackages = {"Entity"})
@SpringBootApplication(scanBasePackages = {
        "Presentation", "Service", "Repository", "Validation", "Util", "Session", "DTO"
})
public class Main extends javafx.application.Application {

    private ConfigurableApplicationContext springContext;

    /**
     * Starts the application and shows the login window.
     *
     * @param primaryStage main JavaFX stage
     * @throws Exception if FXML loading fails
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        springContext = new SpringApplicationBuilder(Main.class).run();
        loader.setControllerFactory(springContext::getBean);

        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Login Application");
        primaryStage.show();
    }

    /**
     * Closes the Spring context on exit.
     *
     * @throws Exception if closing fails
     */
    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close();
        }
    }

    /**
     * Launches the application.
     *
     * @param args program arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
