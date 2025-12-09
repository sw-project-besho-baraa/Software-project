package Service.OverdueBorrowNotifier;

import Entity.User;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.NotificationSender.INotificationSender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OverdueBorrowNotifier
{
    private final INotificationSender<User, List<OverdueBorrowedItem>> notifier;
    private final OverdueItemDetector overdueBookDetector;
    public OverdueBorrowNotifier(INotificationSender<User, List<OverdueBorrowedItem>> notifier,
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
            notifier.send(overBorrow.user(),overBorrow.items());
        }
    }
}
