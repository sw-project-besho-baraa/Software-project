package Service.Email;
import DTO.UserDTO.UserContactDTO;
import Service.Book.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.Book.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Service.NotificationSender.INotificationSender;
import Util.MessageFormater.IMessageFormater;

import java.util.List;

public class EmailOverdueBorrowNotificationSender implements INotificationSender<UserContactDTO, List<OverdueBorrowedItem>> {
    private final EmailService emailService;
    private final IMessageFormater<OverdueBorrowedItemsData> messageFormater;
    public EmailOverdueBorrowNotificationSender(EmailService emailService, IMessageFormater<OverdueBorrowedItemsData> messageFormater) {
        this.emailService = emailService;
        this.messageFormater = messageFormater;
    }
    @Override
    public void send(UserContactDTO user, List<OverdueBorrowedItem> items) {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank()) {return;}
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, items);
        String htmlBody = messageFormater.formatMessage(data);
        String subject = "Library Management System· Overdue items notice";
        emailService.sendEmail(user.getEmail(), subject, htmlBody);
    }
}
