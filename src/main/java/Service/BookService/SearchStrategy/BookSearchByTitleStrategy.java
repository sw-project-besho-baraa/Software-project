package Service.BookService.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;

import java.util.List;

public class BookSearchByTitleStrategy implements IBookSearchStrategy<String>
{
    @Override
    public List<Book> searchBook(BookRepository bookRepository,String title)
    {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
