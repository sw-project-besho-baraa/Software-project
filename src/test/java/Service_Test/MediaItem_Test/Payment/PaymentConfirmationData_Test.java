package Service_Test.MediaItem_Test.Payment;

import Service.Payment.PaymentConfirmationData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentConfirmationData_Test
{

    @Test
    void record_storesAndReturnsAllFields()
    {
        PaymentConfirmationData data = new PaymentConfirmationData("Mohammad", "20.0", "2025-12-09", "R123", "Cash",
                "T789", "Admin", "50.0", "30.0", "NIS");

        assertEquals("Mohammad",data.patronName());
        assertEquals("20.0",data.amountPaid());
        assertEquals("2025-12-09",data.paymentDate());
        assertEquals("R123",data.receiptId());
        assertEquals("Cash",data.paymentMethod());
        assertEquals("T789",data.transactionId());
        assertEquals("Admin",data.processedBy());
        assertEquals("50.0",data.previousBalance());
        assertEquals("30.0",data.remainingBalance());
        assertEquals("NIS",data.currency());
    }
}