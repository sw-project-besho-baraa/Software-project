package Service_Test.BookService_Test;

import Repository.BookRepository;
import Service.BookService.BookCountService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookCountService_Test
{

    @Test
    void countBooks_returnsRepositoryCount()
    {
        BookRepository repo = mock(BookRepository.class);
        when(repo.count()).thenReturn(10L);

        BookCountService service = new BookCountService(repo);
        assertEquals(10L,service.countBooks());
        verify(repo).count();
    }
}