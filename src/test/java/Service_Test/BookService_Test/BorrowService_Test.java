package Service_Test.BookService_Test;

import Entity.Item;
import Entity.User;
import Repository.UserRepository;
import Service.BorrowService;
import Validation.BorrowValidator;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowService_Test
{
    private UserRepository userRepository;
    private BorrowValidator borrowValidator;
    private BorrowService borrowService;

    @BeforeEach
    void setup()
    {
        userRepository = mock(UserRepository.class);
        borrowValidator = mock(BorrowValidator.class);
        borrowService = new BorrowService(userRepository, borrowValidator);
    }

    @Test
    void borrow_ValidUserAndItem_ReturnsTrue() throws Exception
    {
        User user = mock(User.class);
        Item item = mock(Item.class);
        List<Item> borrowedItems = new ArrayList<>();
        when(user.getBorrowedItems()).thenReturn(borrowedItems);
        borrowService.borrow(user,item);
        assertTrue(borrowedItems.contains(item));
    }

    @Test
    void borrow_InvalidBorrow_ThrowsException() throws Exception
    {
        User user = mock(User.class);
        Item item = mock(Item.class);
        List<Item> borrowedItems = new ArrayList<>();
        when(user.getBorrowedItems()).thenReturn(borrowedItems);
        doThrow(new Exception("invalid borrow")).when(borrowValidator).validate(user,item);
        assertThrows(Exception.class,() -> borrowService.borrow(user,item));
        assertTrue(borrowedItems.isEmpty());
    }

    @Test
    void borrow_NullUser_ThrowsNullPointerException()
    {
        Item item = mock(Item.class);
        assertThrows(NullPointerException.class,() -> borrowService.borrow(null,item));
    }

    @Test
    void borrow_NullItem_ThrowsNullPointerException()
    {
        User user = mock(User.class);
        assertThrows(NullPointerException.class,() -> borrowService.borrow(user,null));
    }
}
