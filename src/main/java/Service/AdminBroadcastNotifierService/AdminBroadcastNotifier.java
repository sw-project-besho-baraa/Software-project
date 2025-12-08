package Service.AdminBroadcastNotifierService;

import DTO.UserDTO.UserContactDTO;
import Entity.User;
import Repository.UserRepository;
import Service.NotificationSender.INotificationSender;
import org.springframework.stereotype.Component;
import Enum.UserRole;

import java.util.List;

@Component
public class AdminBroadcastNotifier {

    private final UserRepository userRepository;
    private final INotificationSender<UserContactDTO, String> notifier;

    public AdminBroadcastNotifier(UserRepository userRepository,
                                  INotificationSender<UserContactDTO, String> notifier) {
        this.userRepository = userRepository;
        this.notifier = notifier;
    }

    public void sendToAll(String message) {
        if (message == null || message.isBlank()) {
            return;
        }
        List<User> users = userRepository.findAll();
        if (users == null || users.isEmpty()) {
            return;
        }

        for (User user : users) {
            if (user.getUserRole() != UserRole.User) {
                continue;
            }
            if (user.getEmail() == null || user.getEmail().isBlank()) {
                continue;
            }

            UserContactDTO contact = new UserContactDTO();
            contact.setName(user.getName());
            contact.setEmail(user.getEmail());
            notifier.send(contact, message);
        }
    }
}