package Service.BookService.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;
import java.util.List;

public class BookSearchByAuthorStrategy implements IBookSearchStrategy<String>
{

    @Override
    public List<Book> searchBook(BookRepository bookRepository,String author)
    {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}
