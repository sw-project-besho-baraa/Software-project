package Util_Test.DataCalculator;

import Util.DateCalculator.DateCalculator;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class DateCalculator_Test {

    @Test
    void add_addsPositiveDays() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime result = DateCalculator.add(base, 5);
        assertEquals(LocalDateTime.of(2025, 1, 6, 10, 0), result);
    }

    @Test
    void add_addsNegativeDays() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 10, 10, 0);
        LocalDateTime result = DateCalculator.add(base, -3);
        assertEquals(LocalDateTime.of(2025, 1, 7, 10, 0), result);
    }

    @Test
    void add_addsZeroDays_returnsSameDate() {
        LocalDateTime base = LocalDateTime.of(2025, 1, 15, 12, 30);
        LocalDateTime result = DateCalculator.add(base, 0);
        assertEquals(base, result);
    }

    @Test
    void daysDifference_returnsZero_whenSameDate() {
        LocalDateTime date1 = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2025, 1, 1, 22, 0);
        assertEquals(0, DateCalculator.daysDifference(date1, date2));
    }

    @Test
    void daysDifference_returnsPositive_whenDate2AfterDate1() {
        LocalDateTime date1 = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2025, 1, 5, 10, 0);
        assertEquals(4, DateCalculator.daysDifference(date1, date2));
    }

    @Test
    void daysDifference_returnsNegative_whenDate2BeforeDate1() {
        LocalDateTime date1 = LocalDateTime.of(2025, 1, 10, 10, 0);
        LocalDateTime date2 = LocalDateTime.of(2025, 1, 5, 10, 0);
        assertEquals(-5, DateCalculator.daysDifference(date1, date2));
    }}