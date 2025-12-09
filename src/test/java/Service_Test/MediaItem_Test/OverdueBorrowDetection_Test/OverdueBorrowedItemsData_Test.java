package Service_Test.MediaItem_Test.OverdueBorrowDetection_Test;

import DTO.UserDTO.UserContactDTO;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class OverdueBorrowedItemsData_Test
{

    @Test
    void createsRecordAndAccessorsWork()
    {
        UserContactDTO user = new UserContactDTO();
        user.setName("besho");
        user.setEmail("besho@mail.com");
        OverdueBorrowedItem item = new OverdueBorrowedItem(null, 3, LocalDate.now());
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, List.of(item));
        assertEquals(user,data.userContactDTO());
        assertEquals(1,data.items().size());
        assertEquals(3,data.items().get(0).overdueDays());
    }
}