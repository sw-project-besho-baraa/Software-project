package Service_Test.BookService_Test;

import Entity.Book;
import Repository.BookRepository;
import Service.BookService.AddBookService;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class AddBookService_Test {

    @Test
    void addBook_callsRepositorySave() {
        BookRepository repo = mock(BookRepository.class);
        AddBookService service = new AddBookService(repo);
        Book book = new Book();
        service.addBook(book);
        verify(repo).save(book);
    }
}