package org.Library.BLL.Services.Classes;

import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.BLL.Services.Interfaces.IBookService;
import org.Library.BLL.Services.Interfaces.IBorrowService;
import org.Library.BLL.Services.Interfaces.IUserService;
import org.Library.DAL.Repositories.Interfaces.IBorrowedBookRepository;

import java.time.LocalDate;

public class BorrowService implements IBorrowService {
    private IAuthenticationService authenticationService;
    private IBookService bookService;
    private IUserService userService;
    private String massageError=null;
    private IBorrowedBookRepository borrowedBookRepository;

    public BorrowService(IAuthenticationService authenticationService, IBookService bookService, IUserService userService,IBorrowedBookRepository borrowedBookRepository) {
        this.authenticationService = authenticationService;
        this.bookService = bookService;
        this.userService = userService;
        this.borrowedBookRepository = borrowedBookRepository;
    }


    @Override
    public boolean borrowBook(String ISBN, String userName) {
        if(userService.findUserByUserName(userName) == null) {
            massageError = "User not found";
            return false;
        }
        if(!authenticationService.isLoggedIn()){
            massageError = "You are not logged in";
            return false;
        }
        if (authenticationService.isAdmin()){
            massageError = "You are admin";
            return false;
        }
        if (bookService.searchForBooksByISBN(ISBN)==null){
            massageError = "Book not found";
            return false;
        }
        if (!bookService.isBookAvilable(ISBN)){
            massageError = "Book is unavailable";
            return false;
        }
        borrowedBookRepository.borrowBook(ISBN,userName,LocalDate.now());
        bookService.setAvailable(ISBN,false);
        return true ;
    }

    @Override
    public String getErrorMassage(){
        return massageError;
    }
}
