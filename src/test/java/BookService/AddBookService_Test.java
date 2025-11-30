package BookService;
import Entity.Book;
import Repository.BookRepository;
import Service.Book.AddBookService;
import Validation.BookValidator;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AddBookService_Test {

    private BookRepository bookRepository;
    private BookValidator bookValidator;
    private AddBookService addBookService;

    @BeforeEach
    void setup() {
        bookRepository = mock(BookRepository.class);
        bookValidator = mock(BookValidator.class);
        addBookService = new AddBookService(bookRepository, bookValidator);
    }


    @Test
    void addBook_ValidBook_ValidatesAndSaves() {
        Book book = mock(Book.class);
        addBookService.addBook(book);
        verify(bookValidator, times(1)).validate(book);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void addBook_InvalidBook_ThrowsException() {
        Book book = mock(Book.class);
        doThrow(new IllegalArgumentException("Book cannot be null")).when(bookValidator).validate(book);
        assertThrows(IllegalArgumentException.class, () -> addBookService.addBook(book));
    }
}
