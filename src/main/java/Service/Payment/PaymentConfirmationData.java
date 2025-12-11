package Service.Payment;

/**
 * Holds information about a user's payment confirmation details.
 *
 * @param patronName
 *            name of the user who made the payment
 * @param amountPaid
 *            amount that was paid
 * @param paymentDate
 *            date of the payment
 * @param receiptId
 *            unique receipt identifier
 * @param paymentMethod
 *            method used for the payment
 * @param transactionId
 *            transaction reference ID
 * @param processedBy
 *            name of the staff or system that processed the payment
 * @param previousBalance
 *            user's balance before payment
 * @param remainingBalance
 *            user's balance after payment
 * @param currency
 *            currency of the transaction
 */
public record PaymentConfirmationData(String patronName, String amountPaid, String paymentDate, String receiptId,
        String paymentMethod, String transactionId, String processedBy, String previousBalance, String remainingBalance,
        String currency) {
}