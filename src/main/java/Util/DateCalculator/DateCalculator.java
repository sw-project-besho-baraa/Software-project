package Util.DateCalculator;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateCalculator
{

    public static Date add(Date date,int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH,days);
        return cal.getTime();
    }

    public static int daysDifference(Date date1,Date date2)
    {

        LocalDate d1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate d2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return (int) ChronoUnit.DAYS.between(d1,d2);
    }
}
