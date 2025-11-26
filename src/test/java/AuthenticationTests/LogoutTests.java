package AuthenticationTests;
import DTO.UserDTO;
import Entity.User;
import FakeHashing.FakeHashingPassword;
import FakeRepository.FakeUserRepository;
import FakeSession.FakeSessionManager;
import FakeValidation.FakeLoginCredentialsValidator;
import Service.LoginService;
import Service.LogoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class LogoutTests {
    private LogoutService logoutService;
    private FakeSessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new FakeSessionManager();
        sessionManager.logout();
        logoutService = new LogoutService(sessionManager);
    }

    /**
     * Scenario 1:
     * When no user is logged in calls logout()
     * should keep the session in not logged in.
     */
    @Test
    void logoutDoesNothingWhenNoUserLoggedIn() {
        assertFalse(sessionManager.isLoggedIn(), "no user logged in initially");
        assertNull(sessionManager.getUser(), "session user should be null");
        logoutService.logout();
        assertFalse(sessionManager.isLoggedIn(), "No user should be logged in after logout()");
        assertNull(sessionManager.getUser(), "Session should remain empty after logout()");
    }
    /**
     * Scenario 2:
     * When a user is logged in  calls logout()
     * should clear the current session.
     */
    @Test
    void logoutClearsActiveSession() {
        User user = new User();
        user.setEmail("bishawi@gmail.com");
        sessionManager.login(user);
        assertTrue(sessionManager.isLoggedIn(), "user should be logged in before logout()");
        assertEquals(user, sessionManager.getUser(), "session should store the loggedin user");
        logoutService.logout();
        assertFalse(sessionManager.isLoggedIn(), "User should not be logged in after logout()");
        assertNull(sessionManager.getUser(), "Session should not store any user after logout()");
    }

    /**
     * Scenario 3:
     * getSessionManager() should return the same instance
     * that was passed to LogoutService.
     */
    @Test
    void getSessionManagerReturnsSameInstance() {
        assertSame(sessionManager, logoutService.getSessionManager(),"LogoutService should expose the same session manager instance");
    }
}
