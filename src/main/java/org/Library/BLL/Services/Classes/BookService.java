package org.Library.BLL.Services.Classes;

import org.Library.BLL.Services.Interfaces.IAuthenticationService;
import org.Library.BLL.Services.Interfaces.IBookService;
import org.Library.DAL.Data.Books;
import org.Library.DAL.Models.Book;
import org.Library.DAL.Repositories.Interfaces.IBookRepository;

import java.util.List;

import static org.Library.DAL.Data.Books.BooksList;


public class BookService implements IBookService {
    public IBookRepository bookRepository;
    public IAuthenticationService authenticationService;
    public BookService(IAuthenticationService authenticationService, IBookRepository bookRepository)
    {
    this.authenticationService = authenticationService;
    this.bookRepository = bookRepository;
    }

    @Override
    public boolean addBook(Book book) {
        if (!authenticationService.isLoggedIn()){
            return false;
        }
        if (!authenticationService.isAdmin()){
            return false;
        }
        bookRepository.addBook(book);
        return true;
    }

    @Override
    public List<Book> searchForBooks(String bookDetails) {
        return bookRepository.search(bookDetails);
    }

    @Override
    public Book searchForBooksByISBN(String isbn) {
        return bookRepository.searchByISBN(isbn);
    }

    @Override
    public boolean isBookAvilable(String isbn) {
        return bookRepository.isBookAvilable(isbn);
    }

    @Override
    public void setAvailable(String isbn, boolean b) {
        bookRepository.setAvailable(isbn, b);
    }
}
