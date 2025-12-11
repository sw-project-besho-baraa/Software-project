package Service.BookService;

import Repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * Service for general book-related operations.
 */
@Service
public class BooksService
{

    private final BookRepository bookRepository;

    /**
     * Creates a new instance of the book service.
     *
     * @param bookRepository
     *            repository for book data
     */
    public BooksService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    /**
     * Counts the total number of books.
     *
     * @return total book count
     */
    public long countBooks()
    {
        return bookRepository.count();
    }
}
