package Util.DateCalculator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for basic date and time calculations.
 */
public class DateCalculator {

    /**
     * Adds a number of days to the given date.
     *
     * @param dateTime the original date
     * @param days     the number of days to add
     * @return the new {@link LocalDateTime} after adding days
     */
    public static LocalDateTime add(LocalDateTime dateTime, long days) {
        return dateTime.plusDays(days);
    }

    /**
     * Calculates the difference in days between two dates.
     *
     * @param date1 the first date
     * @param date2 the second date
     * @return number of days between the two dates
     * @see ChronoUnit#DAYS
     */
    public static long daysDifference(LocalDateTime date1, LocalDateTime date2) {
        return ChronoUnit.DAYS.between(date1.toLocalDate(), date2.toLocalDate());
    }
}
