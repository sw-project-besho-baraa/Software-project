package Presentation;

import Entity.Book;
import Entity.Cd;
import Entity.User;
import Enum.UserRole;
import Repository.BookRepository;
import Repository.CdRepository;
import Repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"Repository"})
@EntityScan(basePackages = {"Entity"})
public class Main
{

    public static void main(String[] args)
    {
        // launch(args);
        SpringApplication.run(Main.class,args);

    }
    //
    // @Override
    // public void start(Stage stage) throws Exception {
    // FXMLLoader fxmlLoader = new
    // FXMLLoader(getClass().getResource("/login.fxml"));
    // Scene scene = new Scene(fxmlLoader.load());
    // stage.setScene(scene);
    // stage.setTitle("MOBO Library · Login");
    // stage.show();
    // }
}

@Component
class DataLoader implements CommandLineRunner
{

    private final UserRepository userRepo;
    private final CdRepository cdRepo;
    public DataLoader(UserRepository userRepo, BookRepository bookRepo, CdRepository cdRepo)
    {
        this.userRepo = userRepo;
        this.cdRepo = cdRepo;
        // this.itemRepo = itemRepo;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception
    {
        User user = new User("Alice", "alice@exadmsfsdffdfdspsfdsfdfffle.com", "SDf");
        user.setUserRole(UserRole.User);

        Cd cd = new Cd("The Dark Tower");

        // cd.setBorrower(user);
        cd.setBorrowed(false);
        cdRepo.save(cd);
        // user.getBorrowedItems().add(cd);
        // userRepo.save(user);
    }
}
