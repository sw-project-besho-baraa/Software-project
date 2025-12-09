package Service.BookService;

import Repository.BookRepository;

import org.springframework.stereotype.Service;

@Service
public class BooksService
{
    private BookRepository bookRepository;
    public BooksService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    public long countBooks()
    {
        return bookRepository.count();
    }

}
