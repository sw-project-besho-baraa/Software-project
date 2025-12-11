package Service.UserService;

import Entity.User;
import Repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for retrieving all users from the database.
 */
@Service
public class GetAllUsersService
{

    private final UserRepository userRepository;

    /**
     * Creates a new instance using the given repository.
     *
     * @param userRepository
     *            repository for accessing user data
     */
    public GetAllUsersService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * @return a list of all users
     */
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }
}
