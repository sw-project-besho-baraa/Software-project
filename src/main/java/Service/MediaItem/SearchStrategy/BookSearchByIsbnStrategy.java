package Service.Book.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;

import java.util.List;

public class BookSearchByIsbnStrategy implements IBookSearchStrategy<String>
{

    @Override
    public List<Book> searchBook(BookRepository bookRepository,String isbn)
    {
        return bookRepository.findByIsbnContainingIgnoreCase(isbn);
    }
}