package Session_Test;

import Entity.User;
import Session.LocalSessionManager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class LocalSession_Test
{
    private LocalSessionManager sessionManager;
    private User user;
    @BeforeEach
    void setUp()
    {
        sessionManager = new LocalSessionManager();
        user = mock(User.class);
    }

    @Test
    void IsLoggedIn_AfterValidLogin_ReturnsTrue()
    {
        sessionManager.login(user);
        assertTrue(sessionManager.isLoggedIn());
    }

    @Test
    void IsLoggedIn_AfterValidLogout_ReturnsFalse()
    {
        sessionManager.login(user);

        sessionManager.logout();
        assertFalse(sessionManager.isLoggedIn());
    }

    @Test
    void GetUser_AfterValidLogIn_ReturnsCurrentUser()
    {
        sessionManager.login(user);
        assertEquals(user,sessionManager.getUser());
    }

    @Test
    void logIn_WithInvalidUser_ThrowsException()
    {
        assertThrows(NullPointerException.class,() -> sessionManager.login(null));
    }

}
