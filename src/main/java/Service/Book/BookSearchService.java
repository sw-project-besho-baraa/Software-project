package Service.Book;

import Entity.Book;
import Service.Book.SearchStrategy.BookSearchStrategy;

import java.util.List;

public class BookSearchService {
    private BookSearchStrategy strategy;

    public void setStrategy(BookSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Book> search(String key) {
        if (strategy == null) {
            throw new IllegalStateException("Search strategy not set");
        }
        return strategy.searchBook(key);
    }
}
