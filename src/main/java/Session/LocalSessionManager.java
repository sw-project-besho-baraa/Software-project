package Session;

import Entity.User;

/**
 * Simple in-memory session manager that stores the current user in a local
 * field.
 * <p>
 * Suitable for desktop applications, CLI tools, or simple prototypes. Not
 * suitable for multi-user server environments.
 */
public class LocalSessionManager implements ISessionManager
{
    private User user;

    /**
     * Stores the given user as the current session.
     *
     * @param user
     *            the authenticated user
     */
    @Override
    public void login(User user)
    {
        if(user==null){
            throw new IllegalArgumentException("User cannot be null");
        }
        this.user = user;
    }

    /**
     * Clears the current session.
     */
    @Override
    public void logout()
    {
        this.user = null;
    }

    /**
     * Returns whether a user is currently associated with the session.
     *
     * @return true if a user is set; false otherwise
     */
    @Override
    public boolean isLoggedIn()
    {
        return user != null;
    }

    /**
     * Gets the current session user.
     *
     * @return the current user, or null if no session is active
     */
    @Override
    public User getUser()
    {
        return this.user;
    }
}
