package Validation_Test;

import Entity.MediaItem;
import Entity.User;
import Enum.MediaItemType;
import Exception.OverdueItemsException;
import Validation.OverdueBorrowValidator.StandardOverdueBorrowValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StandardOverdueBorrowValidator_Test
{

    private StandardOverdueBorrowValidator validator;

    @BeforeEach
    void setUp()
    {
        validator = new StandardOverdueBorrowValidator();
    }

    @Test
    void validate_doesNotThrow_whenNoOverdueItems()
    {
        User user = new User();
        TestMediaItem item = new TestMediaItem("Book1");
        item.setBorrowed(true);
        item.setDueDate(LocalDateTime.now().plusDays(2));
        user.setBorrowedItems(List.of(item));
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    void validate_throwsException_whenOverdueItemsExist()
    {
        User user = new User();
        TestMediaItem item = new TestMediaItem("Book2");
        item.setBorrowed(true);
        item.setDueDate(LocalDateTime.now().minusDays(1)); // overdue
        user.setBorrowedItems(List.of(item));
        assertThrows(OverdueItemsException.class,() -> validator.validate(user));
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