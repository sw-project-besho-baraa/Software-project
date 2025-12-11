package Service.BookService;

import lombok.NonNull;
import Entity.Book;
import Repository.BookRepository;
import Service.BookService.SearchStrategy.IBookSearchStrategy;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service that delegates book search requests to the selected strategy.
 */
@Service
public class BookSearchService {

    private final BookRepository bookRepository;

    /**
     * Creates a new book search service.
     *
     * @param bookRepository repository used for book queries
     */
    public BookSearchService(@NonNull BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Executes a search using the given strategy and value.
     *
     * @param strategy search strategy (e.g., by title, author, or ISBN)
     * @param value search value
     * @param <TValue> type of the search value
     * @return list of matching books
     * @see Service.BookService.SearchStrategy.IBookSearchStrategy
     */
    public <TValue> List<Book> search(@NonNull IBookSearchStrategy<TValue> strategy,
                                      @NonNull TValue value) {
        return strategy.searchBook(bookRepository, value);
    }
}
