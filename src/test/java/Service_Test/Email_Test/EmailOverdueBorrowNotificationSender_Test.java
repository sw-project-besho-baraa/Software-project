package Service_Test.Email_Test;

import Entity.User;
import Service.Email.EmailOverdueBorrowNotificationSender;
import Service.Email.EmailService;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Util.MessageFormater.IMessageFormater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class EmailOverdueBorrowNotificationSender_Test {

    private EmailService emailService;
    private IMessageFormater<OverdueBorrowedItemsData> messageFormater;
    private EmailOverdueBorrowNotificationSender sender;

    @BeforeEach
    void setUp() {
        emailService = mock(EmailService.class);
        messageFormater = mock(IMessageFormater.class);
        sender = new EmailOverdueBorrowNotificationSender(emailService, messageFormater);
    }

    @Test
    void send_doesNothing_whenUserIsNull() {
        sender.send(null, List.of());
        verifyNoInteractions(emailService);
        verifyNoInteractions(messageFormater);
    }

    @Test
    void send_doesNothing_whenUserEmailIsNull() {
        User user = new User();
        user.setEmail(null);
        sender.send(user, List.of());
        verifyNoInteractions(emailService);
        verifyNoInteractions(messageFormater);
    }

    @Test
    void send_doesNothing_whenUserEmailIsBlank() {
        User user = new User();
        user.setEmail("   ");
        sender.send(user, List.of());
        verifyNoInteractions(emailService);
        verifyNoInteractions(messageFormater);
    }

    @Test
    void send_callsEmailService_whenValidUserAndItems() {
        User user = new User();
        user.setEmail("Besho@example.com");
        user.setName("Besho");
        List<OverdueBorrowedItem> items = List.of(mock(OverdueBorrowedItem.class));
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, items);
        when(messageFormater.formatMessage(data)).thenReturn("<html>body</html>");
        sender.send(user, items);
        verify(messageFormater, times(1)).formatMessage(data);
        verify(emailService, times(1)).sendEmail(eq("Besho@example.com"), eq("Library Management System· Overdue items notice"), eq("<html>body</html>"));
    }
}