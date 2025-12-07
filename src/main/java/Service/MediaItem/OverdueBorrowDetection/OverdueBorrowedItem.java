package Service.MediaItem.OverdueBorrowDetection;

import Entity.MediaItem;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public record OverdueBorrowedItem(MediaItem item, int overdueDays, LocalDate detectedAt) {

}