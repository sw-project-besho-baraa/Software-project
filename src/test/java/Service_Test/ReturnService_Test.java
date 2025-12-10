package Service_Test;

import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Exception.ItemNotBorrowedByUserException;
import Repository.UserRepository;
import Service.ReturnItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReturnService_Test {

    private UserRepository userRepository;
    private ReturnItemService returnService;

    private static class TestMediaItem extends MediaItem {
        public TestMediaItem() {
            super("Test");
        }
        @Override
        public MediaItemType getMediaType() {
            return MediaItemType.Book;
        }
    }

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        returnService = new ReturnItemService(userRepository);
    }

    @Test
    void returnItem_throwsNullPointer_whenUserIsNull() {
        TestMediaItem item = new TestMediaItem();
        assertThrows(NullPointerException.class, () -> returnService.returnItem(null, item));
        verifyNoInteractions(userRepository);
    }

    @Test
    void returnItem_throwsNullPointer_whenItemIsNull() {
        User user = new User();
        user.setBorrowedItems(new ArrayList<>());
        assertThrows(NullPointerException.class, () -> returnService.returnItem(user, null));
        verifyNoInteractions(userRepository);
    }

    @Test
    void returnItem_throwsItemNotBorrowedByUserException_whenItemNotInUserBorrowedItems() {
        User user = new User();
        user.setBorrowedItems(new ArrayList<>());
        TestMediaItem item = new TestMediaItem();
        assertThrows(ItemNotBorrowedByUserException.class, () -> returnService.returnItem(user, item));
        verifyNoInteractions(userRepository);
    }

    @Test
    void returnItem_clearsItemAndRemovesFromUserBorrowedItems_whenValid() {
        User user = new User();
        user.setBorrowedItems(new ArrayList<>());

        TestMediaItem item = new TestMediaItem();
        item.setBorrowed(true);
        item.setBorrower(user);
        item.setBorrowedDate(java.time.LocalDateTime.now());
        item.setDueDate(java.time.LocalDateTime.now());
        user.getBorrowedItems().add(item);

        returnService.returnItem(user, item);

        assertFalse(item.isBorrowed());
        assertNull(item.getBorrowedDate());
        assertNull(item.getDueDate());
        assertNull(item.getBorrower());
        assertTrue(user.getBorrowedItems().isEmpty());
        verify(userRepository).save(user);
    }
}