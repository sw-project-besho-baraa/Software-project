package Service.Book.OverdueBorrowDetection;

import DTO.UserDTO.UserContactDTO;

import java.util.List;

public record OverdueBorrowedItemsData(UserContactDTO userContactDTO, List<OverdueBorrowedItem> items) {

}
