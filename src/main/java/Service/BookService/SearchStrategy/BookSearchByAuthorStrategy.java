package Service.BookService.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;
import java.util.List;

/**
 * Search strategy for finding books by author name.
 */
public class BookSearchByAuthorStrategy implements IBookSearchStrategy<String> {

    /**
     * Finds all books whose author names contain the given keyword.
     *
     * @param bookRepository book repository
     * @param author part or full name of the author
     * @return list of matching books
     */
    @Override
    public List<Book> searchBook(BookRepository bookRepository, String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}
