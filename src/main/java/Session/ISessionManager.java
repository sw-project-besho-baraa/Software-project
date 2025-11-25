package Session;

import Entity.User;

/**
 * Abstraction for managing the lifecycle of a user session.
 * <p>
 * Implementations can vary (local, distributed, HTTP, etc.) but must provide a
 * consistent API for logging in/out and querying session state.
 */
public interface ISessionManager
{
    /**
     * Starts a session for the specified user.
     *
     * @param user
     *            the authenticated user for whom the session will be started
     */
    void login(User user);

    /**
     * Terminates the current session, if any.
     */
    void logout();

    /**
     * Indicates whether there is an active session.
     *
     * @return true if a user is currently logged in; false otherwise
     */
    boolean isLoggedIn();

    /**
     * Returns the user associated with the current session, if any.
     *
     * @return the current session user, or null if no session is active
     */
    User getUser();
}
