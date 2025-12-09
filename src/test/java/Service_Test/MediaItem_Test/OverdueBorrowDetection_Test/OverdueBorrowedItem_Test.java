package Service_Test.MediaItem_Test.OverdueBorrowDetection_Test;

import Entity.MediaItem;
import Enum.MediaItemType;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class OverdueBorrowedItem_Test {

    @Test
    void record_storesAndReturnsValuesCorrectly() {
        MediaItem mockItem = new MediaItem("Test Item") {
            @Override
            public MediaItemType getMediaType() {return MediaItemType.Book;}};

        LocalDate date = LocalDate.now();
        OverdueBorrowedItem data = new OverdueBorrowedItem(mockItem, 7, date);

        assertEquals(mockItem, data.item());
        assertEquals(7, data.overdueDays());
        assertEquals(date, data.detectedAt());
        assertEquals(MediaItemType.Book, data.item().getMediaType());
    }
}
