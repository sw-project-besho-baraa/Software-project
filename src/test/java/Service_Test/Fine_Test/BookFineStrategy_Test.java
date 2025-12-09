package Service_Test.Fine_Test;

import Service.Fine.BookFineStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BookFineStrategy_Test
{

    @Test
    void calculateFine_returnsZeroIfNoOverdue()
    {
        BookFineStrategy s = new BookFineStrategy();
        assertEquals(BigDecimal.ZERO,s.calculateFine(0));
        assertEquals(BigDecimal.ZERO,s.calculateFine(-5));
    }

    @Test
    void calculateFine_returnsCorrectValueForOverdueDays()
    {
        BookFineStrategy s = new BookFineStrategy();
        assertEquals(BigDecimal.valueOf(30),s.calculateFine(3));
    }
}