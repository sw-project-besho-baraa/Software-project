package Service.OverdueBorrowDetection;

import java.util.*;
import Entity.User;
import Entity.MediaItem;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import DTO.UserDTO.UserContactDTO;
import Repository.MediaItemRepository;
import Util.DateCalculator.DateCalculator;
import org.springframework.stereotype.Component;

@Component
public class OverdueItemDetector
{

    private final MediaItemRepository mediaItemRepository;

    public OverdueItemDetector(MediaItemRepository mediaItemRepository)
    {

        this.mediaItemRepository = mediaItemRepository;
    }

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
                .map(entry -> new OverdueBorrowedItemsData(entry.getKey(), entry.getValue()))
                .toList();
    }
}
