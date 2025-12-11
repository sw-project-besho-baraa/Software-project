package Service.Fine;

import Entity.User;
import Repository.MediaItemRepository;
import Repository.UserRepository;
import Service.OverdueBorrowDetection.OverdueItemDetector;
import Util.CurrentLocalTimeDateResolver.CurrentLocalDateTimeResolver;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Scheduled background service that periodically calculates and applies overdue
 * fines.
 * <p>
 * Runs automatically once every 24 hours.
 * </p>
 */
@Component
public class FineThreadService implements Runnable
{

    private final MediaItemRepository mediaItemRepository;
    private final UserRepository userRepository;

    /**
     * Constructs a fine thread service with required repositories.
     *
     * @param mediaItemRepository
     *            repository for media items
     * @param userRepository
     *            repository for user data
     */
    public FineThreadService(MediaItemRepository mediaItemRepository, UserRepository userRepository)
    {
        this.mediaItemRepository = mediaItemRepository;
        this.userRepository = userRepository;
    }

    /**
     * Executes the fine calculation process.
     * <p>
     * Scheduled to run every 24 hours to detect overdue items and apply fines
     * accordingly.
     * </p>
     */
    @Override
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void run()
    {
        var service = new CalculateFineService(mediaItemRepository, userRepository);
        var currentDateTimeResolver = new CurrentLocalDateTimeResolver();
        service.calculateFines(currentDateTimeResolver.getCurrentLocalDateTime());
    }
}
