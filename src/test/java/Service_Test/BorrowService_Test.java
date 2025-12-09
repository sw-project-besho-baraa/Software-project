package Service_Test;

import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Repository.UserRepository;
import Service.BorrowService;
import Validation.BorrowValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowService_Test
{

    private UserRepository userRepository;
    private BorrowValidator borrowValidator;
    private BorrowService borrowService;

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
        borrowValidator = mock(BorrowValidator.class);
        borrowService = new BorrowService(userRepository, borrowValidator);
    }

    @Test
    void borrow_throwsNullPointer_whenSessionUserIsNull()
    {
        TestMediaItem item = new TestMediaItem();
        item.setId(1);
        assertThrows(NullPointerException.class,() -> borrowService.borrow(null,item));
    }

    @Test
    void borrow_throwsNullPointer_whenItemIsNull()
    {
        User sessionUser = new User();
        sessionUser.setId(1);
        assertThrows(NullPointerException.class,() -> borrowService.borrow(sessionUser,null));
    }

    @Test
    void borrow_throwsIllegalState_whenUserNotFound()
    {
        User sessionUser = new User();
        sessionUser.setId(2);

        TestMediaItem item = new TestMediaItem();
        item.setId(10);

        when(userRepository.findById(2)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class,() -> borrowService.borrow(sessionUser,item));
    }

    @Test
    void borrow_updatesItemAndUserBorrowedItems_whenValid() throws Exception
    {
        User sessionUser = new User();
        sessionUser.setId(3);

        User dbUser = new User();
        dbUser.setId(3);
        dbUser.setBorrowedItems(new ArrayList<>());

        TestMediaItem item = new TestMediaItem();
        item.setId(20);

        when(userRepository.findById(3)).thenReturn(Optional.of(dbUser));

        borrowService.borrow(sessionUser,item);

        assertTrue(item.isBorrowed());
        assertNotNull(item.getBorrowedDate());
        assertNotNull(item.getDueDate());
        assertEquals(dbUser,item.getBorrower());
        assertEquals(1,dbUser.getBorrowedItems().size());
        assertSame(item,dbUser.getBorrowedItems().get(0));
    }

    @Test
    void borrow_throwsExceptionAndDoesNotChangeState_whenValidatorFails() throws Exception
    {
        User sessionUser = new User();
        sessionUser.setId(4);

        User dbUser = new User();
        dbUser.setId(4);
        dbUser.setBorrowedItems(new ArrayList<>());

        TestMediaItem item = new TestMediaItem();
        item.setId(30);

        when(userRepository.findById(4)).thenReturn(Optional.of(dbUser));
        doThrow(new Exception("Not allowed")).when(borrowValidator).validate(dbUser,item);

        assertThrows(Exception.class,() -> borrowService.borrow(sessionUser,item));

        assertFalse(item.isBorrowed());
        assertNull(item.getBorrowedDate());
        assertNull(item.getDueDate());
        assertNull(item.getBorrower());
        assertTrue(dbUser.getBorrowedItems().isEmpty());
    }
}
