package Service.Book.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;

import java.util.List;

public class BookSearchByIsbnStrategy implements BookSearchStrategy {
    private BookRepository bookRepository;

    public  BookSearchByIsbnStrategy (BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> searchBook(String isbn ) {
        return bookRepository.findByIsbnContainingIgnoreCase(isbn);
    }
}