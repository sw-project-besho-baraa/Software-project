package Service_Test.BookService_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.BookService.BookSearchService;
import Service.BookService.SearchStrategy.IBookSearchStrategy;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchService_Test {

    @Test
    void search_callsStrategyWithRepositoryAndValue() {
        BookRepository repo = mock(BookRepository.class);
        IBookSearchStrategy<String> strategy = mock(IBookSearchStrategy.class);
        Book book = new Book();
        when(strategy.searchBook(repo, "Robert")).thenReturn(List.of(book));
        BookSearchService service = new BookSearchService(repo);
        List<Book> result = service.search(strategy, "Robert");
        assertEquals(1, result.size());
        assertSame(book, result.get(0));
        verify(strategy).searchBook(repo, "Robert");
    }
}