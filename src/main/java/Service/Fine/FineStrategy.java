package Service.Fine;

import java.math.BigDecimal;

/**
 * Defines a strategy for calculating fines for overdue items.
 */
public interface FineStrategy {

    /**
     * Calculates the fine amount based on the number of overdue days.
     *
     * @param overdueDays number of days an item is overdue
     * @return calculated fine amount
     */
    BigDecimal calculateFine(int overdueDays);
}
