package Service.UserService;

import Entity.User;
import Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnregisterUserService {

    private final UserRepository userRepository;

    public UnregisterUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void unregisterUser(User user) {
        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + user.getId()));

        if (managedUser.getBorrowedItems() != null && !managedUser.getBorrowedItems().isEmpty()) {
            throw new IllegalStateException("User has borrowed items and cannot be unregistered");
        }

        userRepository.delete(managedUser);
    }
}