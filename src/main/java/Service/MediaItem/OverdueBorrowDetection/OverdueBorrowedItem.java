package Service.MediaItem.OverdueBorrowDetection;

import Entity.MediaItem;

import java.time.LocalDate;

public record OverdueBorrowedItem(MediaItem item, int overdueDays, LocalDate detectedAt) {

}