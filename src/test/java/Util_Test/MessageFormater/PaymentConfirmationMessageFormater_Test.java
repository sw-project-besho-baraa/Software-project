package Util_Test.MessageFormater;

import Service.MediaItem.Payment.PaymentConfirmationData;
import Util.MessageFormater.PaymentConfirmationMessageFormater;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PaymentConfirmationMessageFormater_Test
{

    private final PaymentConfirmationMessageFormater formatter = new PaymentConfirmationMessageFormater();

    @Test
    void formatMessage_nullData_returnsEmptyString()
    {
        String result = formatter.formatMessage(null);
        assertEquals("",result);
    }

    @Test
    void formatMessage_filledData_replacesPlaceholders()
    {
        PaymentConfirmationData data = mock(PaymentConfirmationData.class);
        when(data.patronName()).thenReturn("Mohammad");
        when(data.amountPaid()).thenReturn("$10");
        when(data.paymentDate()).thenReturn("2025-12-09");
        when(data.receiptId()).thenReturn("R-123");
        when(data.paymentMethod()).thenReturn("Cash");
        when(data.transactionId()).thenReturn("TX-999");
        when(data.processedBy()).thenReturn("Librarian A");
        when(data.previousBalance()).thenReturn("$20");
        when(data.remainingBalance()).thenReturn("$10");
        when(data.currency()).thenReturn("USD");
        String result = formatter.formatMessage(data);
        assertTrue(result.contains("Mohammad"));
        assertTrue(result.contains("$10"));
        assertTrue(result.contains("R-123"));
        assertFalse(result.contains("{{patronName}}"));
        assertFalse(result.contains("{{amountPaid}}"));
    }

    @Test
    void formatMessage_nullFields_usesEmptyStringInsteadOfNull()
    {
        PaymentConfirmationData data = mock(PaymentConfirmationData.class);
        when(data.patronName()).thenReturn(null);
        when(data.amountPaid()).thenReturn(null);
        when(data.paymentDate()).thenReturn(null);
        when(data.receiptId()).thenReturn(null);
        when(data.paymentMethod()).thenReturn(null);
        when(data.transactionId()).thenReturn(null);
        when(data.processedBy()).thenReturn(null);
        when(data.previousBalance()).thenReturn(null);
        when(data.remainingBalance()).thenReturn(null);
        when(data.currency()).thenReturn(null);
        String result = formatter.formatMessage(data);
        assertFalse(result.contains("{{patronName}}"));
        assertFalse(result.contains("null"));
    }
}
