package Service.MediaItem.OverdueBorrowDetection;

import DTO.UserDTO.UserContactDTO;
import org.springframework.stereotype.Component;

import java.util.List;


public record OverdueBorrowedItemsData(UserContactDTO userContactDTO, List<OverdueBorrowedItem> items) {

}
