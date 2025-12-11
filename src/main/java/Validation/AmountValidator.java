package Validation;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

/**
 * Utility validator for parsing and validating monetary amounts.
 * <p>
 * Ensures proper numeric format, decimal precision, and optional maximum limits.
 */
@Component
public class AmountValidator
{
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    private static final int SCALE = 2;

    /**
     * Validates and parses a string amount, ensuring it follows numeric format
     * and is within the allowed range.
     *
     * @param raw         the raw amount string to validate
     * @param maxAllowed  optional maximum allowed value (nullable)
     * @return parsed {@link BigDecimal} value with 2 decimal places
     * @throws IllegalArgumentException if format is invalid or exceeds max allowed
     */
    public BigDecimal validateAndParse(String raw,BigDecimal maxAllowed)
    {
        if (raw == null)
        {
            throw new IllegalArgumentException("Amount is required.");
        }
        String s = raw.trim();
        if (s.isEmpty())
        {
            throw new IllegalArgumentException("Amount is required.");
        }
        s = s.replace(',','.');
        if (!AMOUNT_PATTERN.matcher(s).matches())
        {
            throw new IllegalArgumentException("Invalid amount format. Use digits with up to 2 decimals (e.g. 12.50).");
        }
        BigDecimal amount;
        try
        {
            amount = new BigDecimal(s).setScale(SCALE,RoundingMode.UNNECESSARY);
        } catch (ArithmeticException ae)
        {
            throw new IllegalArgumentException("Amount must have at most 2 decimal places.");
        } catch (NumberFormatException nfe)
        {
            throw new IllegalArgumentException("Invalid numeric amount.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }

        if (maxAllowed != null && amount.compareTo(maxAllowed) > 0)
        {
            throw new IllegalArgumentException(
                    "Amount exceeds allowed maximum of " + maxAllowed.setScale(SCALE,RoundingMode.HALF_UP));
        }

        return amount;
    }

    /**
     * Validates and parses a raw string amount with no maximum limit.
     *
     * @param raw the raw amount string
     * @return parsed {@link BigDecimal} value
     */
    public BigDecimal validateAndParse(String raw)
    {
        return validateAndParse(raw,null);
    }

    /**
     * Checks if the given string matches a valid numeric amount format.
     *
     * @param raw the string to check
     * @return true if valid, false otherwise
     */
    public boolean isValidFormat(String raw)
    {
        if (raw == null)
            return false;
        String s = raw.trim().replace(',','.');
        return !s.isEmpty() && AMOUNT_PATTERN.matcher(s).matches();
    }
}
