package Service_Test.Email_Test;


import DTO.UserDTO.UserContactDTO;
import Service.BroadcastMessage.AdminBroadcastMessageData;
import Service.Email.EmailAdminBroadcastNotificationSender;
import Service.Email.EmailService;
import Util.MessageFormater.IMessageFormater;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class EmailAdminBroadcastNotificationSender_Test {

    @Test
    void send_validUser_sendsEmail() {
        EmailService emailService = mock(EmailService.class);
        IMessageFormater<AdminBroadcastMessageData> formater = mock(IMessageFormater.class);
        when(formater.formatMessage(any())).thenReturn("<html>msg</html>");
        EmailAdminBroadcastNotificationSender sender = new EmailAdminBroadcastNotificationSender(emailService, formater);
        UserContactDTO user = new UserContactDTO();
        user.setEmail("besho@mail.com");
        sender.send(user, "besho");
        verify(emailService).sendEmail(eq("besho@mail.com"), contains("Admin Message"), anyString());
    }

    @Test
    void send_nullOrBlankEmail_doesNothing() {
        EmailService emailService = mock(EmailService.class);
        IMessageFormater<AdminBroadcastMessageData> formater = mock(IMessageFormater.class);
        EmailAdminBroadcastNotificationSender sender = new EmailAdminBroadcastNotificationSender(emailService, formater);
        sender.send(null, "besho");
        UserContactDTO u = new UserContactDTO();
        u.setEmail(" ");
        sender.send(u, "besho");
        verifyNoInteractions(emailService, formater);
    }
}