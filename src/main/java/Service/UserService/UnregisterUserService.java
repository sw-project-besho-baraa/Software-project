package Service.UserService;

import Entity.User;
import Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

/**
 * Service for handling user unregistration.
 */
@Service
public class UnregisterUserService
{

    private final UserRepository userRepository;

    /**
     * Creates a new service for unregistering users.
     *
     * @param userRepository
     *            repository for user data
     */
    public UnregisterUserService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * Unregisters a user if they have no borrowed items or unpaid fines.
     *
     * @param user
     *            the user to unregister
     * @throws IllegalArgumentException
     *             if the user is not found
     * @throws IllegalStateException
     *             if the user still has borrowed items or unpaid fines
     */
    @Transactional
    public void unregisterUser(User user)
    {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + user.getId()));

        if (managedUser.getBorrowedItems() != null && !managedUser.getBorrowedItems().isEmpty())
        {
            throw new IllegalStateException("User has borrowed items and cannot be unregistered");
        }
        if (managedUser.getFineBalance() != null && managedUser.getFineBalance().compareTo(BigDecimal.ZERO) > 0)
        {
            throw new IllegalStateException("User has unpaid fines and cannot be unregistered");
        }

        userRepository.delete(managedUser);
    }
}
