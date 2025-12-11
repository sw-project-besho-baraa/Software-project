package Service.UserService;

import Entity.User;
import Repository.UserRepository;
import Util.HashingPassword.BCryptHashingPassword;
import Util.HashingPassword.IHashingPassword;
import org.springframework.stereotype.Service;
import Enum.UserRole;

/**
 * Handles the creation and registration of new users in the system.
 */
@Service
public class AddUserService
{

    private final UserRepository userRepository;
    private final IHashingPassword hashingPassword;

    /**
     * Creates a new service for adding users.
     *
     * @param userRepository
     *            repository for saving users
     * @param hashingPassword
     *            password hashing utility
     */
    public AddUserService(UserRepository userRepository, BCryptHashingPassword hashingPassword)
    {
        this.userRepository = userRepository;
        this.hashingPassword = hashingPassword;
    }

    /**
     * Adds a new user to the system.
     *
     * @param name
     *            user's name
     * @param email
     *            user's email address
     * @param password
     *            raw password (will be hashed)
     * @param role
     *            user's assigned role
     */
    public void addUser(String name,String email,String password,UserRole role)
    {
        password = hashingPassword.hashPassword(password);
        User user = new User(name, email, password);
        user.setUserRole(role);
        userRepository.save(user);
    }
}
