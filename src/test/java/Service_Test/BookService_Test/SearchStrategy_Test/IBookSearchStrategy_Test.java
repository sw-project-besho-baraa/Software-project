package Service_Test.BookService_Test.SearchStrategy_Test;


import Entity.Book;
import Service.BookService.SearchStrategy.IBookSearchStrategy;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IBookSearchStrategy_Test {

    @Test
    void customImplementation_callsSearchMethod() {
        IBookSearchStrategy<String> strategy = (repo, value) -> {Book b = new Book();b.setTitle("Test Book");return List.of(b);};
        List<Book> result = strategy.searchBook(null, "anything");
        assertEquals(1, result.size());
        assertEquals("Test Book", result.get(0).getTitle());
    }
}