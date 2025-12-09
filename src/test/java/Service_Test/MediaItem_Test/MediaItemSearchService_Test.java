package Service_Test.MediaItem_Test;


import Entity.Book;
import Entity.Cd;
import Entity.MediaItem;
import Service.BookService.BookSearchService;
import Service.CDService.CdSearchService;
import Service.MediaItem.MediaItemSearchService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MediaItemSearchService_Test {

    @Test
    void searchByMode_nullOrBlank_returnsEmptyList() {
        var bookService = mock(BookSearchService.class);
        var cdService = mock(CdSearchService.class);
        var service = new MediaItemSearchService(bookService, cdService);
        assertTrue(service.searchByMode(null, "hi").isEmpty());
        assertTrue(service.searchByMode("mode", null).isEmpty());
        assertTrue(service.searchByMode("mode", " ").isEmpty());
    }

    @Test
    void searchByMode_titleMode_mergesBookAndCdResults() {
        var bookService = mock(BookSearchService.class);
        var cdService = mock(CdSearchService.class);
        var service = new MediaItemSearchService(bookService, cdService);
        Book b = mock(Book.class);
        Cd c = mock(Cd.class);
        when(bookService.search(any(), eq("hello"))).thenReturn(List.of(b));
        when(cdService.search(any(), eq("hello"))).thenReturn(List.of(c));
        List<MediaItem> result = service.searchByMode("Title (Books & CDs)", "hello");
        assertEquals(2, result.size());
        verify(bookService).search(any(), eq("hello"));
        verify(cdService).search(any(), eq("hello"));
    }

    @Test
    void searchByMode_authorAndIsbn_onlyUsesBookService() {
        var bookService = mock(BookSearchService.class);
        var cdService = mock(CdSearchService.class);
        var service = new MediaItemSearchService(bookService, cdService);
        when(bookService.search(any(), eq("keyword"))).thenReturn(List.of(mock(Book.class)));
        assertEquals(1, service.searchByMode("Book Author", "keyword").size());
        assertEquals(1, service.searchByMode("Book ISBN", "keyword").size());
        verify(bookService, times(2)).search(any(), eq("keyword"));
        verifyNoInteractions(cdService);
    }
}