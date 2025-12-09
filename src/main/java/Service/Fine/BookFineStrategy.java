package Service.Fine;

import java.math.BigDecimal;

public class BookFineStrategy implements FineStrategy
{
    private static final BigDecimal RATE_PER_DAY = BigDecimal.valueOf(10); // 10 NIS per overdue day

    @Override
    public BigDecimal calculateFine(int overdueDays)
    {
        if (overdueDays <= 0)
            return BigDecimal.ZERO;
        return RATE_PER_DAY.multiply(BigDecimal.valueOf(overdueDays));
    }
}