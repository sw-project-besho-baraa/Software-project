package Service.Email;

import DTO.UserDTO.UserContactDTO;
import Service.MediaItem.Payment.PaymentConfirmationData;
import Service.NotificationSender.INotificationSender;
import Util.MessageFormater.IMessageFormater;

public class EmailPaymentConfirmationSender implements INotificationSender<UserContactDTO, PaymentConfirmationData>
{
    private final EmailService emailService;
    private final IMessageFormater<PaymentConfirmationData> messageFormater;
    public EmailPaymentConfirmationSender(EmailService emailService,
            IMessageFormater<PaymentConfirmationData> messageFormater)
    {
        this.emailService = emailService;
        this.messageFormater = messageFormater;
    }

    @Override
    public void send(UserContactDTO user,PaymentConfirmationData data)
    {
        if (user == null || user.getEmail() == null || user.getEmail().isBlank())
        {
            return;
        }
        String htmlBody = messageFormater.formatMessage(data);
        String subject = "Library Management System · Payment confirmation";
        emailService.sendEmail(user.getEmail(),subject,htmlBody);
    }
}
