package Service.Email;

import Entity.User;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Service.NotificationSender.INotificationSender;
import Util.MessageFormater.IMessageFormater;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Sends overdue borrow notifications to users via email.
 */
@Component
public class EmailOverdueBorrowNotificationSender implements INotificationSender<User, List<OverdueBorrowedItem>> {

    private final EmailService emailService;
    private final IMessageFormater<OverdueBorrowedItemsData> messageFormater;

    /**
     * Creates a new overdue borrow email sender.
     *
     * @param emailService email sending service
     * @param messageFormater formatter for overdue message templates
     */
    public EmailOverdueBorrowNotificationSender(EmailService emailService,
                                                IMessageFormater<OverdueBorrowedItemsData> messageFormater) {
        this.emailService = emailService;
        this.messageFormater = messageFormater;
    }

    /**
     * Sends an overdue notice email to the specified user.
     *
     * @param user user receiving the notification
     * @param items list of overdue borrowed items
     * @see EmailService
     */
    @Override
    public void send(User user, List<OverdueBorrowedItem> items) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {
            return;
        }

        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, items);
        String htmlBody = messageFormater.formatMessage(data);
        String subject = "Library Management System · Overdue items notice";

        emailService.sendEmail(user.getEmail(), subject, htmlBody);
    }
}
