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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowService_Test
{

    private UserRepository userRepository;
    private BorrowValidator borrowValidator;
    private BorrowService borrowService;

    @BeforeEach
    void setUp()
    {
        userRepository = mock(UserRepository.class);
        borrowValidator = mock(BorrowValidator.class);
        borrowService = new BorrowService(userRepository, borrowValidator);
    }

    @Test
    void borrow_success_updatesItemAndUserAndCallsSave() throws Exception
    {
        User user = new User();
        user.setBorrowedItems(new ArrayList<>());
        TestMediaItem item = new TestMediaItem("Book1");
        doNothing().when(borrowValidator).validate(user,item);

        borrowService.borrow(user,item);

        verify(borrowValidator).validate(user,item);
        verify(userRepository).save(user);
        assertTrue(item.isBorrowed());
        assertNotNull(item.getBorrowedDate());
        assertNotNull(item.getDueDate());
        assertEquals(user,item.getBorrower());
        assertTrue(user.getBorrowedItems().contains(item));
    }

    @Test
    void borrow_validationFails_throwsExceptionAndDoesNotSave() throws Exception
    {
        User user = new User();
        user.setBorrowedItems(new ArrayList<>());
        TestMediaItem item = new TestMediaItem("Book2");
        doThrow(new RuntimeException("Validation failed")).when(borrowValidator).validate(user,item);

        assertThrows(RuntimeException.class,() -> borrowService.borrow(user,item));

        verify(borrowValidator).validate(user,item);
        verifyNoInteractions(userRepository);
        assertFalse(item.isBorrowed());
        assertNull(item.getBorrowedDate());
        assertNull(item.getDueDate());
        assertNull(item.getBorrower());
        assertFalse(user.getBorrowedItems().contains(item));
    }

    @Test
    void borrow_nullUser_throwsNullPointerException_beforeValidation()
    {
        TestMediaItem item = new TestMediaItem("Book3");
        assertThrows(NullPointerException.class,() -> borrowService.borrow(null,item));
        verifyNoInteractions(borrowValidator,userRepository);
    }

    @Test
    void borrow_nullItem_throwsNullPointerException_beforeValidation()
    {
        User user = new User();
        assertThrows(NullPointerException.class,() -> borrowService.borrow(user,null));
        verifyNoInteractions(borrowValidator,userRepository);
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