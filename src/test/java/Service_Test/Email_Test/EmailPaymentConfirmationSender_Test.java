package Service_Test.Email_Test;


import DTO.UserDTO.UserContactDTO;
import Service.Email.EmailPaymentConfirmationSender;
import Service.Email.EmailService;
import Service.MediaItem.Payment.PaymentConfirmationData;
import Util.MessageFormater.IMessageFormater;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class EmailPaymentConfirmationSender_Test {

    @Test
    void send_validUser_sendsEmail() {
        EmailService emailService = mock(EmailService.class);
        IMessageFormater<PaymentConfirmationData> formater = mock(IMessageFormater.class);
        when(formater.formatMessage(any())).thenReturn("<html>ok</html>");
        EmailPaymentConfirmationSender sender = new EmailPaymentConfirmationSender(emailService, formater);

        UserContactDTO user = new UserContactDTO();
        user.setEmail("besho@mail.com");
        PaymentConfirmationData data = new PaymentConfirmationData(
                "bbbbbbbbbbbbbb", "kjkjjk", "kjkjkjkj", "kjkjkj", "bjbkjk",
                "kjkj", "bjbjbj", "kkjk", "kjkjkjjk", "kjkj"
        );
        sender.send(user, data);
        verify(emailService).sendEmail(eq("besho@mail.com"), contains("Payment confirmation"), anyString());
    }

    @Test
    void send_nullOrEmptyEmail_doesNothing() {
        EmailService emailService = mock(EmailService.class);
        IMessageFormater<PaymentConfirmationData> formater = mock(IMessageFormater.class);
        EmailPaymentConfirmationSender sender = new EmailPaymentConfirmationSender(emailService, formater);
        sender.send(null, null);
        UserContactDTO user = new UserContactDTO();
        user.setEmail(" ");
        sender.send(user, null);
        verifyNoInteractions(emailService, formater);
    }
}