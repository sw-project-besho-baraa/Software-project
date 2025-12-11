package Service.OverdueBorrowDetection;

import Entity.MediaItem;
import java.time.LocalDateTime;

/**
 * Represents a borrowed media item that is overdue.
 *
 * @param item
 *            the overdue media item
 * @param overdueDays
 *            number of days the item is overdue
 * @param detectedAt
 *            the time when the overdue status was detected
 */
public record OverdueBorrowedItem(MediaItem item, long overdueDays, LocalDateTime detectedAt) {
}
