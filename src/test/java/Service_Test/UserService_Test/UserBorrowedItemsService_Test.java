package Service_Test.UserService_Test;

import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Service.UserService.UserBorrowedItemsService;
import Util.CurrentLocalTimeDateResolver.ICurrentLocalTimeDateResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserBorrowedItemsService_Test {

    private UserBorrowedItemsService service;
    private ICurrentLocalTimeDateResolver timeResolver;
    private final LocalDateTime now = LocalDateTime.of(2025, 1, 1, 12, 0);

    @BeforeEach
    void setUp() {
        service = new UserBorrowedItemsService();
        timeResolver = mock(ICurrentLocalTimeDateResolver.class);
        when(timeResolver.getCurrentLocalDateTime()).thenReturn(now);
    }

    @Test
    void countBorrowedItems_returnsZero_whenBorrowedItemsNull() {
        User user = new User();
        user.setBorrowedItems(null);
        assertEquals(0, service.countBorrowedItems(user));
    }

    @Test
    void countBorrowedItems_returnsSize_whenBorrowedItemsNotNull() {
        User user = new User();
        user.setBorrowedItems(List.of(new TestMediaItem("B1"), new TestMediaItem("B2")));
        assertEquals(2, service.countBorrowedItems(user));
    }

    @Test
    void countBorrowedItems_nullUser_throwsNpe() {
        assertThrows(NullPointerException.class, () -> service.countBorrowedItems(null));
    }

    @Test
    void countOverdueItems_returnsZero_whenBorrowedItemsNull() {
        User user = new User();
        user.setBorrowedItems(null);
        assertEquals(0, service.countOverdueItems(user, timeResolver));
    }

    @Test
    void countOverdueItems_noOverdue() {
        User user = new User();
        TestMediaItem item = new TestMediaItem("B1");
        item.setBorrowed(true);
        item.setDueDate(now.plusDays(1));
        user.setBorrowedItems(List.of(item));
        assertEquals(0, service.countOverdueItems(user, timeResolver));
    }

    @Test
    void countOverdueItems_mixedBorrowedAndNotBorrowed_overdueOnlyBorrowedCounted() {
        User user = new User();
        TestMediaItem overdueBorrowed = new TestMediaItem("OB");
        overdueBorrowed.setBorrowed(true);
        overdueBorrowed.setDueDate(now.minusDays(1));
        TestMediaItem overdueNotBorrowed = new TestMediaItem("ONB");
        overdueNotBorrowed.setBorrowed(false);
        overdueNotBorrowed.setDueDate(now.minusDays(2));
        user.setBorrowedItems(List.of(overdueBorrowed, overdueNotBorrowed));
        assertEquals(1, service.countOverdueItems(user, timeResolver));
    }

    @Test
    void countOverdueItems_nullUser_throwsNpe() {
        assertThrows(NullPointerException.class, () -> service.countOverdueItems(null, timeResolver));
    }

    @Test
    void getOverdueItems_returnsNull_whenBorrowedItemsNull() {
        User user = new User();
        user.setBorrowedItems(null);
        assertNull(service.getOverdueItems(user, timeResolver));
    }

    @Test
    void getOverdueItems_filtersCorrectly() {
        User user = new User();

        TestMediaItem overdueBorrowed = new TestMediaItem("OB");
        overdueBorrowed.setBorrowed(true);
        overdueBorrowed.setDueDate(now.minusDays(1));

        TestMediaItem futureBorrowed = new TestMediaItem("FB");
        futureBorrowed.setBorrowed(true);
        futureBorrowed.setDueDate(now.plusDays(1));

        TestMediaItem overdueNotBorrowed = new TestMediaItem("ONB");
        overdueNotBorrowed.setBorrowed(false);
        overdueNotBorrowed.setDueDate(now.minusDays(2));

        user.setBorrowedItems(List.of(overdueBorrowed, futureBorrowed, overdueNotBorrowed));

        Stream<MediaItem> stream = service.getOverdueItems(user, timeResolver);
        List<MediaItem> list = stream.toList();

        assertEquals(1, list.size());
        assertEquals("OB", list.get(0).getTitle());
    }

    @Test
    void getOverdueItems_nullUser_throwsNpe() {
        assertThrows(NullPointerException.class, () -> service.getOverdueItems(null, timeResolver));
    }

    @Test
    void getBorrowedItems_returnsList() {
        User user = new User();
        List<MediaItem> items = new ArrayList<>();
        items.add(new TestMediaItem("B1"));
        user.setBorrowedItems(items);
        assertEquals(items, service.getBorrowedItems(user));
    }

    @Test
    void getBorrowedItems_nullUser_throwsNpe() {
        assertThrows(NullPointerException.class, () -> service.getBorrowedItems(null));
    }

    private static class TestMediaItem extends MediaItem {
        public TestMediaItem(String title) {
            super(title);
        }
        @Override
        public MediaItemType getMediaType() {
            return MediaItemType.Book;
        }
    }
}