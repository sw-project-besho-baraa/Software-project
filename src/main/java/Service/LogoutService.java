package Service;

import Session.ISessionManager;
import lombok.Getter;
import org.springframework.stereotype.Service;

/**
 * Handles terminating the current user session.
 * <p>
 * This service delegates the logout process to the configured
 * {@link ISessionManager}.
 */
@Getter
@Service
public class LogoutService
{

    private final ISessionManager sessionManager;

    /**
     * Creates a new logout service with the given session manager.
     *
     * @param sessionManager
     *            the session manager used to perform logout operations
     */
    public LogoutService(ISessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    /**
     * Logs out the current user by delegating to {@link ISessionManager#logout()}.
     * <p>
     * If no session is active, this method performs no action.
     */
    public void logout()
    {
        sessionManager.logout();
    }

    /**
     * @return the session manager instance used by this service
     */
}
