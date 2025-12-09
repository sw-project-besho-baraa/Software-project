package Util_Test.DataCalculator;

import Util.DateCalculator.DateCalculator;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateCalculator_Test
{

    private Date buildDate(int year,int month,int dayOfMonth)
    {

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year,month - 1,dayOfMonth,0,0,0);
        cal.set(Calendar.MILLISECOND,0);
        return cal.getTime();
    }

    @Test
    void add_positiveDays_returnsDateInFuture()
    {
        Date base = buildDate(2024,1,10);
        Date result = DateCalculator.add(base,5);
        Date expected = buildDate(2024,1,15);
        assertEquals(expected,result);
    }

    @Test
    void add_negativeDays_returnsDateInPast()
    {
        Date base = buildDate(2024,1,10);
        Date result = DateCalculator.add(base,-7);
        Date expected = buildDate(2024,1,3);
        assertEquals(expected,result);
    }

    @Test
    void add_zeroDays_returnsSameDate()
    {
        Date base = buildDate(2024,3,5);
        Date result = DateCalculator.add(base,0);
        assertEquals(base,result);
    }

    @Test
    void daysDifference_sameDate_returnsZero()
    {
        Date d1 = buildDate(2024,2,20);
        Date d2 = buildDate(2024,2,20);
        int diff = DateCalculator.daysDifference(d1,d2);
        assertEquals(0,diff);
    }

    @Test
    void daysDifference_futureAfterPast_returnsPositive()
    {
        Date earlier = buildDate(2024,2,1);
        Date later = buildDate(2024,2,11);
        int diff = DateCalculator.daysDifference(earlier,later);
        assertEquals(10,diff);
    }

    @Test
    void daysDifference_pastAfterFuture_returnsNegative()
    {
        Date earlier = buildDate(2024,2,1);
        Date later = buildDate(2024,2,11);
        int diff = DateCalculator.daysDifference(later,earlier);
        assertEquals(-10,diff);
    }
}