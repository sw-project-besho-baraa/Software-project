package Service.BookService.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;
import java.util.List;

/**
 * Defines a strategy for searching books using different criteria.
 *
 * @param <TValue> type of the search value (e.g., String)
 */
public interface IBookSearchStrategy<TValue> {

    /**
     * Searches for books based on the provided value.
     *
     * @param bookRepository book repository
     * @param value search value (e.g., title, author, or ISBN)
     * @return list of matching books
     */
    List<Book> searchBook(BookRepository bookRepository, TValue value);
}
