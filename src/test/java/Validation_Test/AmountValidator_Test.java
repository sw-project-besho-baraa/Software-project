package Validation_Test;

import Validation.AmountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AmountValidator_Test {

    private AmountValidator amountValidator;

    @BeforeEach
    void setUp() {
        amountValidator = new AmountValidator();
    }


    @Test
    void validateAndParse_validInteger_withoutMax_returnsBigDecimal() {
        BigDecimal result = amountValidator.validateAndParse("10");
        assertEquals(new BigDecimal("10.00"), result);
    }

    @Test
    void validateAndParse_validDecimalTwoPlaces_withoutMax_returnsBigDecimal() {
        BigDecimal result = amountValidator.validateAndParse("12.50");
        assertEquals(new BigDecimal("12.50"), result);
    }

    @Test
    void validateAndParse_validWithCommaDecimal_withoutMax_returnsBigDecimal() {
        BigDecimal result = amountValidator.validateAndParse("99,99");
        assertEquals(new BigDecimal("99.99"), result);
    }

    @Test
    void validateAndParse_nullRaw_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> amountValidator.validateAndParse(null)
        );
        assertEquals("Amount is required.", ex.getMessage());
    }

    @Test
    void validateAndParse_emptyString_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> amountValidator.validateAndParse("   ")
        );
        assertEquals("Amount is required.", ex.getMessage());
    }


    @Test
    void validateAndParse_invalidNumeric_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> amountValidator.validateAndParse("12.a3")
        );
        assertEquals("Invalid numeric amount.", ex.getMessage());
    }


    @Test
    void validateAndParse_moreThanTwoDecimals_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> amountValidator.validateAndParse("12.345")
        );
        assertEquals("Amount must have at most 2 decimal places.", ex.getMessage());
    }


    @Test
    void validateAndParse_zeroAmount_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> amountValidator.validateAndParse("0")
        );
        assertEquals("Amount must be greater than zero.", ex.getMessage());
    }

    @Test
    void validateAndParse_negativeAmount_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> amountValidator.validateAndParse("-5")
        );
        assertEquals("Amount must be greater than zero.", ex.getMessage());
    }


    @Test
    void validateAndParse_withMax_validLessThanMax_returnsAmount() {
        BigDecimal maxAllowed = new BigDecimal("100.00");
        BigDecimal result = amountValidator.validateAndParse("50.00", maxAllowed);
        assertEquals(new BigDecimal("50.00"), result);
    }

    @Test
    void validateAndParse_withMax_equalToMax_returnsAmount() {
        BigDecimal maxAllowed = new BigDecimal("100.00");
        BigDecimal result = amountValidator.validateAndParse("100.00", maxAllowed);
        assertEquals(new BigDecimal("100.00"), result);
    }

    @Test
    void validateAndParse_withMax_exceedsMax_throwsIllegalArgumentException() {
        BigDecimal maxAllowed = new BigDecimal("100.00");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> amountValidator.validateAndParse("100.01", maxAllowed)
        );

        assertEquals(
                "Amount exceeds allowed maximum of " + maxAllowed.setScale(2),
                ex.getMessage()
        );
    }


    @Test
    void isValidFormat_null_returnsFalse() {
        assertFalse(amountValidator.isValidFormat(null));
    }

    @Test
    void isValidFormat_empty_returnsFalse() {
        assertFalse(amountValidator.isValidFormat("   "));
    }

    @Test
    void isValidFormat_validInteger_returnsTrue() {
        assertTrue(amountValidator.isValidFormat("10"));
    }

    @Test
    void isValidFormat_validDecimalTwoPlaces_returnsTrue() {
        assertTrue(amountValidator.isValidFormat("10.50"));
    }

    @Test
    void isValidFormat_validCommaDecimal_returnsTrue() {
        assertTrue(amountValidator.isValidFormat("10,50"));
    }

    @Test
    void isValidFormat_moreThanTwoDecimals_returnsFalse() {
        assertFalse(amountValidator.isValidFormat("10.123"));
    }

    @Test
    void isValidFormat_containsLetters_returnsFalse() {
        assertFalse(amountValidator.isValidFormat("10a.5"));
    }
}
