package Service_Test.BookService_Test.SearchStrategy_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.BookService.SearchStrategy.BookSearchByIsbnStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchByIsbnStrategy_Test
{

    @Test
    void searchBook_callsRepositoryAndReturnsResult()
    {
        BookRepository repo = mock(BookRepository.class);
        Book book = new Book();
        book.setIsbn("12216904");
        when(repo.findByIsbnContainingIgnoreCase("1234")).thenReturn(List.of(book));
        BookSearchByIsbnStrategy strategy = new BookSearchByIsbnStrategy();
        List<Book> result = strategy.searchBook(repo,"1234");
        assertEquals(List.of(book),result);
        verify(repo).findByIsbnContainingIgnoreCase("1234");
    }
}