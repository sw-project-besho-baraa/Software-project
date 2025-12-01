package Service.BookService;
import Entity.Book;
import Service.Book.BookSearchService;
import Service.Book.SearchStrategy.BookSearchStrategy;

import Validation.SearchValidator;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchService_Test {

    private BookSearchService bookSearchService;
    private BookSearchStrategy strategy;
    private SearchValidator searchValidator;

    @BeforeEach
    void setup() {
        bookSearchService = new BookSearchService();
        strategy = mock(BookSearchStrategy.class);
        searchValidator = mock(SearchValidator.class);
        bookSearchService.setStrategy(strategy, searchValidator);
    }

    @Test
    void search_WithStrategy_CallsStrategyAndReturnsResult() {
        String keyword = "Bara Obama";
        List<Book> expectedBooks = Collections.singletonList(mock(Book.class));
        when(strategy.searchBook(keyword)).thenReturn(expectedBooks);
        List<Book> result = bookSearchService.search(keyword);
        assertSame(expectedBooks, result);
    }

    @Test
    void search_WithoutStrategy_ThrowsIllegalStateException() {
        BookSearchService nullServiceStrategy = new BookSearchService();
        assertThrows(IllegalStateException.class, () -> nullServiceStrategy.search("besho"));
    }
    @Test
    void search_InvalidKey_ThrowsFromValidator() {
        String invalid = null;
        doThrow(new IllegalArgumentException("Search string cannot be null or empty")).when(searchValidator).validate(invalid);
        assertThrows(IllegalArgumentException.class, () -> bookSearchService.search(invalid));
    }
}
