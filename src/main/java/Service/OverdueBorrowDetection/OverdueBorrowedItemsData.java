package Service.OverdueBorrowDetection;

import Entity.User;

import java.util.List;

public record OverdueBorrowedItemsData(User user, List<OverdueBorrowedItem> items) {

}
