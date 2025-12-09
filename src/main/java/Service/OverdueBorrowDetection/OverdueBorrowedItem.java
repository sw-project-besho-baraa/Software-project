package Service.OverdueBorrowDetection;

import Entity.MediaItem;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OverdueBorrowedItem(MediaItem item, long overdueDays, LocalDateTime detectedAt) {

}