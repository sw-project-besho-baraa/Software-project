package Service.Book;

import Entity.Book;
import Service.Book.SearchStrategy.BookSearchStrategy;
import Validation.BookValidator;
import Validation.SearchValidator;

import java.util.List;

public class BookSearchService {
    private BookSearchStrategy strategy;
    private SearchValidator searchValidator;
    public void setStrategy(BookSearchStrategy strategy, SearchValidator searchValidator) {
        this.strategy = strategy;
        this.searchValidator = searchValidator;
    }

    public List<Book> search(String key) {
        if (strategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        searchValidator.validate(key);
        return strategy.searchBook(key);
    }
}
