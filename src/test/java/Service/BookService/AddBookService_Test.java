package Service.BookService;

import Entity.Book;
import Repository.BookRepository;
import Service.Book.AddBookService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddBookService_Test
{

    private BookRepository bookRepository;
    private AddBookService addBookService;

    @BeforeEach
    void setup()
    {
        bookRepository = mock(BookRepository.class);
        addBookService = new AddBookService(bookRepository);
    }

    @Test
    void addBook_ValidBook_SavesBook() {
        Book book = mock(Book.class);
        addBookService.addBook(book);
        verify(bookRepository, times(1)).save(book);

    }

    @Test
    void addBook_NullBook_ThrowsException() {
        assertThrows(NullPointerException.class, () -> addBookService.addBook(null));
    }
}
