package Service.Book.SearchStrategy;

import Entity.Book;

import java.util.List;

public interface BookSearchStrategy {
    List<Book> searchBook(String strategy);
}

