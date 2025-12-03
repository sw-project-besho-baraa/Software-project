package Service.Book.OverdueBorrowDetection;

import DTO.UserDTO.UserContactDTO;
import Entity.Item;
import Entity.User;
import java.time.LocalDate;
import java.util.List;

public record OverdueBorrowedItemsData(UserContactDTO userContactDTO, List<OverdueBorrowedItem> items) {

}
