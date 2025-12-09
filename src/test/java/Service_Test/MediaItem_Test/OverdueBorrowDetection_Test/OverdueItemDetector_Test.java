package Service_Test.MediaItem_Test.OverdueBorrowDetection_Test;

import Entity.MediaItem;
import Entity.User;
import Service.BookService.AllBookService;
import Service.CDService.AllCdService;
import Service.MediaItem.OverdueBorrowDetection.OverdueItemDetector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OverdueItemDetector_Test {

    private AllBookService allBookService;
    private AllCdService allCdService;
    private OverdueItemDetector detector;

    @BeforeEach
    void setUp() {
        allBookService = mock(AllBookService.class);
        allCdService = mock(AllCdService.class);
        detector = new OverdueItemDetector(allBookService, allCdService);
    }

    @Test
    void detectUsersWithOverdueBooks_noItems_returnsEmptyList() {
        when(allBookService.getAllBooks()).thenReturn(Collections.emptyList());
        when(allCdService.getAllCds()).thenReturn(Collections.emptyList());
        var result = detector.detectUsersWithOverdueBooks();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void detectUsersWithOverdueBooks_ignoresNotBorrowedAndFutureOrNullDueDates() {
        MediaItem notBorrowed = mock(MediaItem.class);
        when(notBorrowed.isBorrowed()).thenReturn(false);
        MediaItem borrowedNoDueDate = mock(MediaItem.class);
        when(borrowedNoDueDate.isBorrowed()).thenReturn(true);
        when(borrowedNoDueDate.getDueDate()).thenReturn(null);
        MediaItem borrowedFutureDue = mock(MediaItem.class);
        when(borrowedFutureDue.isBorrowed()).thenReturn(true);
        when(borrowedFutureDue.getDueDate()).thenReturn(new Date(System.currentTimeMillis() + 60_000));
        when(allBookService.getAllBooks()).thenReturn((List) List.of(notBorrowed));
        when(allCdService.getAllCds()).thenReturn((List) List.of(borrowedNoDueDate, borrowedFutureDue));
        var result = detector.detectUsersWithOverdueBooks();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void detectUsersWithOverdueBooks_ignoresOverdueItemsWithNullBorrower() {
        MediaItem overdue = mock(MediaItem.class);
        when(overdue.isBorrowed()).thenReturn(true);
        when(overdue.getDueDate()).thenReturn(new Date(System.currentTimeMillis() - 60_000));
        when(overdue.getBorrower()).thenReturn(null);
        when(allBookService.getAllBooks()).thenReturn((List) List.of(overdue));
        when(allCdService.getAllCds()).thenReturn(Collections.emptyList());
        var result = detector.detectUsersWithOverdueBooks();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void detectUsersWithOverdueBooks_singleOverdueItem_createsOneEntry() {
        User user = new User();
        user.setId(1);
        user.setEmail("u@test.com");
        user.setName("User One");
        MediaItem overdue = mock(MediaItem.class);
        when(overdue.isBorrowed()).thenReturn(true);
        when(overdue.getDueDate()).thenReturn(new Date(System.currentTimeMillis() - 86_400_000L * 2));
        when(overdue.getBorrower()).thenReturn(user);
        when(allBookService.getAllBooks()).thenReturn((List) List.of(overdue));
        when(allCdService.getAllCds()).thenReturn(Collections.emptyList());
        var result = detector.detectUsersWithOverdueBooks();
        assertEquals(1, result.size());
    }

    @Test
    void detectUsersWithOverdueBooks_overdueToday_stillCreatesEntry() {
        User user = new User();
        user.setId(2);
        user.setEmail("today@test.com");
        user.setName("Today User");
        MediaItem overdueToday = mock(MediaItem.class);
        when(overdueToday.isBorrowed()).thenReturn(true);
        when(overdueToday.getDueDate()).thenReturn(new Date(System.currentTimeMillis() - 1_000));
        when(overdueToday.getBorrower()).thenReturn(user);
        when(allBookService.getAllBooks()).thenReturn((List) List.of(overdueToday));
        when(allCdService.getAllCds()).thenReturn(Collections.emptyList());
        var result = detector.detectUsersWithOverdueBooks();
        assertEquals(1, result.size());
    }

    @Test
    void detectUsersWithOverdueBooks_multipleOverdueItemsSameUser_groupedInSingleEntry() {
        User user = new User();
        user.setId(3);
        user.setEmail("multi@test.com");
        user.setName("Multi User");
        MediaItem overdue1 = mock(MediaItem.class);
        when(overdue1.isBorrowed()).thenReturn(true);
        when(overdue1.getDueDate()).thenReturn(new Date(System.currentTimeMillis() - 86_400_000L));
        when(overdue1.getBorrower()).thenReturn(user);
        MediaItem overdue2 = mock(MediaItem.class);
        when(overdue2.isBorrowed()).thenReturn(true);
        when(overdue2.getDueDate()).thenReturn(new Date(System.currentTimeMillis() - 86_400_000L * 3));
        when(overdue2.getBorrower()).thenReturn(user);
        when(allBookService.getAllBooks()).thenReturn((List) List.of(overdue1));
        when(allCdService.getAllCds()).thenReturn((List) List.of(overdue2));
        var result = detector.detectUsersWithOverdueBooks();
        assertEquals(1, result.size());
    }
}
