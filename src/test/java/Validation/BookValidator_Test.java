package Validation;

import Entity.Book;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

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
