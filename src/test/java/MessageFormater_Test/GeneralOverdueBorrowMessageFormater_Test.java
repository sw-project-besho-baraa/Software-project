package MessageFormater_Test;

import DTO.UserDTO.UserContactDTO;
import Entity.MediaItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Util.MessageFormater.GeneralOverdueBorrowMessageFormater;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeneralOverdueBorrowMessageFormater_Test
{

    private final GeneralOverdueBorrowMessageFormater formater = new GeneralOverdueBorrowMessageFormater();

    @Test
    void formatMessage_NullInput_ReturnsEmptyString()
    {
        String result = formater.formatMessage(null);
        assertEquals("",result);
    }

    @Test
    void formatMessage_UserNull_ItemsNull_UsesDefaultsAndEmptyRow()
    {
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(null, null);
        String result = formater.formatMessage(data);
        assertTrue(result.contains("Valued Reader"));
        assertTrue(result.contains("You currently have no overdue items."));
        assertTrue(result.contains("0 item(s)"));
    }

    @Test
    void formatMessage_UserWithData_NoItems_FillsUserInfoAndEmptyRow()
    {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getName()).thenReturn("Mohammad");
        when(user.getEmail()).thenReturn("user@example.com");
        when(user.getPhoneNumber()).thenReturn("0590000000");
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, List.of());
        String result = formater.formatMessage(data);
        assertTrue(result.contains("Mohammad"));
        assertTrue(result.contains("user@example.com"));
        assertTrue(result.contains("0590000000"));
        assertTrue(result.contains("You currently have no overdue items."));
        assertTrue(result.contains("0 item(s)"));
    }

    @Test
    void formatMessage_WithSingleOverdueItem_BuildsItemRowAndCountIsOne()
    {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getName()).thenReturn("Ali");
        when(user.getEmail()).thenReturn("ali@example.com");
        when(user.getPhoneNumber()).thenReturn("0591234567");
        MediaItem item = mock(MediaItem.class);
        when(item.getBorrowedDate()).thenReturn(new Date(0));
        OverdueBorrowedItem overdueItem = new OverdueBorrowedItem(item, 5, LocalDate.of(2025,12,1));
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, List.of(overdueItem));
        String result = formater.formatMessage(data);
        assertTrue(result.contains("Ali"));
        assertTrue(result.contains("ali@example.com"));
        assertTrue(result.contains("1 item(s)"));

    }

    @Test
    void formatMessage_EscapesHtmlInTitleAndUserName()
    {
        UserContactDTO user = mock(UserContactDTO.class);
        when(user.getName()).thenReturn("<Mohammad & Co>");
        when(user.getEmail()).thenReturn("user@example.com");
        when(user.getPhoneNumber()).thenReturn("0590000000");
        MediaItem item = mock(MediaItem.class);
        when(item.getTitle()).thenReturn("C++ <Advanced> & Tips");
        when(item.getBorrowedDate()).thenReturn(null);
        OverdueBorrowedItem overdueItem = new OverdueBorrowedItem(item, 3, null);
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, List.of(overdueItem));
        String result = formater.formatMessage(data);
        assertTrue(result.contains("&lt;Mohammad &amp; Co&gt;"));
        assertTrue(result.contains("C++ &lt;Advanced&gt; &amp; Tips"));
    }
}