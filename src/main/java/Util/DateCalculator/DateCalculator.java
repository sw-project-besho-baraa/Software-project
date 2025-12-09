package Util.DateCalculator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateCalculator
{

    public static LocalDateTime add(LocalDateTime dateTime,long days)
    {
        return dateTime.plusDays(days);
    }

    public static long daysDifference(LocalDateTime date1, LocalDateTime date2)
    {

        return ChronoUnit.DAYS.between(date1.toLocalDate(), date2.toLocalDate());
    }
}
