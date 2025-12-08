package Service.Fine;

import java.math.BigDecimal;

public interface FineStrategy {
    BigDecimal calculateFine(int overdueDays);
}
