package Service.OverdueBorrowDetection;

import java.util.*;
import Entity.User;
import Entity.MediaItem;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import Repository.MediaItemRepository;
import Util.DateCalculator.DateCalculator;
import org.springframework.stereotype.Component;

/**
 * Detects users who have overdue borrowed media items.
 */
@Component
public class OverdueItemDetector
{

    private final MediaItemRepository mediaItemRepository;

    /**
     * Creates a new detector using the given media item repository.
     *
     * @param mediaItemRepository
     *            repository used to fetch media items
     */
    public OverdueItemDetector(MediaItemRepository mediaItemRepository)
    {
        this.mediaItemRepository = mediaItemRepository;
    }

    /**
     * Finds all users who currently have overdue items.
     *
     * @return a list of users and their overdue borrowed items
     */
    public List<OverdueBorrowedItemsData> detectUsersWithOverdueBooks()
    {
        LocalDateTime currentDate = LocalDateTime.now();
        List<MediaItem> allItems = mediaItemRepository.findAllByBorrowedTrueAndDueDateBefore(currentDate);

        Map<User, List<OverdueBorrowedItem>> overdueByUser = allItems.stream()
                .collect(
                        Collectors
                                .groupingBy(MediaItem::getBorrower,
                                        Collectors
                                                .mapping(
                                                        item -> new OverdueBorrowedItem(item,
                                                                DateCalculator.daysDifference(currentDate,
                                                                        item.getDueDate()),
                                                                currentDate),
                                                        Collectors.toList())));

        return overdueByUser.entrySet().stream()
                .map(entry -> new OverdueBorrowedItemsData(entry.getKey(), entry.getValue())).toList();
    }
}
