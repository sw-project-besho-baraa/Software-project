package Service.Book.OverdueBorrowNotifier;

import DTO.UserDTO.UserContactDTO;
import Service.Book.OverdueBorrowDetection.OverdueItemDetector;
import Service.Book.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.NotificationSender.INotificationSender;

import java.util.List;

public class OverdueBorrowNotifier
{
    private final List<INotificationSender<UserContactDTO, List<OverdueBorrowedItem>>> notifiersMethods;
    private final OverdueItemDetector overdueBookDetector;
    public OverdueBorrowNotifier(List<INotificationSender<UserContactDTO, List<OverdueBorrowedItem>>> notifiersMethods,
            OverdueItemDetector overdueBookDetector)
    {
        this.notifiersMethods = notifiersMethods;
        this.overdueBookDetector = overdueBookDetector;
    }

    public void send()
    {
        var overBorrows = overdueBookDetector.detectUsersWithOverdueBooks();
        for (var overBorrow : overBorrows)
        {
            for (var notifier : notifiersMethods)
            {
                notifier.send(overBorrow.userContactDTO(),overBorrow.items());
            }
        }
    }
}
