package Service.OverdueBorrowNotifier;

import DTO.UserDTO.UserContactDTO;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.NotificationSender.INotificationSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
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
        if (overBorrows == null)
        {
            return;
        }
        for (var overBorrow : overBorrows)
        {
            notifier.send(overBorrow.userContactDTO(),overBorrow.items());
        }
    }
}
