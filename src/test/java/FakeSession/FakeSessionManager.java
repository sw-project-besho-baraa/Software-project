package FakeSession;

import Entity.User;
import Session.ISessionManager;

public class FakeSessionManager implements ISessionManager {

    private static User currentUser;
    @Override
    public void login(User user) {
        currentUser = user;
        System.out.println("User logged in: " + user.getEmail());
    }

    @Override
    public void logout() {
        System.out.println("User logged out: " + (currentUser != null ? currentUser.getEmail() : "none"));
        currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    @Override
    public User getUser() {
        return currentUser;
    }
}