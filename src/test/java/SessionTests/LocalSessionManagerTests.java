package SessionTests;

import Entity.User;
import Session.LocalSessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocalSessionManagerTests {

    private LocalSessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = new LocalSessionManager();
    }

    /**
     * Scenario 1:
     * Initially, no user should be logged in.
     */
    @Test
    void noUserLoggedInInitially() {
        assertFalse(sessionManager.isLoggedIn(), "No user should be logged in initially");
        assertNull(sessionManager.getUser(), "getUser() should return null ");
    }

    /**
     * Scenario 2:
     * When login() is called with a valid user,
     * the session should store that user.
     */
    @Test
    void loginStoresUserCorrectly() {
        User user = new User();
        user.setEmail("bishawi@example.com");
        sessionManager.login(user);
        assertTrue(sessionManager.isLoggedIn(), "User should be marked as logged in");
        assertEquals(user, sessionManager.getUser(), "getUser() should return the logged-in user");
    }
    /**
     * Scenario 3:
     * When logout() is called, the session should be cleared.
     */
    @Test
    void logoutClearsUserSession() {
        User user = new User();
        user.setEmail("bishawi@example.com");
        sessionManager.login(user);
        assertTrue(sessionManager.isLoggedIn(), "User should be logged in before logout");
        sessionManager.logout();
        assertFalse(sessionManager.isLoggedIn(), "User should be logged out after logout()");
        assertNull(sessionManager.getUser(), "getUser() should return null after logout()");
    }
    /**
     * Scenario 4:
     * Logging in a new user should replace the old session user.
     */
    @Test
    void loginReplacesExistingUser() {
        User user1 = new User();
        user1.setEmail("bishawi@example.com");
        User user2 = new User();
        user2.setEmail("besho@example.com");
        sessionManager.login(user1);
        assertEquals(user1, sessionManager.getUser(), "First user should be logged in");
        sessionManager.login(user2);
        assertEquals(user2, sessionManager.getUser(), "Second user should replace the first one");
    }
}
