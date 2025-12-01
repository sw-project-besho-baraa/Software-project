package Service.Book;

import Entity.Book;
import Repository.BookRepository;
import Session.ISessionManager;
import Session.LocalSessionManager;
import Validation.BookValidator;

public class AddBookService
{
    private ISessionManager sessionManager;
    private BookValidator bookValidator;
    private final BookRepository bookRepository;
    public AddBookService(BookRepository bookRepository, BookValidator bookValidator)
    {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
    }

    public void addBook(Book book)
    {
        bookValidator.validate(book);
        bookRepository.save(book);
    }
}
