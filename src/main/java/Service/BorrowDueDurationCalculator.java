package Service;

import Entity.MediaItem;
import Enum.MediaItemType;
import Exception.NotImplementedException;
import lombok.NonNull;

/**
 * Utility class for calculating the borrowing duration of media items.
 */
public class BorrowDueDurationCalculator {

    /**
     * Returns the allowed borrowing duration (in days) based on the media type.
     *
     * @param item the media item to check
     * @return number of days the item can be borrowed
     * @throws NotImplementedException if the media type is not supported
     */
    public static int getDuration(@NonNull MediaItem item) {

        if (item.getMediaType() == MediaItemType.Book) {
            return 28;
        }
        if (item.getMediaType() == MediaItemType.Cd) {
            return 7;
        }
        throw new NotImplementedException("Duration not implemented for media type: " + item.getMediaType());
    }
}
