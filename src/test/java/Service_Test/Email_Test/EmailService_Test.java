package Service_Test.Email_Test;

import Service.Email.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class EmailService_Test {

    @Test
    void sendEmail_sendsSuccessfully() throws Exception {
        try (MockedStatic<Transport> transport = mockStatic(Transport.class)) {
            transport.when(() -> Transport.send(any(MimeMessage.class))).thenAnswer(inv -> null);
            EmailService service = new EmailService("besho@gmail.com", "besho");
            service.sendEmail("besho@mail.com", "besho", "<b>besho</b>");
            transport.verify(() -> Transport.send(any(MimeMessage.class)));
        }
    }

    @Test
    void sendEmail_handlesFailure() throws Exception {
        try (MockedStatic<Transport> transport = mockStatic(Transport.class)) {
            transport.when(() -> Transport.send(any(MimeMessage.class))).thenThrow(new MessagingException("fail"));
            EmailService service = new EmailService("besho@gmail.com", "besho");
            try {service.sendEmail("besho@mail.com", "Hi", "<b>besho</b>");
            } catch (RuntimeException e) {assert e.getMessage().contains("Failed to send email");
            }
            transport.verify(() -> Transport.send(any(MimeMessage.class)));
        }
    }
}