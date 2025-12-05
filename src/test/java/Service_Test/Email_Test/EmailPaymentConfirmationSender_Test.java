package Service_Test.Email_Test;

import DTO.UserDTO.UserContactDTO;
import Service.Book.Payment.PaymentConfirmationData;
import Service.Email.EmailPaymentConfirmationSender;
import Service.Email.EmailService;
import Util.MessageFormater.IMessageFormater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class EmailPaymentConfirmationSender_Test {

    private EmailService emailService;
    private IMessageFormater<PaymentConfirmationData> messageFormater;
    private EmailPaymentConfirmationSender sender;

    @BeforeEach
    void setup() {
        emailService = mock(EmailService.class);
        messageFormater = mock(IMessageFormater.class);
        sender = new EmailPaymentConfirmationSender(emailService, messageFormater);
    }

    @Test
    void send_ValidUserAndData_SendsEmail() {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getEmail()).thenReturn("besho@gmail.com");
        PaymentConfirmationData data = mock(PaymentConfirmationData.class);
        String htmlBody = "<html>Payment confirmed</html>";
        when(messageFormater.formatMessage(data)).thenReturn(htmlBody);
        sender.send(user, data);
        verify(messageFormater, times(1)).formatMessage(data);
        verify(emailService, times(1)).sendEmail("besho@gmail.com", "Library Management System · Payment confirmation", htmlBody);
    }

    @Test
    void send_UserIsNull_DoesNotSendEmail() {
        PaymentConfirmationData data = mock(PaymentConfirmationData.class);
        sender.send(null, data);
        verifyNoInteractions(emailService, messageFormater);
    }

    @Test
    void send_UserEmailIsNull_DoesNotSendEmail() {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getEmail()).thenReturn(null);
        PaymentConfirmationData data = mock(PaymentConfirmationData.class);
        sender.send(user, data);
        verifyNoInteractions(emailService, messageFormater);
    }

    @Test
    void send_UserEmailIsBlank_DoesNotSendEmail() {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getEmail()).thenReturn("   ");
        PaymentConfirmationData data = mock(PaymentConfirmationData.class);
        sender.send(user, data);
        verifyNoInteractions(emailService, messageFormater);
    }
}
