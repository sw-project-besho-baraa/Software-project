package Service_Test.BookService_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.MediaItem.AddMediaItemService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddBookService_Test
{

    private BookRepository bookRepository;
    private AddMediaItemService addBookService;

    @BeforeEach
    void setup()
    {
        bookRepository = mock(BookRepository.class);
        addBookService = new AddMediaItemService(bookRepository);
    }

    @Test
    void addBook_ValidBook_SavesBook()
    {
        Book book = mock(Book.class);
        addBookService.addItem(book);
        verify(bookRepository,times(1)).save(book);

    }

    @Test
    void addBook_NullBook_ThrowsException()
    {
        assertThrows(NullPointerException.class,() -> addBookService.addItem(null));
    }
}
