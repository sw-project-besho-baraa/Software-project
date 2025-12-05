package Service_Test.BookService_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.Book.BookSearchService;
import Service.Book.SearchStrategy.IBookSearchStrategy;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchService_Test
{

    private BookRepository bookRepository;
    private BookSearchService bookSearchService;
    private IBookSearchStrategy<String> strategy;

    @BeforeEach
    void setup()
    {
        bookRepository = mock(BookRepository.class);
        bookSearchService = new BookSearchService(bookRepository);
        strategy = mock(IBookSearchStrategy.class);
    }

    @Test
    void search_WithStrategy_ReturnsExpectedBooks()
    {
        String keyword = "Bara Obama";
        List<Book> expectedBooks = Collections.singletonList(mock(Book.class));
        when(strategy.searchBook(bookRepository,keyword)).thenReturn(expectedBooks);
        List<Book> result = bookSearchService.search(strategy,keyword);
        assertEquals(expectedBooks,result,"The search result should match the mocked books");
        verify(strategy,times(1)).searchBook(bookRepository,keyword);
    }

    @Test
    void search_NullStrategy_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class,() -> bookSearchService.search(null,"test"),
                "Expected NullPointerException when strategy is null");
    }

    @Test
    void search_NullValue_ThrowsNullPointerException()
    {
        assertThrows(NullPointerException.class,() -> bookSearchService.search(strategy,null),
                "Expected NullPointerException when value is null");
    }
}
