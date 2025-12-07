package Service.BookService;

import Entity.Book;
import Repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class AddBookService {
    private final BookRepository repository;
    public AddBookService(BookRepository repository)
    {
        this.repository = repository;
    }
    public void addBook(Book book)
    {
        repository.save(book);
    }

}
