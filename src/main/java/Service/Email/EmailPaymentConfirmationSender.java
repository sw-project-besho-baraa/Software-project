package Service.Email;

import DTO.UserDTO.UserContactDTO;
import Service.Payment.PaymentConfirmationData;
import Service.NotificationSender.INotificationSender;
import Util.MessageFormater.IMessageFormater;

/**
 * Sends payment confirmation emails to users.
 */
public class EmailPaymentConfirmationSender implements INotificationSender<UserContactDTO, PaymentConfirmationData>
{

    private final EmailService emailService;
    private final IMessageFormater<PaymentConfirmationData> messageFormater;

    /**
     * Creates a new payment confirmation email sender.
     *
     * @param emailService
     *            email sending service
     * @param messageFormater
     *            formatter for confirmation messages
     */
    public EmailPaymentConfirmationSender(EmailService emailService,
            IMessageFormater<PaymentConfirmationData> messageFormater)
    {
        this.emailService = emailService;
        this.messageFormater = messageFormater;
    }

    /**
     * Sends a formatted payment confirmation email to the given user.
     *
     * @param user
     *            user contact information
     * @param data
     *            payment confirmation details
     * @see EmailService
     */
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
