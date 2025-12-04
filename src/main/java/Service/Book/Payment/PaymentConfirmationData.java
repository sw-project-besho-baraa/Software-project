package Service.Book.Payment;

public record PaymentConfirmationData(
        String patronName,
        String amountPaid,
        String paymentDate,
        String receiptId,
        String paymentMethod,
        String transactionId,
        String processedBy,
        String previousBalance,
        String remainingBalance,
        String currency
) { }