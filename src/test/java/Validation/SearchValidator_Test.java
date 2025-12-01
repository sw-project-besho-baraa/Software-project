package Validation;

import Entity.Book;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SearchValidator_Test {

    private SearchValidator searchValidator;

    @BeforeEach
    void setup() {
        searchValidator = new SearchValidator();
    }
    @Test
    void validate_ValidString_DoesNotThrow() {
        assertDoesNotThrow(() -> searchValidator.validate("nihad"));
        assertDoesNotThrow(() -> searchValidator.validate("bara bishawi"));
        assertDoesNotThrow(() -> searchValidator.validate("bara123"));
        assertDoesNotThrow(() -> searchValidator.validate("Bara"));

    }
    @Test
    void validate_Null_ThrowsIllegalArgumentException() {
        Exception exception = assertThrows(IllegalArgumentException.class,() -> searchValidator.validate(null));
        assertEquals("Search string cannot be null or empty", exception.getMessage());
    }
}