package Service.Book.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;

import java.util.List;

public class BookSearchByAuthorStrategy implements BookSearchStrategy {
    private BookRepository bookRepository;

    public  BookSearchByAuthorStrategy(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> searchBook(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}


