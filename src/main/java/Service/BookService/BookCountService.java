package Service.BookService;

import Repository.BookRepository;

import org.springframework.stereotype.Service;

@Service
public class BookCountService {
    private BookRepository bookRepository;
    public BookCountService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public long countBooks() {
        return bookRepository.count();
    }

}
