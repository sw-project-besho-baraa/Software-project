package Service_Test.BookService_Test;

import Entity.Book;
import Repository.BookRepository;
import Repository.MediaItemRepository;
import Service.MediaItem.MediaItemService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddBookService_Test
{

    private MediaItemRepository repository;
    private MediaItemService addBookService;

    @BeforeEach
    void setup()
    {
        repository = mock(MediaItemRepository.class);
        addBookService = new MediaItemService(repository);
    }

    @Test
    void addBook_ValidBook_SavesBook()
    {
        Book book = mock(Book.class);
        addBookService.addItem(book);
        verify(repository,times(1)).save(book);

    }

    @Test
    void addBook_NullBook_ThrowsException()
    {
        assertThrows(NullPointerException.class,() -> addBookService.addItem(null));
    }
}
