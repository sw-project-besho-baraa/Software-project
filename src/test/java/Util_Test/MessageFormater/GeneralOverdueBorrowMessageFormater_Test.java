package Util_Test.MessageFormater;

import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.OverdueBorrowDetection.OverdueBorrowedItemsData;
import Util.MessageFormater.GeneralOverdueBorrowMessageFormater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GeneralOverdueBorrowMessageFormater_Test
{

    private GeneralOverdueBorrowMessageFormater formater;

    @BeforeEach
    void setUp()
    {
        formater = new GeneralOverdueBorrowMessageFormater();
    }

    @Test
    void formatMessage_returnsEmptyString_whenDataIsNull()
    {
        assertEquals("",formater.formatMessage(null));
    }

    @Test
    void formatMessage_handlesEmptyItems()
    {
        User user = new User();
        user.setId(1);
        user.setName("besho");
        user.setEmail("besho@example.com");
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, List.of());
        String result = formater.formatMessage(data);
        assertTrue(result.contains("You currently have no overdue items."));
        assertTrue(result.contains("besho"));
        assertTrue(result.contains("__USER_EMAIL__"));
    }

    @Test
    void formatMessage_handlesNullItem()
    {
        User user = new User();
        user.setId(2);
        user.setName(null);
        user.setEmail(null);
        OverdueBorrowedItem overdueItem = new OverdueBorrowedItem(null, 0, null);
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, List.of(overdueItem));
        String result = formater.formatMessage(data);
        assertTrue(result.contains("Unknown item"));
        assertTrue(result.contains("Valued Reader"));
    }

    @Test
    void formatMessage_handlesValidItem()
    {
        User user = new User();
        user.setId(3);
        user.setName("besho");
        user.setEmail("besho@example.com");
        MediaItem mediaItem = new TestMediaItem("Book Title");
        mediaItem.setBorrowedDate(null);
        OverdueBorrowedItem overdueItem = new OverdueBorrowedItem(mediaItem, 5, LocalDateTime.now());
        OverdueBorrowedItemsData data = new OverdueBorrowedItemsData(user, List.of(overdueItem));
        String result = formater.formatMessage(data);
        assertTrue(result.contains("Book Title"));
        assertTrue(result.contains("besho"));
        assertTrue(result.contains("5"));
        assertTrue(result.contains("item(s)"));
    }

    @Test
    void nullSafe_returnsDash_whenNullOrBlank() throws Exception
    {
        var method = GeneralOverdueBorrowMessageFormater.class.getDeclaredMethod("nullSafe",String.class);
        method.setAccessible(true);
        assertEquals("-",method.invoke(null,(Object) null));
        assertEquals("-",method.invoke(null,""));
        assertEquals("-",method.invoke(null,"   "));
        assertEquals("Hello",method.invoke(null,"Hello"));
    }

    @Test
    void escapeHtml_replacesSpecialCharacters() throws Exception
    {
        var method = GeneralOverdueBorrowMessageFormater.class.getDeclaredMethod("escapeHtml",String.class);
        method.setAccessible(true);
        String input = "<div>& \"test\"</div>";
        String escaped = (String) method.invoke(null,input);
        assertEquals("&lt;div&gt;&amp; &quot;test&quot;&lt;/div&gt;",escaped);
    }

    @Test
    void mediaItem_onCreate_setsBorrowedDate()
    {
        TestMediaItem item = new TestMediaItem("Test Book");
        assertNull(item.getBorrowedDate());
        item.onCreate();
        assertNotNull(item.getBorrowedDate());
    }

    private static class TestMediaItem extends MediaItem
    {
        public TestMediaItem(String title)
        {
            super(title);
        }

        @Override
        public MediaItemType getMediaType()
        {
            return MediaItemType.Book;
        }
    }
}