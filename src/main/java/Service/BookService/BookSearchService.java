package Service.BookService;

import lombok.NonNull;
import Entity.Book;
import Repository.BookRepository;
import Service.BookService.SearchStrategy.IBookSearchStrategy;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookSearchService
{
    private final BookRepository bookRepository;
    public BookSearchService(@NonNull BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    public <TValue> List<Book> search(@NonNull IBookSearchStrategy<TValue> strategy,@NonNull TValue value)
    {
        return strategy.searchBook(bookRepository,value);
    }
}
