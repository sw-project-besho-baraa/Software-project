package Validation_Test;

import Entity.Item;
import Entity.User;
import Validation.BorrowValidator;
import Validation.OverdueBorrowValidator.IOverdueBorrowValidation;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowValidator_Test
{
    private IOverdueBorrowValidation overdueBorrowValidation;
    private BorrowValidator borrowValidator;

    @BeforeEach
    void setup()
    {
        overdueBorrowValidation = mock(IOverdueBorrowValidation.class);
        borrowValidator = new BorrowValidator(overdueBorrowValidation);
    }

    @Test
    void validate_ItemAlreadyBorrowed_ThrowsException() throws Exception
    {
        User user = mock(User.class);
        Item item = mock(Item.class);
        when(item.isBorrowed()).thenReturn(true);
        assertThrows(IllegalArgumentException.class,() -> borrowValidator.validate(user,item));
    }

    @Test
    void validate_ItemNotBorrowed_DoseNotThrows() throws Exception
    {
        User user = mock(User.class);
        Item item = mock(Item.class);
        when(item.isBorrowed()).thenReturn(false);
        assertDoesNotThrow(() -> borrowValidator.validate(user,item));
    }
}