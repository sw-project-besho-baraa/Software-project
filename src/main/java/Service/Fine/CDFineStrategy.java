package Service.Fine;

import java.math.BigDecimal;

/**
 * Fine calculation strategy for overdue CDs.
 */
public class CDFineStrategy implements FineStrategy {

    /** Fine rate per overdue day (20 NIS). */
    private static final BigDecimal RATE_PER_DAY = BigDecimal.valueOf(20);

    /**
     * Calculates the fine based on overdue days.
     *
     * @param overdueDays number of days the CD is overdue
     * @return total fine amount
     */
    @Override
    public BigDecimal calculateFine(int overdueDays) {
        if (overdueDays <= 0)
            return BigDecimal.ZERO;
        return RATE_PER_DAY.multiply(BigDecimal.valueOf(overdueDays));
    }
}
