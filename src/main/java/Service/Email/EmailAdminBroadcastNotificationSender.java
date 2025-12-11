package Service.Email;

import DTO.UserDTO.UserContactDTO;
import Service.BroadcastMessage.AdminBroadcastMessageData;
import Service.NotificationSender.INotificationSender;
import Util.MessageFormater.IMessageFormater;
import org.springframework.stereotype.Component;

/**
 * Sends admin broadcast messages via email.
 */
@Component
public class EmailAdminBroadcastNotificationSender implements INotificationSender<UserContactDTO, String> {

    private final EmailService emailService;
    private final IMessageFormater<AdminBroadcastMessageData> messageFormater;

    /**
     * Creates a new email broadcast sender.
     *
     * @param emailService email sending service
     * @param messageFormater formatter for broadcast messages
     */
    public EmailAdminBroadcastNotificationSender(EmailService emailService,
                                                 IMessageFormater<AdminBroadcastMessageData> messageFormater) {
        this.emailService = emailService;
        this.messageFormater = messageFormater;
    }

    /**
     * Sends a formatted broadcast email to the specified user.
     *
     * @param user target user contact info
     * @param message message content to send
     * @see EmailService
     */
    @Override
    public void send(UserContactDTO user, String message) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {
            return;
        }

        AdminBroadcastMessageData data = new AdminBroadcastMessageData(user, message);
        String htmlBody = messageFormater.formatMessage(data);
        String subject = "Admin Message from Library Management System";

        emailService.sendEmail(user.getEmail(), subject, htmlBody);
    }
}
