package Service.BookService.SearchStrategy_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.Book.SearchStrategy.BookSearchByIsbnStrategy;
import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchByIsbnStrategy_Test
{

    private BookRepository bookRepository;
    private BookSearchByIsbnStrategy strategy;

    @BeforeEach
    void setup()
    {
        bookRepository = mock(BookRepository.class);
        strategy = new BookSearchByIsbnStrategy();
    }

    @Test
    void searchBook_GoToRepositoryAndReturnsResult()
    {
        String isbn = "0569620188";
        List<Book> expectedBooks = Collections.singletonList(mock(Book.class));
        when(bookRepository.findByIsbnContainingIgnoreCase(isbn)).thenReturn(expectedBooks);
        List<Book> result = strategy.searchBook(bookRepository,isbn);
        assertSame(expectedBooks,result);
        verify(bookRepository,times(1)).findByIsbnContainingIgnoreCase(isbn);
    }

    @Test
    void searchBook_RepositoryReturnsEmptyList_ReturnsEmptyList()
    {
        String isbn = "0599662219";
        when(bookRepository.findByIsbnContainingIgnoreCase(isbn)).thenReturn(Collections.emptyList());
        List<Book> result = strategy.searchBook(bookRepository,isbn);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository,times(1)).findByIsbnContainingIgnoreCase(isbn);
    }
}
