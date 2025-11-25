package Service;

import Session.ISessionManager;

/**
 * Service responsible for terminating the current user session.
 * <p>
 * This service delegates the logout operation to the configured
 * {@link ISessionManager}.
 */
public class LogoutService
{

    private final ISessionManager sessionManager;

    /**
     * Creates a new LogoutService.
     *
     * @param sessionManager
     *            the session manager used to perform logout operations
     */
    public LogoutService(ISessionManager sessionManager)
    {
        this.sessionManager = sessionManager;
    }

    /**
     * Logs out the current user by delegating to the {@link ISessionManager}.
     * <p>
     * If no session is active, this method is a no-op.
     */
    public void logout()
    {
        sessionManager.logout();
    }

    /**
     * Returns the session manager used by this service.
     *
     * @return the session manager instance
     */
    public ISessionManager getSessionManager()
    {
        return sessionManager;
    }
}
