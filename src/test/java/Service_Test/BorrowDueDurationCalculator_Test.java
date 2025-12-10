package Service_Test;

import Entity.MediaItem;
import Enum.MediaItemType;
import Exception.NotImplementedException;
import Service.BorrowDueDurationCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BorrowDueDurationCalculator_Test
{

    private static class TestMediaItem extends MediaItem
    {
        private final MediaItemType type;
        public TestMediaItem(String title, MediaItemType type)
        {
            super(title);
            this.type = type;
        }

        @Override
        public MediaItemType getMediaType()
        {
            return type;
        }
    }

    @Test
    void getDuration_returns28_forBook()
    {
        MediaItem item = new TestMediaItem("Book Title", MediaItemType.Book);
        assertEquals(28,BorrowDueDurationCalculator.getDuration(item));
    }

    @Test
    void getDuration_returns7_forCd()
    {
        MediaItem item = new TestMediaItem("CD Title", MediaItemType.Cd);
        assertEquals(7,BorrowDueDurationCalculator.getDuration(item));
    }

    @Test
    void getDuration_throwsNotImplemented_forOtherType()
    {
        MediaItem item = new TestMediaItem("DVD Title", MediaItemType.Dve);
        assertThrows(NotImplementedException.class,() -> BorrowDueDurationCalculator.getDuration(item));
    }
}