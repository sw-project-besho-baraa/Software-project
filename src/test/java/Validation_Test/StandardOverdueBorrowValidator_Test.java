package Validation_Test;


import Entity.MediaItem;
import Entity.User;
import Exception.OverdueItemsException;
import Validation.OverdueBorrowValidator.StandardOverdueBorrowValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StandardOverdueBorrowValidator_Test
{
    private StandardOverdueBorrowValidator validator;

    @BeforeEach
    void setup() {
        validator = new StandardOverdueBorrowValidator();
    }

    private Date daysAgo(long days) {
        return Date.from(LocalDate.now().minusDays(days).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private User userWithItems(long... daysAgoValues) {
        List<MediaItem> items = LongStream.of(daysAgoValues)
                .mapToObj(d -> {
                    MediaItem item = mock(MediaItem.class);
                    when(item.getBorrowedDate()).thenReturn(daysAgo(d));
                    when(item.getTitle()).thenReturn("Item " + d);
                    return item;
                })
                .collect(Collectors.toList());

        User user = mock(User.class);
        when(user.getBorrowedItems()).thenReturn(items);
        return user;
    }

    @Test
    void noItems_doesNotThrow() {
        User user = mock(User.class);
        when(user.getBorrowedItems()).thenReturn(List.of());
        assertDoesNotThrow(() -> validator.validate(user));
    }

    @Test
    void allWithinLimit_doesNotThrow() {
        assertDoesNotThrow(() -> validator.validate(userWithItems(10, 20, 28)));
    }

    @Test
    void singleOverdue_throwsException() {
        OverdueItemsException ex = assertThrows(OverdueItemsException.class, () -> validator.validate(userWithItems(30)));
        assertTrue(ex.getMessage().contains("overdue"));
    }

    @Test
    void multipleOverdue_throwsException() {
        OverdueItemsException ex = assertThrows(OverdueItemsException.class, () -> validator.validate(userWithItems(35, 50)));
        assertTrue(ex.getMessage().contains("Item 35"));
        assertTrue(ex.getMessage().contains("Item 50"));
    }
}