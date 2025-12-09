package Validation_Test;

import Entity.MediaItem;
import Entity.User;
import Validation.BorrowValidator;
import Validation.OverdueBorrowValidator.IOverdueBorrowValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

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
    void validate_itemAlreadyBorrowed_throwsIllegalArgumentException()
    {
        User user = mock(User.class);
        MediaItem item = mock(MediaItem.class);
        when(item.isBorrowed()).thenReturn(true);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> borrowValidator.validate(user, item));
        assertEquals("Item is already borrowed", ex.getMessage());
        verify(overdueBorrowValidation, never()).validate(any());
    }

    @Test
    void validate_itemNotBorrowed_noFine_doesNotThrow()
    {
        User user = mock(User.class);
        MediaItem item = mock(MediaItem.class);
        when(item.isBorrowed()).thenReturn(false);
        when(user.getFineBalance()).thenReturn(null);
        assertDoesNotThrow(() -> borrowValidator.validate(user, item));
        verify(overdueBorrowValidation, times(1)).validate(user);
    }

    @Test
    void validate_itemNotBorrowed_withPositiveFine_throwsIllegalStateException()
    {
        User user = mock(User.class);
        MediaItem item = mock(MediaItem.class);
        when(item.isBorrowed()).thenReturn(false);
        when(user.getFineBalance()).thenReturn(new BigDecimal("5.00"));
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> borrowValidator.validate(user, item));
        assertEquals("fine: please pay full balance before borrowing.", ex.getMessage());
        verify(overdueBorrowValidation, times(1)).validate(user);
    }

    @Test
    void validate_itemNotBorrowed_withZeroFine_doesNotThrow()
    {
        User user = mock(User.class);
        MediaItem item = mock(MediaItem.class);
        when(item.isBorrowed()).thenReturn(false);
        when(user.getFineBalance()).thenReturn(BigDecimal.ZERO);
        assertDoesNotThrow(() -> borrowValidator.validate(user, item));
        verify(overdueBorrowValidation, times(1)).validate(user);
    }
}
