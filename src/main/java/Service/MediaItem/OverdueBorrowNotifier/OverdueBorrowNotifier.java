package Service.MediaItem.OverdueBorrowNotifier;

import DTO.UserDTO.UserContactDTO;
import Service.MediaItem.OverdueBorrowDetection.OverdueItemDetector;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.NotificationSender.INotificationSender;
import org.springframework.stereotype.Service;

import java.util.List;

public class OverdueBorrowNotifier
{
    private final INotificationSender<UserContactDTO, List<OverdueBorrowedItem>> notifier;
    private final OverdueItemDetector overdueBookDetector;
    public OverdueBorrowNotifier(INotificationSender<UserContactDTO, List<OverdueBorrowedItem>> notifier,
            OverdueItemDetector overdueBookDetector)
    {
        this.notifier = notifier;
        this.overdueBookDetector = overdueBookDetector;
    }

    public void send()
    {
        var overBorrows = overdueBookDetector.detectUsersWithOverdueBooks();
        for (var overBorrow : overBorrows)
        {
            notifier.send(overBorrow.userContactDTO(),overBorrow.items());
        }
    }
}
