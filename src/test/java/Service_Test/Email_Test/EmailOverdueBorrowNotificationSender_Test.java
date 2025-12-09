package Service_Test.Email_Test;

import DTO.UserDTO.UserContactDTO;
import Service.Email.EmailOverdueBorrowNotificationSender;
import Service.Email.EmailService;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Util.MessageFormater.IMessageFormater;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class EmailOverdueBorrowNotificationSender_Test {

    @Test
    void send_validUser_sendsEmail() {
        EmailService emailService = mock(EmailService.class);
        IMessageFormater<OverdueBorrowedItemsData> formater = mock(IMessageFormater.class);
        when(formater.formatMessage(any())).thenReturn("<html>ok</html>");
        EmailOverdueBorrowNotificationSender sender = new EmailOverdueBorrowNotificationSender(emailService, formater);
        UserContactDTO user = new UserContactDTO();
        user.setEmail("besho@mail.com");
        sender.send(user, List.of(new OverdueBorrowedItem(null, 2, null)));
        verify(emailService).sendEmail(eq("besho@mail.com"), contains("Overdue"), anyString());
    }

    @Test
    void send_nullOrEmptyEmail_doesNothing() {
        EmailService emailService = mock(EmailService.class);
        IMessageFormater<OverdueBorrowedItemsData> formater = mock(IMessageFormater.class);
        EmailOverdueBorrowNotificationSender sender = new EmailOverdueBorrowNotificationSender(emailService, formater);
        sender.send(null, List.of());
        UserContactDTO user = new UserContactDTO();
        user.setEmail(" ");
        sender.send(user, List.of());
        verifyNoInteractions(emailService, formater);
    }
}