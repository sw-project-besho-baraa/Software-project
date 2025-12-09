package Service_Test.MediaItem_Test.OverdueBorrowNotifier_Test;

import DTO.UserDTO.UserContactDTO;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Service.MediaItem.OverdueBorrowDetection.OverdueItemDetector;
import Service.MediaItem.OverdueBorrowNotifier.OverdueBorrowNotifier;
import Service.NotificationSender.INotificationSender;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class OverdueBorrowNotifier_Test
{

    @Test
    void send_withValidData_sendsNotificationForEachUser()
    {
        var notifier = mock(INotificationSender.class);
        var detector = mock(OverdueItemDetector.class);
        var user = mock(UserContactDTO.class);
        var item = mock(OverdueBorrowedItem.class);
        var data = mock(OverdueBorrowedItemsData.class);
        when(data.userContactDTO()).thenReturn(user);
        when(data.items()).thenReturn(List.of(item));
        when(detector.detectUsersWithOverdueBooks()).thenReturn(List.of(data));
        new OverdueBorrowNotifier(notifier, detector).send();
        verify(detector).detectUsersWithOverdueBooks();
        verify(notifier).send(user,List.of(item));
    }

    @Test
    void send_whenDetectorReturnsNull_doesNothing()
    {
        var notifier = mock(INotificationSender.class);
        var detector = mock(OverdueItemDetector.class);
        when(detector.detectUsersWithOverdueBooks()).thenReturn(null);
        new OverdueBorrowNotifier(notifier, detector).send();
        verify(detector).detectUsersWithOverdueBooks();
        verifyNoInteractions(notifier);
    }

    @Test
    void send_whenEmptyList_doesNothing()
    {
        var notifier = mock(INotificationSender.class);
        var detector = mock(OverdueItemDetector.class);
        when(detector.detectUsersWithOverdueBooks()).thenReturn(List.of());
        new OverdueBorrowNotifier(notifier, detector).send();
        verify(detector).detectUsersWithOverdueBooks();
        verifyNoInteractions(notifier);
    }
}