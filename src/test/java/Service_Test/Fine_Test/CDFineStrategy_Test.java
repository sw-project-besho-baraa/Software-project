package Service_Test.Fine_Test;

import Service.Fine.CDFineStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CDFineStrategy_Test
{

    @Test
    void calculateFine_zeroOrNegative_returnsZero()
    {
        CDFineStrategy s = new CDFineStrategy();
        assertEquals(BigDecimal.ZERO,s.calculateFine(0));
        assertEquals(BigDecimal.ZERO,s.calculateFine(-2));
    }

    @Test
    void calculateFine_positive_returnsCorrectFine()
    {
        CDFineStrategy s = new CDFineStrategy();
        assertEquals(BigDecimal.valueOf(60),s.calculateFine(3)); // 3 * 20
    }
}