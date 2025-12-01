package Service.Book.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;

import java.util.List;

public interface IBookSearchStrategy<TValue>
{
    List<Book> searchBook(BookRepository bookRepository,TValue value);
}
