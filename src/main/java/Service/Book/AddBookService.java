package Service.Book;

import Entity.Book;
import Repository.BookRepository;
import Session.ISessionManager;
import lombok.NonNull;

public class AddBookService
{
    private final BookRepository bookRepository;
    public AddBookService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    public void addBook(@NonNull Book book)
    {
        bookRepository.save(book);
    }
}
