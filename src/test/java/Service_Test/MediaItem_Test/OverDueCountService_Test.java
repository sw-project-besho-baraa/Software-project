package Service_Test.MediaItem_Test;

import Entity.MediaItem;
import Service.BookService.AllBookService;
import Service.CDService.AllCdService;
import Service.MediaItem.OverDueCountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OverDueCountService_Test
{

    private AllBookService bookService;
    private AllCdService cdService;
    private OverDueCountService service;

    @BeforeEach
    void setUp()
    {
        bookService = mock(AllBookService.class);
        cdService = mock(AllCdService.class);
        service = new OverDueCountService(bookService, cdService);
    }

    @Test
    void countOverdueItems_mixedItems_returnsCorrectCount()
    {
        MediaItem overdue = mock(MediaItem.class);
        when(overdue.isBorrowed()).thenReturn(true);
        when(overdue.getDueDate()).thenReturn(new Date(System.currentTimeMillis() - 1000));

        MediaItem notOverdue = mock(MediaItem.class);
        when(notOverdue.isBorrowed()).thenReturn(true);
        when(notOverdue.getDueDate()).thenReturn(new Date(System.currentTimeMillis() + 100000));

        when(bookService.getAllBooks()).thenReturn((List) List.of(overdue));
        when(cdService.getAllCds()).thenReturn((List) List.of(notOverdue));

        long result = service.countOverdueItems();

        assertEquals(1,result);
    }

    @Test
    void countOverdueItems_noBorrowedOrNoDueDate_returnsZero()
    {
        MediaItem notBorrowed = mock(MediaItem.class);
        when(notBorrowed.isBorrowed()).thenReturn(false);

        when(bookService.getAllBooks()).thenReturn((List) List.of(notBorrowed));
        when(cdService.getAllCds()).thenReturn((List) Collections.emptyList());

        long result = service.countOverdueItems();

        assertEquals(0,result);
    }

    @Test
    void countOverdueItems_borrowedButDueDateNull_notCounted()
    {
        MediaItem borrowedNoDueDate = mock(MediaItem.class);
        when(borrowedNoDueDate.isBorrowed()).thenReturn(true);
        when(borrowedNoDueDate.getDueDate()).thenReturn(null);

        when(bookService.getAllBooks()).thenReturn((List) List.of(borrowedNoDueDate));
        when(cdService.getAllCds()).thenReturn((List) Collections.emptyList());

        long result = service.countOverdueItems();

        assertEquals(0,result);
    }
}
