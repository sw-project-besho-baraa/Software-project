package AuthenticationTests;
import org.Library.BLL.Services.Classes.AuthenticationService;
import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminAuthenticationTest {

    UserRepository repo = new UserRepository();
    AuthenticationService auth = new AuthenticationService(repo);

    private void createData(String userName, String password,String Role) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(Role);
        repo.saveUser(user);
    }

    @Test
    void loginInvalidRequest(){
        auth.login(null);
        assertEquals(auth.getErrorMassage(),"Invalid request" );
    }
    @Test
    void loginReturnsTrueForAdmin() {
        createData("besho","1234","admin");
        assertTrue(auth.login(new LoginRequest("besho","1234")).equals("admin"));
    }

    @Test
    void loginUserNotFoundForAdmin() {
        createData("besho","1234","admin");
        auth.login(new LoginRequest("bara","1234"));
        assertEquals(auth.getErrorMassage(),"User Not Found");
    }
    @Test
    void loginReturnsIncorrectPasswordForUser() {
        createData("besho","1234","admin");
        auth.login(new LoginRequest("besho","12"));
        assertEquals(auth.getErrorMassage(),"Incorrect Password");
    }
    @Test
    void loginReturnsNotAdmin() {
        createData("User","1234","user");
        auth.login(new LoginRequest("User","1234"));
        assertTrue(auth.isLoggedIn()&&!auth.isAdmin());
    }

    @Test
    void loginReturnsAlreadyLoggedInAsAdmin() {
        createData("Mohammad","besho","admin");
        auth.login(new LoginRequest("Mohammad","besho"));
        assertTrue(auth.isLoggedIn()&& auth.isAdmin());
    }

    @Test
    void loginReturnsAlreadyLoggedInAsUser() {
        createData("User","1234","user");
        auth.login(new LoginRequest("User","1234"));
        assertTrue(auth.isLoggedIn()&& !auth.isAdmin());
    }

    @Test
    void logoutBeforLogin(){
        auth.logout();
        assertEquals(auth.getErrorMassage(),"You are not logged in");
    }

    @Test
    void logoutAfterLogin(){
        createData("User","1234","user");
        auth.login(new LoginRequest("User","1234"));
        assertTrue(auth.logout());
    }




}
