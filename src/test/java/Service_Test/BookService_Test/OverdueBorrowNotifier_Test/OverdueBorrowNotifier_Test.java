package Service_Test.BookService_Test.OverdueBorrowNotifier_Test;

import DTO.UserDTO.UserContactDTO;
import Service.MediaItem.OverdueBorrowDetection.OverdueItemDetector;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Service.MediaItem.OverdueBorrowNotifier.OverdueBorrowNotifier;
import Service.NotificationSender.INotificationSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class OverdueBorrowNotifier_Test
{

    private OverdueItemDetector overdueBookDetector;
    private INotificationSender<UserContactDTO, List<OverdueBorrowedItem>> notifier1;
    private INotificationSender<UserContactDTO, List<OverdueBorrowedItem>> notifier2;
    private OverdueBorrowNotifier overdueBorrowNotifier;

    @BeforeEach
    void setup()
    {
        overdueBookDetector = mock(OverdueItemDetector.class);
        notifier1 = mock(INotificationSender.class);
        notifier2 = mock(INotificationSender.class);
        var notifiers = List.of(notifier1,notifier2);
        overdueBorrowNotifier = new OverdueBorrowNotifier(notifiers, overdueBookDetector);
    }

    @Test
    void send_WhenThereAreOverdueBorrows_CallsAllNotifiersForEachUser()
    {
        UserContactDTO userContact = mock(UserContactDTO.class);
        OverdueBorrowedItem overdueItem = mock(OverdueBorrowedItem.class);
        List<OverdueBorrowedItem> items = List.of(overdueItem);
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(userContact, items);
        List<OverdueBorrowedItemsData> detected = List.of(data);
        when(overdueBookDetector.detectUsersWithOverdueBooks()).thenReturn(detected);
        overdueBorrowNotifier.send();
        verify(notifier1,times(1)).send(userContact,items);
        verify(notifier2,times(1)).send(userContact,items);
        verify(overdueBookDetector,times(1)).detectUsersWithOverdueBooks();
    }

    @Test
    void send_WhenNoOverdueBorrows_DoesNotCallNotifiers()
    {
        when(overdueBookDetector.detectUsersWithOverdueBooks()).thenReturn(List.of());
        overdueBorrowNotifier.send();
        verify(overdueBookDetector,times(1)).detectUsersWithOverdueBooks();
        verifyNoInteractions(notifier1,notifier2);
    }
}
