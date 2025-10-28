package BooksTests;
import org.Library.BLL.Services.Classes.AuthenticationService;
import org.Library.BLL.Services.Classes.BookService;
import org.Library.BLL.Services.Classes.BorrowService;
import org.Library.BLL.Services.Classes.UserService;
import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.BLL.Services.Interfaces.IBookService;
import org.Library.BLL.Services.Interfaces.IBorrowService;
import org.Library.BLL.Services.Interfaces.IUserService;
import org.Library.DAL.DTO.LoginRequest;
import org.Library.DAL.Models.Book;
import org.Library.DAL.Models.User;
import org.Library.DAL.Repositories.Classes.BookRepository;
import org.Library.DAL.Repositories.Classes.BorrowedBookRepository;
import org.Library.DAL.Repositories.Classes.UserRepository;
import org.Library.DAL.Repositories.Interfaces.IBookRepository;
import org.Library.DAL.Repositories.Interfaces.IBorrowedBookRepository;
import org.Library.DAL.Repositories.Interfaces.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class BorrowServiceTests {
    private IBookRepository bookRepository=new BookRepository();
    private IUserRepository userRepository=new UserRepository();
    private IAuthenticationService auth=new AuthenticationService(userRepository);
    private IBookService bookService=new BookService(auth,bookRepository);
    private IUserService userService=new UserService(userRepository);
    private IBorrowedBookRepository borrowedBookRepository=new BorrowedBookRepository();
    private IBorrowService borrowService=new BorrowService(auth,bookService,userService,borrowedBookRepository);
    @BeforeEach
    void setup() {
        User user = new User();
        user.setUserName("bara");
        user.setPassword("abu");
        user.setRole("user");
        userRepository.saveUser(user);
        User admin = new User();
        admin.setUserName("besho");
        admin.setPassword("123");
        admin.setRole("admin");
        userRepository.saveUser(admin);
        Book book =new Book("122","besholetsho","besho");
        bookRepository.addBook(book);
        bookRepository.setAvailable(book.getIsbn(),false);
        Book bookAvilable =new Book("222","besholetsho","besho");
        bookRepository.addBook(bookAvilable);
        bookRepository.setAvailable(bookAvilable.getIsbn(),true);

    }


    @Test
    void bookBorrowedUserNotFound() {
    borrowService.borrowBook("999","bara2");
    assertTrue(borrowService.getErrorMassage().equals("User not found"));
    }
    @Test
    void bookBorrowedBookNotFound() {
        auth.login(new LoginRequest("bara","abu"));
        borrowService.borrowBook("999","bara");
        System.out.println(borrowService.getErrorMassage());
        assertTrue(borrowService.getErrorMassage().equals("Book not found"));
    }
    @Test
    void bookISBNIsNull(){
        auth.login(new LoginRequest("bara","abu"));
        borrowService.borrowBook(null,"bara");
        System.out.println(borrowService.getErrorMassage());
        assertTrue(borrowService.getErrorMassage().equals("Book not found"));

    }
    @Test
    void bookISBNIsEmpty(){
        auth.login(new LoginRequest("bara","abu"));
        borrowService.borrowBook("","bara");
        System.out.println(borrowService.getErrorMassage());
        assertTrue(borrowService.getErrorMassage().equals("Book not found"));
    }

    @Test
    void bookBorrowedUserNotLoggedIn() {
        borrowService.borrowBook("999","bara");
        assertTrue(borrowService.getErrorMassage().equals("You are not logged in"));
    }
    @Test
    void bookBorrowedUserIsAdmin(){
        auth.login(new LoginRequest("besho","123"));
        borrowService.borrowBook("999","besho");
        assertTrue(borrowService.getErrorMassage().equals("You are admin"));
    }
    @Test
    void bookBorrowedBookIsunavailable(){
        auth.login(new LoginRequest("bara","abu"));
        borrowService.borrowBook("122","bara");
        assertTrue(borrowService.getErrorMassage().equals("Book is unavailable"));
    }

    @Test
    void bookBorrowedBookavailable(){
        auth.login(new LoginRequest("bara","abu"));
        assertTrue(borrowService.borrowBook("222","bara"));
    }



}
