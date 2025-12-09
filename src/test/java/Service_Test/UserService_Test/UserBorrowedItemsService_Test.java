package Service_Test.UserService_Test;

import Entity.MediaItem;
import Entity.User;
import Repository.UserRepository;
import Service.UserService.UserBorrowedItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserBorrowedItemsService_Test {

    private UserRepository userRepository;
    private UserBorrowedItemsService userBorrowedItemsService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userBorrowedItemsService = new UserBorrowedItemsService(userRepository);
    }

    @Test
    void countBorrowedItems_returnsZero_whenSessionUserIsNull() {
        int result = userBorrowedItemsService.countBorrowedItems(null);
        assertEquals(0, result);
    }

    @Test
    void countBorrowedItems_returnsZero_whenUserNotFound() {
        User sessionUser = new User();
        sessionUser.setId(1);
        when(userRepository.findByIdWithBorrowedItems(1)).thenReturn(Optional.empty());

        int result = userBorrowedItemsService.countBorrowedItems(sessionUser);

        assertEquals(0, result);
    }

    @Test
    void countBorrowedItems_returnsZero_whenBorrowedItemsIsNull() {
        User sessionUser = new User();
        sessionUser.setId(2);

        User dbUser = new User();
        dbUser.setBorrowedItems(null);

        when(userRepository.findByIdWithBorrowedItems(2)).thenReturn(Optional.of(dbUser));

        int result = userBorrowedItemsService.countBorrowedItems(sessionUser);

        assertEquals(0, result);
    }

    @Test
    void countBorrowedItems_returnsSizeOfBorrowedItems() {
        User sessionUser = new User();
        sessionUser.setId(3);

        User dbUser = new User();
        List<MediaItem> items = Arrays.asList(
                mock(MediaItem.class),
                mock(MediaItem.class),
                mock(MediaItem.class)
        );
        dbUser.setBorrowedItems(items);

        when(userRepository.findByIdWithBorrowedItems(3)).thenReturn(Optional.of(dbUser));

        int result = userBorrowedItemsService.countBorrowedItems(sessionUser);

        assertEquals(3, result);
    }

    @Test
    void countOverdueItems_returnsZero_whenSessionUserIsNull() {
        long result = userBorrowedItemsService.countOverdueItems(null);
        assertEquals(0, result);
    }

    @Test
    void countOverdueItems_returnsZero_whenUserNotFound() {
        User sessionUser = new User();
        sessionUser.setId(8);

        when(userRepository.findByIdWithBorrowedItems(8)).thenReturn(Optional.empty());

        long result = userBorrowedItemsService.countOverdueItems(sessionUser);

        assertEquals(0, result);
    }

    @Test
    void countOverdueItems_returnsZero_whenBorrowedItemsIsNull() {
        User sessionUser = new User();
        sessionUser.setId(9);

        User dbUser = new User();
        dbUser.setBorrowedItems(null);

        when(userRepository.findByIdWithBorrowedItems(9)).thenReturn(Optional.of(dbUser));

        long result = userBorrowedItemsService.countOverdueItems(sessionUser);

        assertEquals(0, result);
    }

    @Test
    void countOverdueItems_countsOnlyBorrowedAndOverdueItems() {
        User sessionUser = new User();
        sessionUser.setId(4);

        User dbUser = new User();

        Date past = new Date(System.currentTimeMillis() - 60_000);
        Date future = new Date(System.currentTimeMillis() + 60_000);

        MediaItem overdueBorrowed = mock(MediaItem.class);
        when(overdueBorrowed.isBorrowed()).thenReturn(true);
        when(overdueBorrowed.getDueDate()).thenReturn(past);

        MediaItem notOverdueBorrowed = mock(MediaItem.class);
        when(notOverdueBorrowed.isBorrowed()).thenReturn(true);
        when(notOverdueBorrowed.getDueDate()).thenReturn(future);

        MediaItem notBorrowed = mock(MediaItem.class);
        when(notBorrowed.isBorrowed()).thenReturn(false);
        when(notBorrowed.getDueDate()).thenReturn(past);

        dbUser.setBorrowedItems(Arrays.asList(overdueBorrowed, notOverdueBorrowed, notBorrowed));

        when(userRepository.findByIdWithBorrowedItems(4)).thenReturn(Optional.of(dbUser));

        long result = userBorrowedItemsService.countOverdueItems(sessionUser);

        assertEquals(1, result);
    }

    @Test
    void getBorrowedItems_returnsEmptyList_whenSessionUserIsNull() {
        List<MediaItem> result = userBorrowedItemsService.getBorrowedItems(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getBorrowedItems_returnsEmptyList_whenUserNotFound() {
        User sessionUser = new User();
        sessionUser.setId(5);

        when(userRepository.findByIdWithBorrowedItems(5)).thenReturn(Optional.empty());

        List<MediaItem> result = userBorrowedItemsService.getBorrowedItems(sessionUser);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getBorrowedItems_returnsEmptyList_whenBorrowedItemsIsNull() {
        User sessionUser = new User();
        sessionUser.setId(6);

        User dbUser = new User();
        dbUser.setBorrowedItems(null);

        when(userRepository.findByIdWithBorrowedItems(6)).thenReturn(Optional.of(dbUser));

        List<MediaItem> result = userBorrowedItemsService.getBorrowedItems(sessionUser);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getBorrowedItems_returnsCopyOfBorrowedItems() {
        User sessionUser = new User();
        sessionUser.setId(7);

        User dbUser = new User();
        List<MediaItem> items = Arrays.asList(
                mock(MediaItem.class),
                mock(MediaItem.class)
        );
        dbUser.setBorrowedItems(items);

        when(userRepository.findByIdWithBorrowedItems(7)).thenReturn(Optional.of(dbUser));

        List<MediaItem> result = userBorrowedItemsService.getBorrowedItems(sessionUser);

        assertEquals(2, result.size());
        assertNotSame(items, result);
    }
}
