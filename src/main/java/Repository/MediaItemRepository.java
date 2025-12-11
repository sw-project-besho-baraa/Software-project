package Repository;

import Entity.MediaItem;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for managing {@link MediaItem} entities.
 * <p>
 * Provides methods to detect and count overdue items.
 */
@Repository
public interface MediaItemRepository extends JpaRepository<@NonNull MediaItem, @NonNull Integer>
{

    /**
     * Finds all borrowed items whose due date has passed.
     *
     * @param date
     *            reference date for overdue detection
     * @return list of overdue media items
     */
    List<MediaItem> findAllByBorrowedTrueAndDueDateBefore(@NonNull LocalDateTime date);

    /**
     * Counts how many borrowed items are overdue.
     *
     * @param date
     *            reference date for overdue detection
     * @return number of overdue items
     */
    long countByBorrowedTrueAndDueDateBefore(@NonNull LocalDateTime date);
}
