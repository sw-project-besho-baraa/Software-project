package Service_Test.BookService_Test.SearchStrategy_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.MediaItem.SearchStrategy.BookSearchByTitleStrategy;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchByTitleStrategy_Test
{
    private BookRepository bookRepository;
    private BookSearchByTitleStrategy strategy;

    @BeforeEach
    void setup()
    {
        bookRepository = mock(BookRepository.class);
        strategy = new BookSearchByTitleStrategy();
    }

    @Test
    void searchBook_GoToRepositoryAndReturnsResult()
    {
        String title = "abuhalima";
        List<Book> expected = Collections.singletonList(mock(Book.class));
        when(bookRepository.findByTitleContainingIgnoreCase(title)).thenReturn(expected);
        List<Book> result = strategy.searchBook(bookRepository,title);
        assertSame(expected,result);
        verify(bookRepository,times(1)).findByTitleContainingIgnoreCase(title);
    }

    @Test
    void searchBook_RepositoryReturnsEmptyList_ReturnsEmptyList()
    {
        String title = "bashboshnotfound";
        when(bookRepository.findByTitleContainingIgnoreCase(title)).thenReturn(Collections.emptyList());
        List<Book> result = strategy.searchBook(bookRepository,title);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository,times(1)).findByTitleContainingIgnoreCase(title);
    }
}
