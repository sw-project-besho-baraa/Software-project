package Service.BookService;
import Entity.Book;
import Service.Book.BookSearchService;
import Service.Book.SearchStrategy.BookSearchStrategy;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchService_Test {

    private BookSearchService bookSearchService;
    private BookSearchStrategy strategy;

    @BeforeEach
    void setup() {
        bookSearchService = new BookSearchService();
        strategy = mock(BookSearchStrategy.class);
    }

    @Test
    void search_WithStrategy_CallsStrategyAndReturnsResult() {
        String keyword = "Java";
        List<Book> expectedBooks = Collections.singletonList(mock(Book.class));
        when(strategy.searchBook(keyword)).thenReturn(expectedBooks);
        bookSearchService.setStrategy(strategy);
        List<Book> result = bookSearchService.search(keyword);
        assertSame(expectedBooks, result);
    }

    @Test
    void search_WithoutStrategy_ThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> bookSearchService.search("anything"));
    }
}
