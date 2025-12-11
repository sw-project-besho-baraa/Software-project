package Service_Test.BookService_Test;

import Repository.BookRepository;
import Service.BookService.BooksService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BooksService_Test {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BooksService booksService;

    @Test
    void countBooks_whenRepositoryReturnsZero_returnsZero() {
        when(bookRepository.count()).thenReturn(0L);
        long result = booksService.countBooks();
        assertEquals(0L, result);
        verify(bookRepository, times(1)).count();
        verifyNoMoreInteractions(bookRepository);
    }

    @Test
    void countBooks_whenRepositoryReturnsPositiveValue_returnsSameValue() {
        when(bookRepository.count()).thenReturn(100L);
        long result = booksService.countBooks();
        assertEquals(100L, result);
        verify(bookRepository, times(1)).count();
        verifyNoMoreInteractions(bookRepository);
    }
}