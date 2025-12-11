package Service.BookService.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;
import java.util.List;

/**
 * Search strategy for finding books by title.
 */
public class BookSearchByTitleStrategy implements IBookSearchStrategy<String> {

    /**
     * Finds all books whose titles contain the given value.
     *
     * @param bookRepository book repository
     * @param title part or full book title
     * @return list of matching books
     */
    @Override
    public List<Book> searchBook(BookRepository bookRepository, String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
}
