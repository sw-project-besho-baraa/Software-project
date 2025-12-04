package Service.Book.OverdueBorrowDetection;

import Entity.Item;

import java.time.LocalDate;

public record OverdueBorrowedItem(Item item, int overdueDays, LocalDate detectedAt) {

}