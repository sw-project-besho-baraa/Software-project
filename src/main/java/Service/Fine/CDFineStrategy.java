package Service.Fine;

import java.math.BigDecimal;

public class CDFineStrategy implements FineStrategy {
    private static final BigDecimal RATE_PER_DAY = BigDecimal.valueOf(20);
    @Override
    public BigDecimal calculateFine(int overdueDays) {
        if (overdueDays <= 0) return BigDecimal.ZERO;
        return RATE_PER_DAY.multiply(BigDecimal.valueOf(overdueDays));
    }
}