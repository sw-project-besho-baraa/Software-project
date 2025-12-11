package Service.MediaItem;

import java.time.LocalDateTime;
import Repository.MediaItemRepository;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service for counting overdue borrowed media items.
 */
@Service
public class OverDueCountService
{

    private final MediaItemRepository mediaItemRepository;

    /**
     * Creates a new service using the given repository.
     *
     * @param mediaItemRepository
     *            repository for media items
     */
    @Autowired
    public OverDueCountService(MediaItemRepository mediaItemRepository)
    {
        this.mediaItemRepository = mediaItemRepository;
    }

    /**
     * Counts all overdue items based on the current system time.
     *
     * @return number of overdue items
     */
    public long countOverdueItems()
    {
        var now = new CurrentLocalDateTimeResolver().getCurrentLocalDateTime();
        return mediaItemRepository.countByBorrowedTrueAndDueDateBefore(now);
    }

    /**
     * Counts overdue items using a specific date and time.
     *
     * @param localDateTime
     *            reference time for checking overdue items
     * @return number of overdue items before the given time
     */
    public long countOverdueItems(LocalDateTime localDateTime)
    {
        return mediaItemRepository.countByBorrowedTrueAndDueDateBefore(localDateTime);
    }
}
