package Session;

import Entity.User;

public interface ISessionManager
{
    void login(User user);

    void logout();

    boolean isLoggedIn();

    User getUser();
}
