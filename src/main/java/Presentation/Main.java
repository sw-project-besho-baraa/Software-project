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

@EnableJpaRepositories(basePackages = {"Repository"})
@EntityScan(basePackages = {"Entity"})
@SpringBootApplication(scanBasePackages = {"Presentation", "Service", "Repository", "Validation", "Util", "Session", "DTO"})
public class Main extends javafx.application.Application {
    private ConfigurableApplicationContext springContext;
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

    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//@Component
//class DataLoader implements CommandLineRunner
//{
//
//    private final UserRepository userRepo;
//    private final CdRepository cdRepo;
//    public DataLoader(UserRepository userRepo, BookRepository bookRepo, CdRepository cdRepo)
//    {
//        this.userRepo = userRepo;
//        this.cdRepo = cdRepo;
//        // this.itemRepo = itemRepo;
//    }
//
//    @Override
//    @Transactional
//    public void run(String... args) throws Exception
//    {
//        User user = new User("Alice", "alice@exadmsfsdffdfdspsfdsfdfffle.com", "SDf");
//        user.setUserRole(UserRole.User);
//
//        Cd cd = new Cd("The Dark Tower");
//
//        // cd.setBorrower(user);
//        cd.setBorrowed(false);
//        cdRepo.save(cd);
//        // user.getBorrowedItems().add(cd);
//        // userRepo.save(user);
//    }
//}
