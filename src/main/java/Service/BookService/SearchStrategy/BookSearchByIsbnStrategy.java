package Service.BookService.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;
import java.util.List;

/**
 * Search strategy for finding books by ISBN.
 */
public class BookSearchByIsbnStrategy implements IBookSearchStrategy<String>
{

    /**
     * Finds all books whose ISBN contains the given value.
     *
     * @param bookRepository
     *            book repository
     * @param isbn
     *            part or full ISBN number
     * @return list of matching books
     */
    @Override
    public List<Book> searchBook(BookRepository bookRepository,String isbn)
    {
        return bookRepository.findByIsbnContainingIgnoreCase(isbn);
    }
}
