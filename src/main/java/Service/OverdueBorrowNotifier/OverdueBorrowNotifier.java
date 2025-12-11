package Service.OverdueBorrowNotifier;

import Entity.User;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.NotificationSender.INotificationSender;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Handles sending notifications to users with overdue borrowed items.
 */
@Component
public class OverdueBorrowNotifier {

    private final INotificationSender<User, List<OverdueBorrowedItem>> notifier;
    private final OverdueItemDetector overdueBookDetector;

    /**
     * Creates a new notifier for overdue borrow events.
     *
     * @param notifier notification sender used to send overdue messages
     * @param overdueBookDetector detector used to find users with overdue items
     */
    public OverdueBorrowNotifier(INotificationSender<User, List<OverdueBorrowedItem>> notifier,
                                 OverdueItemDetector overdueBookDetector) {
        this.notifier = notifier;
        this.overdueBookDetector = overdueBookDetector;
    }

    /**
     * Detects all users with overdue items and sends them notifications.
     */
    public void send() {
        var overBorrows = overdueBookDetector.detectUsersWithOverdueBooks();
        if (overBorrows == null) {
            return;
        }
        for (var overBorrow : overBorrows) {
            notifier.send(overBorrow.user(), overBorrow.items());
        }
    }
}
