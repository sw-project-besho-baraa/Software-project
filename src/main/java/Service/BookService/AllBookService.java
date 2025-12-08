package Service.BookService;


import Entity.Book;
import Repository.BookRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AllBookService {
    BookRepository bookRepository;
    AllBookService( BookRepository bookRepository){this.bookRepository=bookRepository;}
    public Collection<@NonNull Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
