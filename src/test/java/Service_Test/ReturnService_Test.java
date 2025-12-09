package Service_Test;

import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Repository.UserRepository;
import Service.ReturnService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReturnService_Test
{

    private UserRepository userRepository;
    private ReturnService returnService;

    private static class TestMediaItem extends MediaItem
    {
        public TestMediaItem()
        {
            super("Test");
        }

        @Override
        public MediaItemType getMediaType()
        {
            return MediaItemType.Book;
        }
    }

    @BeforeEach
    void setUp()
    {
        userRepository = mock(UserRepository.class);
        returnService = new ReturnService(userRepository);
    }

    @Test
    void returnItem_throwsNullPointer_whenSessionUserIsNull()
    {
        TestMediaItem item = new TestMediaItem();
        item.setId(1);
        assertThrows(NullPointerException.class,() -> returnService.returnItem(null,item));
    }

    @Test
    void returnItem_throwsNullPointer_whenItemIsNull()
    {
        User sessionUser = new User();
        sessionUser.setId(1);
        assertThrows(NullPointerException.class,() -> returnService.returnItem(sessionUser,null));
    }

    @Test
    void returnItem_clearsItemAndRemovesFromUserBorrowedItems_whenValid()
    {
        User sessionUser = new User();
        sessionUser.setId(1);

        User dbUser = new User();
        dbUser.setId(1);
        dbUser.setBorrowedItems(new ArrayList<>());

        TestMediaItem item = new TestMediaItem();
        item.setId(10);
        item.setBorrowed(true);
        item.setBorrower(dbUser);
        item.setBorrowedDate(new java.util.Date());
        item.setDueDate(new java.util.Date());

        dbUser.getBorrowedItems().add(item);

        when(userRepository.findById(1)).thenReturn(Optional.of(dbUser));

        returnService.returnItem(sessionUser,item);

        assertFalse(item.isBorrowed());
        assertNull(item.getBorrowedDate());
        assertNull(item.getDueDate());
        assertNull(item.getBorrower());
        assertTrue(dbUser.getBorrowedItems().isEmpty());
    }

    @Test
    void returnItem_throwsIllegalStateException_whenUserNotFound()
    {
        User sessionUser = new User();
        sessionUser.setId(2);

        TestMediaItem item = new TestMediaItem();
        item.setId(20);

        when(userRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,() -> returnService.returnItem(sessionUser,item));
    }

    @Test
    void returnItem_throwsIllegalStateException_whenItemNotInUserBorrowedItems()
    {
        User sessionUser = new User();
        sessionUser.setId(3);

        User dbUser = new User();
        dbUser.setId(3);
        dbUser.setBorrowedItems(new ArrayList<>());

        TestMediaItem existingItem = new TestMediaItem();
        existingItem.setId(99);
        dbUser.getBorrowedItems().add(existingItem);

        TestMediaItem item = new TestMediaItem();
        item.setId(30);

        when(userRepository.findById(3)).thenReturn(Optional.of(dbUser));

        assertThrows(IllegalStateException.class,() -> returnService.returnItem(sessionUser,item));
    }

    @Test
    void returnItem_throwsIllegalStateException_whenItemNotBorrowedOrHasNoBorrower()
    {
        User sessionUser = new User();
        sessionUser.setId(4);

        User dbUser = new User();
        dbUser.setId(4);
        dbUser.setBorrowedItems(new ArrayList<>());

        TestMediaItem item = new TestMediaItem();
        item.setId(40);
        item.setBorrowed(false);
        item.setBorrower(null);

        dbUser.getBorrowedItems().add(item);

        when(userRepository.findById(4)).thenReturn(Optional.of(dbUser));

        assertThrows(IllegalStateException.class,() -> returnService.returnItem(sessionUser,item));
    }
}
