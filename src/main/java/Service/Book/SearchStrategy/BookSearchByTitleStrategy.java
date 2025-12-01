package Service.Book.SearchStrategy;

import Entity.Book;
import Repository.BookRepository;

import java.util.List;

public class BookSearchByTitleStrategy implements BookSearchStrategy {
    private  BookRepository bookRepository;

    public  BookSearchByTitleStrategy(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> searchBook(String bookTitle) {
        return bookRepository.findByTitleContainingIgnoreCase(bookTitle);
    }
}
