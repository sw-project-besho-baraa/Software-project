package Service_Test.BookService_Test.SearchStrategy_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.BookService.SearchStrategy.BookSearchByAuthorStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookSearchByAuthorStrategy_Test {

    @Test
    void searchBook_callsRepositoryAndReturnsResult() {
        BookRepository repo = mock(BookRepository.class);
        Book book = new Book();
        book.setAuthor("Mohammad");
        when(repo.findByAuthorContainingIgnoreCase("Mohammad")).thenReturn(List.of(book));
        BookSearchByAuthorStrategy strategy = new BookSearchByAuthorStrategy();
        List<Book> result = strategy.searchBook(repo, "Mohammad");
        assertEquals(List.of(book), result);
        verify(repo).findByAuthorContainingIgnoreCase("Mohammad");
    }
}
