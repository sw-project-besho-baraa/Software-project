package Service_Test.BookService_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.BookService.AllBookService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AllBookService_Test
{

    @Test
    void getAllBooks_returnsAllFromRepository()
    {
        BookRepository repo = mock(BookRepository.class);
        Book book = new Book();
        when(repo.findAll()).thenReturn(List.of(book));
        AllBookService service = new AllBookService(repo);
        assertEquals(List.of(book),service.getAllBooks());
        verify(repo).findAll();
    }
}