package Util_Test.MessageFormater;

import DTO.UserDTO.UserContactDTO;
import Entity.MediaItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.MediaItem.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Util.MessageFormater.GeneralOverdueBorrowMessageFormater;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GeneralOverdueBorrowMessageFormater_Test
{

    GeneralOverdueBorrowMessageFormater f = new GeneralOverdueBorrowMessageFormater();

    @Test
    void nullInput_returnsEmpty()
    {
        assertEquals("",f.formatMessage(null));
    }

    @Test
    void userNull_itemsNull_defaultsAndEmptyRow()
    {
        String r = f.formatMessage(new OverdueBorrowedItemsData(null, null));
        assertTrue(r.contains("Valued Reader"));
        assertTrue(r.contains("You currently have no overdue items."));
        assertTrue(r.contains("0 item(s)"));
    }

    @Test
    void userWithInfo_noItems_userInfoFilled()
    {
        UserContactDTO u = mock(UserContactDTO.class);
        when(u.getName()).thenReturn("Mohammad");
        when(u.getEmail()).thenReturn("user@example.com");
        when(u.getPhoneNumber()).thenReturn("0590000000");
        String r = f.formatMessage(new OverdueBorrowedItemsData(u, List.of()));
        assertTrue(r.contains("Mohammad"));
        assertTrue(r.contains("user@example.com"));
        assertTrue(r.contains("0590000000"));
        assertTrue(r.contains("0 item(s)"));
    }

    @Test
    void items_present_rowsBuilt_andEscaped()
    {
        UserContactDTO u = mock(UserContactDTO.class);
        when(u.getName()).thenReturn("<Mohammad & Co>");
        when(u.getEmail()).thenReturn(null);
        when(u.getPhoneNumber()).thenReturn("   ");

        MediaItem m1 = mock(MediaItem.class);
        when(m1.getTitle()).thenReturn("C++ <Advanced> & Tips");
        when(m1.getBorrowedDate()).thenReturn(new Date(0));

        OverdueBorrowedItem o1 = new OverdueBorrowedItem(m1, 3, LocalDate.of(2025,12,1));
        OverdueBorrowedItem o3 = new OverdueBorrowedItem(null, 2, null);

        String r = f.formatMessage(new OverdueBorrowedItemsData(u, Arrays.asList(o1,null,o3)));

        assertTrue(r.contains("&lt;Mohammad &amp; Co&gt;"));
        assertTrue(r.contains("C++ &lt;Advanced&gt; &amp; Tips"));
        assertTrue(r.contains("Unknown item"));
        assertTrue(r.contains("3"));
        assertTrue(r.contains("3 item(s)"));
    }
}
