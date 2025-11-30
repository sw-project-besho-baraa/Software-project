package Service.LogoutService_Test;
import Service.LogoutService;
import Session.ISessionManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class LogoutService_Test {
    private ISessionManager sessionManager;
    private LogoutService logoutService;

    @BeforeEach
    void setUp() {
        sessionManager = mock(ISessionManager.class);
        logoutService = new LogoutService(sessionManager);
    }

    @Test
    void logout_callsSessionManagerLogout() {
        logoutService.logout();
        verify(sessionManager, times(1)).logout();
    }

    @Test
    void getSessionManager_returnsSameInstance() {
        ISessionManager result = logoutService.getSessionManager();
        assertSame(sessionManager, result);
    }
}
