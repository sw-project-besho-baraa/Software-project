package Service.MediaItem;

import java.time.LocalDateTime;
import Repository.MediaItemRepository;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OverDueCountService
{
    private final MediaItemRepository mediaItemRepository;

    @Autowired
    public OverDueCountService(MediaItemRepository mediaItemRepository)
    {

        this.mediaItemRepository = mediaItemRepository;
    }

    public long countOverdueItems()
    {
        var now = new CurrentLocalDateTimeResolver().getCurrentLocalDateTime();
        return mediaItemRepository.countByBorrowedTrueAndDueDateBefore(now);
    }

    public long countOverdueItems(LocalDateTime localDateTime)
    {
        return mediaItemRepository.countByBorrowedTrueAndDueDateBefore(localDateTime);
    }
}
