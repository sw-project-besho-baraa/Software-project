package Service_Test;

import Entity.MediaItem;
import Enum.MediaItemType;
import Util.FineResolver;
import Exception.NotImplementedException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FineResolver_Test
{

    @Test
    void getFine_whenBook_returns20()
    {
        MediaItem book = mock(MediaItem.class);
        when(book.getMediaType()).thenReturn(MediaItemType.Book);

        BigDecimal fine = FineResolver.getFine(book);

        assertEquals(BigDecimal.valueOf(20),fine);
    }

    @Test
    void getFine_whenCd_returns10()
    {
        MediaItem cd = mock(MediaItem.class);
        when(cd.getMediaType()).thenReturn(MediaItemType.Cd);

        BigDecimal fine = FineResolver.getFine(cd);

        assertEquals(BigDecimal.valueOf(10),fine);
    }

    @Test
    void getFine_whenUnknownType_throwsNotImplemented()
    {
        MediaItem item = mock(MediaItem.class);
        when(item.getMediaType()).thenReturn(null);
        assertThrows(java.lang.IllegalArgumentException.class,() -> FineResolver.getFine(item));
    }
}