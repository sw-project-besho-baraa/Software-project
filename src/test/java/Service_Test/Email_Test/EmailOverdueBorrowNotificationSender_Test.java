package Service_Test.Email_Test;

import DTO.UserDTO.UserContactDTO;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Service.Email.EmailOverdueBorrowNotificationSender;
import Service.Email.EmailService;
import Util.MessageFormater.IMessageFormater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class EmailOverdueBorrowNotificationSender_Test
{

    private EmailService emailService;
    private IMessageFormater<OverdueBorrowedItemsData> messageFormater;
    private EmailOverdueBorrowNotificationSender sender;

    @BeforeEach
    void setup()
    {
        emailService = mock(EmailService.class);
        messageFormater = mock(IMessageFormater.class);
        sender = new EmailOverdueBorrowNotificationSender(emailService, messageFormater);
    }

    @Test
    void send_ValidUserAndItems_SendsEmail()
    {

        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getEmail()).thenReturn("besho@gmail.com");
        OverdueBorrowedItem item = mock(OverdueBorrowedItem.class);
        List<OverdueBorrowedItem> items = List.of(item);
        String expectedHtml = "<html>Overdue Notice</html>";
        when(messageFormater.formatMessage(any(OverdueBorrowedItemsData.class))).thenReturn(expectedHtml);
        sender.send(user,items);
        verify(messageFormater,times(1)).formatMessage(any(OverdueBorrowedItemsData.class));
        verify(emailService,times(1)).sendEmail("besho@gmail.com","Library Management System· Overdue items notice",
                expectedHtml);
    }

    @Test
    void send_UserIsNull_DoesNotSendEmail()
    {
        sender.send(null,List.of());
        verifyNoInteractions(emailService,messageFormater);
    }

    @Test
    void send_UserEmailIsBlank_DoesNotSendEmail()
    {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getEmail()).thenReturn("   ");
        sender.send(user,List.of());
        verifyNoInteractions(emailService,messageFormater);
    }

    @Test
    void send_UserEmailIsNull_DoesNotSendEmail()
    {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getEmail()).thenReturn(null);
        sender.send(user,List.of());
        verifyNoInteractions(emailService,messageFormater);
    }
}
