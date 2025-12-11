package Service.AdminBroadcastNotifierService;

import DTO.UserDTO.UserContactDTO;
import Entity.User;
import Repository.UserRepository;
import Service.NotificationSender.INotificationSender;
import org.springframework.stereotype.Component;
import Enum.UserRole;
import java.util.List;

/**
 * Handles admin broadcast messages to all regular users.
 */
@Component
public class AdminBroadcastNotifier {

    private final UserRepository userRepository;
    private final INotificationSender<UserContactDTO, String> notifier;

    /**
     * Creates a new notifier for sending admin broadcasts.
     *
     * @param userRepository repository for user data
     * @param notifier       notification sender implementation
     */
    public AdminBroadcastNotifier(UserRepository userRepository,
                                  INotificationSender<UserContactDTO, String> notifier) {
        this.userRepository = userRepository;
        this.notifier = notifier;
    }

    /**
     * Sends a message to all users with role {@link UserRole#User}.
     *
     * @param message text message to broadcast
     */
    public void sendToAll(String message) {
        if (message == null || message.isBlank()) return;

        List<User> users = userRepository.findAll();
        if (users == null || users.isEmpty()) return;

        for (User user : users) {
            if (user.getUserRole() != UserRole.User) continue;
            if (user.getEmail() == null || user.getEmail().isBlank()) continue;

            UserContactDTO contact = new UserContactDTO();
            contact.setName(user.getName());
            contact.setEmail(user.getEmail());
            notifier.send(contact, message);
        }
    }
}
