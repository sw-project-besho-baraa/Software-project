package Service.MediaItem.OverdueBorrowDetection;

import DTO.UserDTO.UserContactDTO;
import Entity.MediaItem;
import Entity.User;
import Service.BookService.AllBookService;
import Service.CDService.AllCdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
public class OverdueItemDetector {

    private final AllBookService allBookService;
    private final AllCdService allCdService;

    @Autowired
    public OverdueItemDetector(AllBookService allBookService, AllCdService allCdService) {
        this.allBookService = allBookService;
        this.allCdService = allCdService;
    }

    public List<OverdueBorrowedItemsData> detectUsersWithOverdueBooks() {
        List<MediaItem> allItems = new ArrayList<>();
        allItems.addAll(allBookService.getAllBooks());
        allItems.addAll(allCdService.getAllCds());

        Date now = new Date();
        LocalDate today = LocalDate.now();

        Map<User, List<OverdueBorrowedItem>> overdueByUser = new HashMap<>();

        for (MediaItem item : allItems) {
            if (!item.isBorrowed() || item.getDueDate() == null || !item.getDueDate().before(now))
                continue;

            User borrower = item.getBorrower();
            if (borrower == null) continue;

            LocalDate dueDateLocal = item.getDueDate()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            int overdueDays = (int) ChronoUnit.DAYS.between(dueDateLocal, today);
            if (overdueDays < 1) overdueDays = 1;

            OverdueBorrowedItem overdueItem =
                    new OverdueBorrowedItem(item, overdueDays, today);

            overdueByUser
                    .computeIfAbsent(borrower, k -> new ArrayList<>())
                    .add(overdueItem);
        }

        List<OverdueBorrowedItemsData> result = new ArrayList<>();

        for (Map.Entry<User, List<OverdueBorrowedItem>> entry : overdueByUser.entrySet()) {
            User user = entry.getKey();
            List<OverdueBorrowedItem> items = entry.getValue();

            if (items.isEmpty()) continue;

            UserContactDTO contact = new UserContactDTO();
            contact.setEmail(user.getEmail());
            contact.setName(user.getName());
            contact.setId(user.getId());
            result.add(new OverdueBorrowedItemsData(contact, items));
        }

        return result;
    }
}
