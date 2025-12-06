package Service_Test.BookService_Test.SearchStrategy_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.MediaItem.SearchStrategy.BookSearchByAuthorStrategy;

import org.junit.jupiter.api.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchByAuthorStrategy_Test
{

    private BookRepository bookRepository;
    private BookSearchByAuthorStrategy strategy;

    @BeforeEach
    void setup()
    {
        bookRepository = mock(BookRepository.class);
        strategy = new BookSearchByAuthorStrategy();
    }

    @Test
    void searchBook_goToRepositoryAndReturnsResult()
    {
        String author = "bashbosh";
        List<Book> expectedBooks = Collections.singletonList(mock(Book.class));
        when(bookRepository.findByAuthorContainingIgnoreCase(author)).thenReturn(expectedBooks);
        List<Book> result = strategy.searchBook(bookRepository,author);
        assertSame(expectedBooks,result);
        verify(bookRepository,times(1)).findByAuthorContainingIgnoreCase(author);
    }

    @Test
    void searchBook_RepositoryReturnsEmptyList_ReturnsEmptyList()
    {
        String author = "BarboryAuthor";
        when(bookRepository.findByAuthorContainingIgnoreCase(author)).thenReturn(Collections.emptyList());
        List<Book> result = strategy.searchBook(bookRepository,author);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(bookRepository,times(1)).findByAuthorContainingIgnoreCase(author);
    }
}
