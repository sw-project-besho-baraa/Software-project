package Util;

import Entity.MediaItem;
import Enum.MediaItemType;
import java.math.BigDecimal;
import Exception.NotImplementedException;

/**
 * Utility class for resolving fine amounts based on {@link MediaItem} type.
 */
public class FineResolver
{

    private FineResolver()
    {
    }

    /**
     * Returns the fine amount applicable to the given media item type.
     *
     * @param item
     *            the media item for which to determine the fine
     * @return the fine amount as a {@link BigDecimal}
     * @throws NotImplementedException
     *             if the item's media type is unsupported
     */
    public static BigDecimal getFine(MediaItem item)
    {
        if (item == null || item.getMediaType() == null)
        {
            throw new IllegalArgumentException("Media item or type cannot be null.");
        }

        return switch (item.getMediaType()) {
            case Book -> BigDecimal.valueOf(20);
            case Cd -> BigDecimal.valueOf(10);
            default -> throw new NotImplementedException("Fine not implemented for media type: " + item.getMediaType());
        };
    }
}
