package Util.MessageFormater;

import Service.MediaItem.Payment.PaymentConfirmationData;
import org.springframework.stereotype.Component;

@Component
public class PaymentConfirmationMessageFormater implements IMessageFormater<PaymentConfirmationData>
{

    @Override
    public String formatMessage(PaymentConfirmationData data)
    {
        if (data == null)
        {
            return "";
        }

        String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="en" dir="ltr">
                <head>
                    <meta charset="UTF-8">
                    <title>Library Management System · Payment Confirmation</title>
                    <style>
                        :root {
                            --bg: #020617;
                            --accent: #22c55e;
                            --accent-soft: rgba(34, 197, 94, 0.18);
                            --text-main: #e5e7eb;
                            --text-muted: #9ca3af;
                            --border-subtle: rgba(148, 163, 184, 0.35);
                            --radius-xl: 18px;
                        }

                        * {
                            box-sizing: border-box;
                        }

                        body {
                            margin: 0;
                            padding: 32px 12px;
                            font-family: system-ui, -apple-system, BlinkMacSystemFont, "SF Pro Text",
                            "Segoe UI", sans-serif;
                            background:
                                    radial-gradient(circle at top, #0b1120 0, #020617 52%),
                                    radial-gradient(circle at bottom, rgba(15, 23, 42, 0.9) 0, transparent 60%);
                            color: var(--text-main);
                        }

                        .wrapper {
                            width: 100%;
                            max-width: 640px;
                            margin: 0 auto;
                        }

                        .card {
                            background: radial-gradient(circle at top left, rgba(34, 197, 94, 0.18), transparent 55%),
                                        radial-gradient(circle at bottom right, rgba(15, 23, 42, 0.9), #020617);
                            border-radius: 28px;
                            border: 1px solid var(--border-subtle);
                            box-shadow:
                                    0 32px 90px rgba(15, 23, 42, 0.85),
                                    0 0 0 1px rgba(148, 163, 184, 0.16);
                            padding: 26px 22px 24px;
                            position: relative;
                            overflow: hidden;
                        }

                        .card-inner {
                            position: relative;
                            z-index: 1;
                        }

                        .badge {
                            display: inline-flex;
                            align-items: center;
                            gap: 8px;
                            padding: 4px 12px 4px 4px;
                            border-radius: 999px;
                            background: rgba(6, 78, 59, 0.9);
                            border: 1px solid rgba(52, 211, 153, 0.7);
                            box-shadow: 0 16px 40px rgba(6, 95, 70, 0.9);
                            margin-bottom: 16px;
                        }

                        .badge-logo {
                            height: 26px;
                            width: 26px;
                            border-radius: 999px;
                            display: inline-flex;
                            align-items: center;
                            justify-content: center;
                            background:
                                    radial-gradient(circle at 20% 20%, #bbf7d0, transparent 55%),
                                    radial-gradient(circle at 80% 80%, #22c55e, transparent 55%);
                            box-shadow: 0 0 0 1px rgba(15, 23, 42, 0.9);
                            color: #022c22;
                            font-size: 14px;
                            font-weight: 800;
                            letter-spacing: 0.03em;
                        }

                        .badge-text {
                            font-size: 11px;
                            text-transform: uppercase;
                            letter-spacing: 0.18em;
                            color: #bbf7d0;
                        }

                        .header {
                            display: flex;
                            align-items: flex-start;
                            justify-content: space-between;
                            gap: 12px;
                            margin-bottom: 16px;
                        }

                        .title-block {
                            display: flex;
                            flex-direction: column;
                            gap: 4px;
                        }

                        .title {
                            font-size: 22px;
                            font-weight: 700;
                            letter-spacing: 0.03em;
                            text-transform: uppercase;
                        }

                        .subtitle {
                            font-size: 13px;
                            color: var(--text-muted);
                        }

                        .status-pill {
                            padding: 6px 12px;
                            border-radius: 999px;
                            font-size: 11px;
                            text-transform: uppercase;
                            letter-spacing: 0.16em;
                            border: 1px solid rgba(34, 197, 94, 0.7);
                            background: linear-gradient(
                                    135deg,
                                    rgba(22, 163, 74, 0.22),
                                    rgba(6, 78, 59, 0.9)
                            );
                            color: #bbf7d0;
                            display: inline-flex;
                            align-items: center;
                            gap: 6px;
                            box-shadow: 0 20px 40px rgba(6, 95, 70, 0.8);
                        }

                        .status-dot {
                            width: 7px;
                            height: 7px;
                            border-radius: 999px;
                            background: #bbf7d0;
                            box-shadow: 0 0 0 4px rgba(34, 197, 94, 0.5);
                        }

                        .greeting {
                            margin-bottom: 16px;
                            font-size: 14px;
                            line-height: 1.6;
                            color: var(--text-main);
                        }

                        .greeting strong {
                            color: #e5e7eb;
                        }

                        .summary-grid {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 10px;
                            margin-bottom: 18px;
                        }

                        .summary-card {
                            flex: 1 1 180px;
                            min-width: 0;
                            border-radius: 16px;
                            padding: 10px 12px;
                            border: 1px solid rgba(34, 197, 94, 0.55);
                            background: radial-gradient(circle at top, rgba(15, 23, 42, 0.96), rgba(6, 78, 59, 0.9));
                        }

                        .summary-label {
                            font-size: 11px;
                            text-transform: uppercase;
                            letter-spacing: 0.16em;
                            color: var(--text-muted);
                            margin-bottom: 4px;
                        }

                        .summary-value {
                            font-size: 15px;
                            font-weight: 600;
                            color: #e5e7eb;
                        }

                        .summary-sub {
                            font-size: 12px;
                            margin-top: 2px;
                            color: #86efac;
                        }

                        .details-card {
                            margin-top: 6px;
                            border-radius: 18px;
                            border: 1px solid rgba(148, 163, 184, 0.35);
                            background: radial-gradient(circle at top left, rgba(15, 23, 42, 0.98), #020617);
                            padding: 14px 16px 12px;
                        }

                        .details-header {
                            font-size: 12px;
                            text-transform: uppercase;
                            letter-spacing: 0.16em;
                            color: var(--text-muted);
                            margin-bottom: 8px;
                        }

                        .details-table {
                            width: 100%;
                            border-collapse: collapse;
                            font-size: 13px;
                        }

                        .details-table tr + tr td {
                            border-top: 1px dashed rgba(55, 65, 81, 0.9);
                        }

                        .details-table td {
                            padding: 6px 0;
                        }

                        .details-label {
                            color: var(--text-muted);
                            width: 40%;
                        }

                        .details-value {
                            color: #e5e7eb;
                            text-align: right;
                            font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace;
                        }

                        .note {
                            margin-top: 14px;
                            font-size: 12px;
                            color: var(--text-muted);
                            line-height: 1.5;
                        }

                        .footer {
                            margin-top: 18px;
                            padding-top: 12px;
                            border-top: 1px dashed rgba(55, 65, 81, 0.9);
                            font-size: 11px;
                            color: var(--text-muted);
                        }

                        .footer-strong {
                            color: #e5e7eb;
                        }

                        .brand {
                            margin-top: 6px;
                            font-size: 11px;
                            color: rgba(148, 163, 184, 0.85);
                        }

                        @media (max-width: 600px) {
                            .card {
                                padding: 22px 16px 20px;
                                border-radius: 22px;
                            }

                            .header {
                                flex-direction: column;
                                align-items: flex-start;
                            }

                            .details-value {
                                text-align: left;
                                margin-top: 2px;
                            }
                        }
                    </style>
                </head>
                <body>
                <div class="wrapper">
                    <div class="card">
                        <div class="card-inner">
                            <div class="badge">
                                <div class="badge-logo">LM</div>
                                <div class="badge-text">LIBRARY MANAGEMENT · PAYMENT</div>
                            </div>

                            <div class="header">
                                <div class="title-block">
                                    <div class="title">Payment confirmation</div>
                                    <div class="subtitle">
                                        This email confirms your recent payment to the Library Management System.
                                    </div>
                                </div>
                                <div class="status-pill">
                                    <span class="status-dot"></span>
                                    PAYMENT RECORDED
                                </div>
                            </div>

                            <div class="greeting">
                                Hello <strong>{{patronName}}</strong>,<br />
                                We have successfully received your payment of
                                <strong>{{amountPaid}}</strong> on <strong>{{paymentDate}}</strong>.
                                Below you can find a summary of your receipt and updated balance.
                            </div>

                            <div class="summary-grid">
                                <div class="summary-card">
                                    <div class="summary-label">Amount paid</div>
                                    <div class="summary-value">{{amountPaid}}</div>
                                    <div class="summary-sub">{{currency}}</div>
                                </div>
                                <div class="summary-card">
                                    <div class="summary-label">Receipt ID</div>
                                    <div class="summary-value">{{receiptId}}</div>
                                    <div class="summary-sub">Keep this for your records</div>
                                </div>
                                <div class="summary-card">
                                    <div class="summary-label">Payment date</div>
                                    <div class="summary-value">{{paymentDate}}</div>
                                    <div class="summary-sub">Local library time</div>
                                </div>
                            </div>

                            <div class="details-card">
                                <div class="details-header">Payment details</div>
                                <table class="details-table" role="presentation" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td class="details-label">Payment method</td>
                                        <td class="details-value">{{paymentMethod}}</td>
                                    </tr>
                                    <tr>
                                        <td class="details-label">Transaction ID</td>
                                        <td class="details-value">{{transactionId}}</td>
                                    </tr>
                                    <tr>
                                        <td class="details-label">Processed by</td>
                                        <td class="details-value">{{processedBy}}</td>
                                    </tr>
                                    <tr>
                                        <td class="details-label">Previous balance</td>
                                        <td class="details-value">{{previousBalance}}</td>
                                    </tr>
                                    <tr>
                                        <td class="details-label">Payment amount</td>
                                        <td class="details-value">{{amountPaid}}</td>
                                    </tr>
                                    <tr>
                                        <td class="details-label">Remaining balance</td>
                                        <td class="details-value">{{remainingBalance}}</td>
                                    </tr>
                                </table>

                                <div class="note">
                                    If you believe any of the above information is incorrect, please contact your library
                                    staff and provide your <strong>Receipt ID</strong> and <strong>Transaction ID</strong>.
                                </div>
                            </div>

                            <div class="footer">
                                <div class="footer-strong">
                                    Thank you for keeping your account in good standing.
                                </div>
                                <div class="brand">
                                    Library Management System · Automated Payment Confirmation Email
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </body>
                </html>
                """;

        return htmlTemplate.replace("{{patronName}}",nullSafe(data.patronName()))
                .replace("{{amountPaid}}",nullSafe(data.amountPaid()))
                .replace("{{paymentDate}}",nullSafe(data.paymentDate()))
                .replace("{{receiptId}}",nullSafe(data.receiptId()))
                .replace("{{paymentMethod}}",nullSafe(data.paymentMethod()))
                .replace("{{transactionId}}",nullSafe(data.transactionId()))
                .replace("{{processedBy}}",nullSafe(data.processedBy()))
                .replace("{{previousBalance}}",nullSafe(data.previousBalance()))
                .replace("{{remainingBalance}}",nullSafe(data.remainingBalance()))
                .replace("{{currency}}",nullSafe(data.currency()));
    }

    private static String nullSafe(Object value)
    {
        return value == null ? "" : String.valueOf(value);
    }
}
