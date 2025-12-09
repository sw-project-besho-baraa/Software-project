package Util;

import Entity.MediaItem;
import Enum.MediaItemType;
import java.math.BigDecimal;
import Exception.NotImplementedException;

public class FineResolver
{
    public static BigDecimal getFine(MediaItem item)
    {
        if (item.getMediaType() == MediaItemType.Book)
        {
            return BigDecimal.valueOf(20);
        }
        if (item.getMediaType() == MediaItemType.Cd)
        {
            return BigDecimal.valueOf(10);
        }
        throw new NotImplementedException("");
    }
}
