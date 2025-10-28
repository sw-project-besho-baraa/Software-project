package BooksTests;
import org.Library.BLL.Services.Classes.AuthenticationService;
import org.Library.BLL.Services.Classes.BookService;
import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.BLL.Services.Interfaces.IBookService;
import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Models.Book;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Classes.BookRepository;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.Library.DAL.Repositories.Interfaces.IBookRepository;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class AddBookTests {
    IUserRepository userRepo   = new UserRepository();
    IBookRepository bookRepo   = new BookRepository();
    private IAuthenticationService authService=new AuthenticationService(userRepo);
    private IBookService bookService=new BookService(authService,bookRepo);

    private void createData(String userName, String password,String Role) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setRole(Role);
        userRepo.saveUser(user);
    }
    @Test
    void addBookAfterLoginAsAdmin() {
        createData("admin","admin","admin");
        authService.login(new LoginRequest("admin", "admin"));
        assertTrue(bookService.addBook( new Book("isbn", "title", "author")));
    }
    @Test
    void addBookAfterLoginAsUser() {
        createData("user","user","user");
        authService.login(new LoginRequest("user", "user"));
        assertFalse(bookService.addBook( new Book("isbn", "title", "author")));
    }
    @Test
    void addBookBeforeLogin() {
        createData("admin","admin","admin");
        assertFalse(bookService.addBook( new Book("isbn", "title", "author")));
    }
}
