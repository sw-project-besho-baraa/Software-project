package Service.BookService.OverdueBorrowDetection_Test;

import Entity.Item;
import Entity.User;
import Service.Book.OverdueBorrowDetection.OverdueBorrowData;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OverdueBorrowDetection_Test
{

    @Test
    void toMessage_ReturnsCorrectFormat()
    {
        User user = mock(User.class);
        Item item = mock(Item.class);

        when(item.getTitle()).thenReturn("bashabesh");

        OverdueBorrowData event = new OverdueBorrowData(user, item, 4, LocalDate.of(2025,1,1));
        String msg = event.toMessage();
        assertEquals("Item \"bashabesh\" is overdue by 4 days",msg);
    }
}
