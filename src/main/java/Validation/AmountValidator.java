package Validation;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

@Component
public class AmountValidator
{
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
    private static final int SCALE = 2;

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

    public BigDecimal validateAndParse(String raw)
    {
        return validateAndParse(raw,null);
    }

    public boolean isValidFormat(String raw)
    {
        if (raw == null)
            return false;
        String s = raw.trim().replace(',','.');
        return !s.isEmpty() && AMOUNT_PATTERN.matcher(s).matches();
    }
}