package Session;

import Entity.User;

public class LocalSessionManager implements ISessionManager
{
    private User user;

    @Override
    public void login(User user)
    {
        this.user = user;
    }

    @Override
    public void logout()
    {
        this.user = null;
    }

    @Override
    public boolean isLoggedIn()
    {
        return user != null;
    }

    @Override
    public User getUser()
    {
        return this.user;
    }
}
