
import Entity.Book;
import Validation.BookValidator;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class BookValidator_Test {
    private BookValidator validator;
    @BeforeEach
    void setup() {
        validator = new BookValidator();
    }
    @Test
    void validate_NullBook_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
    }

    @Test
    void validate_NonNullBook_DoesNotThrowException() {
        Book book = new Book();
        assertDoesNotThrow(() -> validator.validate(book));
    }
}
