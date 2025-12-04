package Util.MessageFormater;

import DTO.UserDTO.UserContactDTO;
import Entity.Item;
import Service.Book.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.Book.OverdueBorrowDetection.OverdueBorrowedItemsData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GeneralOverdueBorrowMessageFormater implements IMessageFormater<OverdueBorrowedItemsData> {
    private static final SimpleDateFormat BORROWED_DATE_FORMAT =
            new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
    @Override
    public String formatMessage(OverdueBorrowedItemsData overdueBorrowedItemsData) {
        if (overdueBorrowedItemsData == null) {
            return "";
        }
        UserContactDTO user = overdueBorrowedItemsData.userContactDTO();
        List<OverdueBorrowedItem> items = overdueBorrowedItemsData.items();
        String userName = user != null && user.getName() != null && !user.getName().isBlank() ? user.getName() : "Valued Reader";
        String userEmail = user != null ? nullSafe(user.getEmail()) : "-";
        String userPhone = user != null ? nullSafe(user.getPhoneNumber()) : "-";
        int overdueCount = (items == null) ? 0 : items.size();
        StringBuilder rowsBuilder = new StringBuilder();
        if (items != null && !items.isEmpty()) {
            for (OverdueBorrowedItem overdueItem : items) {
                if (overdueItem == null) continue;

                Item item = overdueItem.item();
                String title = (item != null) ? nullSafe(item.getTitle()) : "Unknown item";
                String borrowedDateStr = "-";

                if (item != null) {
                    Date borrowedDate = item.getBorrowedDate();
                    if (borrowedDate != null) {
                        borrowedDateStr = BORROWED_DATE_FORMAT.format(borrowedDate);
                    }
                }
                String detectedAtStr = (overdueItem.detectedAt() != null) ? overdueItem.detectedAt().toString() : "-";
                int overdueDays = overdueItem.overdueDays();
                rowsBuilder.append("""
                       <tr class="item-row">
                       <td class="item-title">
                       <div class="item-main-title">""").append(escapeHtml(title))
                        .append("""
                            </div>
                            </td>
                            <td class="item-date">""")
                        .append(escapeHtml(borrowedDateStr))
                        .append("""
                            </td>
                            <td class="item-overdue">
                            """)
                        .append(overdueDays)
                        .append("""
                            days
                            </td>
                            <td class="item-detected">
                            """)
                        .append(escapeHtml(detectedAtStr))
                        .append("""
                            </td>
                        </tr>
                        """);
            }
        } else {
            rowsBuilder.append("""
                    <tr class="item-row empty-row">
                        <td colspan="4" class="empty-text">
                            You currently have no overdue items.
                        </td>
                    </tr>
                    """);
        }

        String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="en" dir="ltr">
                <head>
                    <meta charset="UTF-8">
                    <title>MOBO Library · Overdue Items Notice</title>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <style>
                        :root {
                            --bg: #020617;
                            --card-bg: #020617;
                            --accent: #38bdf8;
                            --accent-soft: rgba(56, 189, 248, 0.12);
                            --accent-strong: #0ea5e9;
                            --text-main: #e5e7eb;
                            --text-muted: #9ca3af;
                            --border-subtle: rgba(148, 163, 184, 0.35);
                            --danger: #f97373;
                            --danger-soft: rgba(248, 113, 113, 0.16);
                            --success: #22c55e;
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
                            background: radial-gradient(circle at top, #0b1120 0, #020617 52%);
                            color: var(--text-main);
                        }

                        .wrapper {
                            width: 100%;
                            max-width: 640px;
                            margin: 0 auto;
                        }

                        .card {
                            background: linear-gradient(145deg, #020617 0, #020617 45%, #020617 100%);
                            border-radius: 28px;
                            border: 1px solid var(--border-subtle);
                            box-shadow:
                                    0 32px 90px rgba(15, 23, 42, 0.85),
                                    0 0 0 1px rgba(148, 163, 184, 0.2);
                            padding: 28px 24px 26px;
                            position: relative;
                            overflow: hidden;
                        }

                        .glow {
                            position: absolute;
                            inset: -30%;
                            background:
                                    radial-gradient(circle at top left, rgba(56, 189, 248, 0.16), transparent 55%),
                                    radial-gradient(circle at bottom right, rgba(15, 118, 110, 0.2), transparent 55%);
                            opacity: 0.9;
                            pointer-events: none;
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
                            background: rgba(15, 23, 42, 0.82);
                            border: 1px solid rgba(148, 163, 184, 0.5);
                            box-shadow: 0 18px 40px rgba(15, 23, 42, 0.85);
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
                                    radial-gradient(circle at 20% 20%, #e0f2fe, transparent 55%),
                                    radial-gradient(circle at 80% 80%, #22c55e, transparent 55%),
                                    radial-gradient(circle at 40% 80%, #38bdf8, transparent 55%);
                            box-shadow: 0 0 0 1px rgba(15, 23, 42, 0.9);
                            color: #020617;
                            font-size: 14px;
                            font-weight: 800;
                            letter-spacing: 0.03em;
                        }

                        .badge-text {
                            font-size: 11px;
                            text-transform: uppercase;
                            letter-spacing: 0.18em;
                            color: var(--text-muted);
                        }

                        .header {
                            display: flex;
                            justify-content: space-between;
                            align-items: flex-start;
                            gap: 12px;
                            margin-bottom: 18px;
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
                            letter-spacing: 0.18em;
                            border: 1px solid rgba(248, 113, 113, 0.5);
                            background: linear-gradient(
                                    135deg,
                                    rgba(248, 113, 113, 0.26),
                                    rgba(127, 29, 29, 0.72)
                            );
                            color: #fee2e2;
                            display: inline-flex;
                            align-items: center;
                            gap: 6px;
                            box-shadow: 0 20px 40px rgba(127, 29, 29, 0.8);
                        }

                        .status-dot {
                            width: 7px;
                            height: 7px;
                            border-radius: 999px;
                            background: #fee2e2;
                            box-shadow: 0 0 0 4px rgba(248, 113, 113, 0.4);
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

                        .summary {
                            display: flex;
                            flex-wrap: wrap;
                            gap: 10px;
                            margin-bottom: 20px;
                        }

                        .summary-card {
                            flex: 1 1 160px;
                            min-width: 0;
                            border-radius: 16px;
                            padding: 10px 12px;
                            border: 1px solid rgba(148, 163, 184, 0.5);
                            background: radial-gradient(circle at top, rgba(15, 23, 42, 0.9), rgba(15, 23, 42, 0.82));
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

                        .summary-pill {
                            font-size: 12px;
                            margin-top: 2px;
                            color: #f97373;
                        }

                        .items-card {
                            margin-top: 10px;
                            border-radius: 18px;
                            border: 1px solid rgba(148, 163, 184, 0.35);
                            background: radial-gradient(circle at top left, rgba(15, 23, 42, 0.96), #020617);
                            overflow: hidden;
                        }

                        .items-header {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            padding: 12px 16px;
                            border-bottom: 1px solid rgba(148, 163, 184, 0.32);
                            background: linear-gradient(120deg, rgba(15, 23, 42, 0.9), rgba(15, 23, 42, 0.7));
                        }

                        .items-title {
                            font-size: 13px;
                            text-transform: uppercase;
                            letter-spacing: 0.16em;
                            color: var(--text-muted);
                        }

                        .items-count {
                            font-size: 13px;
                            color: #f97373;
                            font-weight: 500;
                        }

                        table.items-table {
                            width: 100%;
                            border-collapse: collapse;
                        }

                        table.items-table thead th {
                            text-align: left;
                            font-size: 11px;
                            text-transform: uppercase;
                            letter-spacing: 0.16em;
                            padding: 10px 16px 8px;
                            color: var(--text-muted);
                            border-bottom: 1px solid rgba(30, 64, 175, 0.5);
                            background: linear-gradient(
                                    135deg,
                                    rgba(30, 64, 175, 0.45),
                                    rgba(15, 23, 42, 0.95)
                            );
                        }

                        table.items-table tbody td {
                            padding: 10px 16px;
                            font-size: 13px;
                            border-bottom: 1px solid rgba(31, 41, 55, 0.9);
                        }

                        .item-row:last-child td {
                            border-bottom: none;
                        }

                        .item-title .item-main-title {
                            font-weight: 500;
                            color: #e5e7eb;
                        }

                        .item-date,
                        .item-detected {
                            font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, "Liberation Mono", "Courier New", monospace;
                            font-size: 12px;
                            color: var(--text-muted);
                        }

                        .item-overdue {
                            font-weight: 600;
                            color: #fecaca;
                        }

                        .empty-row td.empty-text {
                            text-align: center;
                            color: var(--text-muted);
                            font-size: 13px;
                            padding: 16px;
                        }

                        .footer {
                            margin-top: 22px;
                            padding-top: 14px;
                            border-top: 1px dashed rgba(55, 65, 81, 0.9);
                            font-size: 11px;
                            color: var(--text-muted);
                        }

                        .footer-strong {
                            color: #e5e7eb;
                        }

                        .contact-line {
                            margin-top: 4px;
                        }

                        .contact-line span {
                            color: #9ca3af;
                        }

                        .brand {
                            margin-top: 8px;
                            font-size: 11px;
                            color: rgba(148, 163, 184, 0.8);
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

                            table.items-table thead {
                                display: none;
                            }

                            table.items-table,
                            table.items-table tbody,
                            table.items-table tr,
                            table.items-table td {
                                display: block;
                                width: 100%;
                            }

                            .item-row {
                                padding: 10px 14px;
                                border-bottom: 1px solid rgba(31, 41, 55, 0.9);
                            }

                            .item-row td {
                                padding: 2px 0;
                                border: none;
                            }

                            .item-overdue {
                                margin-top: 6px;
                            }
                        }
                    </style>
                </head>
                <body>
                <div class="wrapper">
                    <div class="card">
                        <div class="glow"></div>
                        <div class="card-inner">
                            <div class="badge">
                                <div class="badge-logo">MO</div>
                                <div class="badge-text">MOBO LIBRARY · OVERDUE ALERT</div>
                            </div>

                            <div class="header">
                                <div class="title-block">
                                    <div class="title">Overdue borrowed items</div>
                                    <div class="subtitle">
                                        A quick summary of items that require your attention.
                                    </div>
                                </div>
                                <div class="status-pill">
                                    <span class="status-dot"></span>
                                    OVERDUE ITEMS
                                </div>
                            </div>

                            <div class="greeting">
                                Hello <strong>__USER_NAME__</strong>,<br />
                                Our system has detected that you currently have
                                <strong>__OVERDUE_COUNT__ overdue item(s)</strong> that are past their due date.
                                Please review the details below and return the items as soon as possible to avoid additional fines.
                            </div>

                            <div class="summary">
                                <div class="summary-card">
                                    <div class="summary-label">Total overdue</div>
                                    <div class="summary-value">__OVERDUE_COUNT__ item(s)</div>
                                    <div class="summary-pill">Immediate attention required</div>
                                </div>
                                <div class="summary-card">
                                    <div class="summary-label">Contact email</div>
                                    <div class="summary-value">__USER_EMAIL__</div>
                                </div>
                                <div class="summary-card">
                                    <div class="summary-label">Phone</div>
                                    <div class="summary-value">__USER_PHONE__</div>
                                </div>
                            </div>

                            <div class="items-card">
                                <div class="items-header">
                                    <div class="items-title">Overdue items details</div>
                                    <div class="items-count">
                                        __OVERDUE_COUNT__ item(s)
                                    </div>
                                </div>

                                <table class="items-table" role="presentation" cellspacing="0" cellpadding="0">
                                    <thead>
                                    <tr>
                                        <th scope="col">Title</th>
                                        <th scope="col">Borrowed at</th>
                                        <th scope="col">Overdue</th>
                                        <th scope="col">Detected at</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    __ITEM_ROWS__
                                    </tbody>
                                </table>
                            </div>

                            <div class="footer">
                                <div class="footer-strong">
                                    Please visit MOBO Library or contact us if you believe any of this information is incorrect.
                                </div>
                                <div class="contact-line">
                                    <span>MOBO Library · Automated Overdue Notification System</span>
                                </div>
                                <div class="brand">
                                    Thank you for being part of MOBO Library. We appreciate your commitment to returning items on time.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </body>
                </html>
                """;

        return htmlTemplate
                .replace("__USER_NAME__", escapeHtml(userName))
                .replace("__OVERDUE_COUNT__", String.valueOf(overdueCount))
                .replace("__USER_EMAIL__", escapeHtml(userEmail))
                .replace("__USER_PHONE__", escapeHtml(userPhone))
                .replace("__ITEM_ROWS__", rowsBuilder.toString());
    }

    private static String nullSafe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    private static String escapeHtml(String input) {
        if (input == null) return "";
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
