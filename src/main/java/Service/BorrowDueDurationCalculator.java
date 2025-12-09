package Service;

import Entity.MediaItem;
import Enum.MediaItemType;
import Exception.NotImplementedException;
import lombok.NonNull;

public class BorrowDueDurationCalculator
{
    public static int getDuration(@NonNull MediaItem item)
    {

        if (item.getMediaType() == MediaItemType.Book)
        {
            return 28;
        }
        if (item.getMediaType() == MediaItemType.Cd)
        {
            return 7;
        }
        throw new NotImplementedException("Duration not implemented for media type: " + item.getMediaType());

    }
}
