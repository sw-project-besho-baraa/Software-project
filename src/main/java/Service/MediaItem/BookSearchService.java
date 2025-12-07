package Service.MediaItem;

import lombok.NonNull;
import Entity.Book;
import Repository.BookRepository;
import Service.MediaItem.SearchStrategy.IBookSearchStrategy;

import java.util.List;

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
