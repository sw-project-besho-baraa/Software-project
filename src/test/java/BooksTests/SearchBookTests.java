package BooksTests;
import org.Library.BLL.Services.Classes.AuthenticationService;
import org.Library.BLL.Services.Classes.BookService;
import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.BLL.Services.Interfaces.IBookService;
import org.Library.DAL.Data.Users;
import org.Library.DAL.Data.Books;
import org.Library.DAL.Models.Book;
import org.Library.DAL.Repositories.Classes.BookRepository;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.Library.DAL.Repositories.Interfaces.IBookRepository;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SearchBookTests {
    private IBookService bookService;
    @BeforeEach
    void setup() {
        Users.UsersList.clear();
        Books.BooksList.clear();
        IUserRepository userRepo = new UserRepository();
        IBookRepository bookRepo = new BookRepository();
        IAuthenticationService auth = new AuthenticationService(userRepo);
        bookService = new BookService(auth, bookRepo);
        bookRepo.addBook(new Book("12216904", "besho", "bishawi"));
        bookRepo.addBook(new Book("12216900", "letsho", "abuhalima"));
        bookRepo.addBook(new Book("12216901", "bara", "bishawi"));
    }

    @Test
    void testSearchBookByISBNAndReturnMatches() {
        List<Book> results = bookService.searchForBooks("12216904");
        assertEquals(1, results.size());
    }
    @Test
    void testSearchBookByTitleAndReturnMatches() {
        List<Book> results = bookService.searchForBooks("besho");
        assertEquals(1, results.size());
    }
    @Test
    void testSearchBookByAuthorAndReturnMatches() {
        List<Book> results = bookService.searchForBooks("bishawi");
        assertEquals(2, results.size());
    }
    @Test
    void testSearchBookWithEmptyInput(){
        List<Book> results = bookService.searchForBooks("");
        assertEquals(3, results.size());
    }
    @Test
    void testSearchBookWithNullInput(){
        List<Book> results = bookService.searchForBooks(null);
        assertEquals(3, results.size());
    }


}
