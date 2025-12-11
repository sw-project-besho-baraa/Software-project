package Service.UserService;

import Repository.UserRepository;
import org.springframework.stereotype.Service;
import Enum.UserRole;

/**
 * Service for counting users based on their assigned roles.
 */
@Service
public class UserCountService
{

    private final UserRepository userRepository;

    /**
     * Creates a new service for counting users.
     *
     * @param userRepository
     *            repository for user data
     */
    public UserCountService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Counts the number of users with a specific role.
     *
     * @param userRole
     *            the role to count users for
     * @return number of users with the given role
     */
    public long countUsersByRole(UserRole userRole)
    {
        return userRepository.countByUserRole(userRole);
    }
}
