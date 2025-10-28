package PLTests;
import org.Library.BLL.Services.Classes.AuthenticationService;
import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Data.Users;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;
import org.Library.PL.Areas.Identity.LoginPageController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class LoginControllerTest {
    private IAuthenticationService authService;
    private LoginPageController controller;
    @BeforeEach
    void setup() {
        Users.UsersList.clear();
        IUserRepository userRepo = new UserRepository();
        authService = new AuthenticationService(userRepo);
        controller = new LoginPageController(authService);
        User admin = new User();
        admin.setUserName("admin");
        admin.setPassword("admin");
        admin.setRole("admin");
        userRepo.saveUser(admin);
    }
    @Test
    void testLogin() {
        assertEquals(controller.Login("admin", "admin"),"admin");
    }

}
