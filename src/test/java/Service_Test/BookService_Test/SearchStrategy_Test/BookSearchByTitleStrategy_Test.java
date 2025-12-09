package Service_Test.BookService_Test.SearchStrategy_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.BookService.SearchStrategy.BookSearchByTitleStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookSearchByTitleStrategy_Test
{

    @Test
    void searchBook_callsRepositoryAndReturnsResult()
    {
        BookRepository repo = mock(BookRepository.class);
        Book book = new Book();
        when(repo.findByTitleContainingIgnoreCase("besho")).thenReturn(List.of(book));
        BookSearchByTitleStrategy strategy = new BookSearchByTitleStrategy();
        List<Book> result = strategy.searchBook(repo,"besho");
        assertEquals(List.of(book),result);
        verify(repo).findByTitleContainingIgnoreCase("besho");
    }
}