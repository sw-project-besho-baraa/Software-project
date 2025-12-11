package Util.MessageFormater;

import DTO.UserDTO.UserContactDTO;
import Entity.MediaItem;
import Service.OverdueBorrowDetection.OverdueBorrowedItem;
import Service.OverdueBorrowDetection.OverdueBorrowedItemsData;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Formats HTML email messages notifying users of overdue borrowed items.
 * <p>
 * This formatter builds a visually rich, mobile-friendly HTML email that includes
 * user details, summary statistics, and a table of overdue media items.
 */
@Component
public class GeneralOverdueBorrowMessageFormater implements IMessageFormater<OverdueBorrowedItemsData> {

    private static final SimpleDateFormat BORROWED_DATE_FORMAT =
            new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

    /**
     * Formats a complete overdue notification message in HTML.
     *
     * @param overdueBorrowedItemsData the data containing the user and their overdue items
     * @return the HTML-formatted message ready for email sending
     */
    @Override
    public String formatMessage(OverdueBorrowedItemsData overdueBorrowedItemsData) {
        if (overdueBorrowedItemsData == null) {
            return "";
        }

        var user = overdueBorrowedItemsData.user();
        List<OverdueBorrowedItem> items = overdueBorrowedItemsData.items();

        String userName = (user != null && user.getName() != null && !user.getName().isBlank())
                ? user.getName() : "Valued Reader";
        String userEmail = user != null ? nullSafe(user.getEmail()) : "-";
        int overdueCount = (items == null) ? 0 : items.size();

        StringBuilder rowsBuilder = new StringBuilder();
        if (items != null && !items.isEmpty()) {
            for (OverdueBorrowedItem overdueItem : items) {
                if (overdueItem == null)
                    continue;

                MediaItem item = overdueItem.item();
                String title = (item != null) ? nullSafe(item.getTitle()) : "Unknown item";
                String borrowedDateStr = "-";

                if (item != null && item.getBorrowedDate() != null) {
                    borrowedDateStr = BORROWED_DATE_FORMAT.format(item.getBorrowedDate());
                }

                String detectedAtStr = (overdueItem.detectedAt() != null)
                        ? overdueItem.detectedAt().toString() : "-";
                long overdueDays = overdueItem.overdueDays();

                rowsBuilder.append("""
                        <tr class="item-row">
                            <td class="item-title"><div class="item-main-title">""")
                        .append(escapeHtml(title))
                        .append("</div></td>")
                        .append("<td class=\"item-date\">").append(escapeHtml(borrowedDateStr)).append("</td>")
                        .append("<td class=\"item-overdue\">").append(overdueDays).append(" days</td>")
                        .append("<td class=\"item-detected\">").append(escapeHtml(detectedAtStr)).append("</td>")
                        .append("</tr>");
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
                        /* Tailwind-like dark luxury theme */
                        :root {
                            --bg: #020617;
                            --card-bg: #020617;
                            --accent: #38bdf8;
                            --text-main: #e5e7eb;
                            --text-muted: #9ca3af;
                            --danger: #f97373;
                            --border-subtle: rgba(148, 163, 184, 0.35);
                        }
                        body {
                            margin: 0;
                            padding: 32px 12px;
                            font-family: "Segoe UI", system-ui, sans-serif;
                            background: radial-gradient(circle at top, #0b1120 0, #020617 52%);
                            color: var(--text-main);
                        }
                        .wrapper { max-width: 640px; margin: 0 auto; }
                        .card {
                            border: 1px solid var(--border-subtle);
                            border-radius: 28px;
                            padding: 28px 24px;
                            background: var(--card-bg);
                        }
                        .badge {
                            display: inline-flex;
                            align-items: center;
                            gap: 8px;
                            margin-bottom: 16px;
                        }
                        .badge-logo {
                            height: 26px; width: 26px;
                            border-radius: 50%;
                            background: radial-gradient(circle at 20% 20%, #e0f2fe, transparent 55%),
                                        radial-gradient(circle at 80% 80%, #38bdf8, transparent 55%);
                            font-weight: 800;
                            font-size: 14px;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            color: #020617;
                        }
                        .items-table { width: 100%; border-collapse: collapse; }
                        .items-table th, .items-table td {
                            padding: 8px 16px;
                            border-bottom: 1px solid rgba(31,41,55,0.9);
                        }
                        .item-overdue { color: var(--danger); font-weight: 600; }
                    </style>
                </head>
                <body>
                    <div class="wrapper">
                        <div class="card">
                            <div class="badge">
                                <div class="badge-logo">MO</div>
                                <div class="badge-text">MOBO LIBRARY · OVERDUE ALERT</div>
                            </div>
                            <p>Hello <strong>__USER_NAME__</strong>,</p>
                            <p>Our system has detected <strong>__OVERDUE_COUNT__ overdue item(s)</strong> that need your attention.</p>
                            <table class="items-table">
                                <thead>
                                    <tr><th>Title</th><th>Borrowed at</th><th>Overdue</th><th>Detected at</th></tr>
                                </thead>
                                <tbody>__ITEM_ROWS__</tbody>
                            </table>
                            <p style="font-size:11px;color:#9ca3af;margin-top:20px;">
                                MOBO Library · Automated Overdue Notification System
                            </p>
                        </div>
                    </div>
                </body>
                </html>
                """;

        return htmlTemplate
                .replace("__USER_NAME__", escapeHtml(userName))
                .replace("__OVERDUE_COUNT__", String.valueOf(overdueCount))
                .replace("__ITEM_ROWS__", rowsBuilder.toString());
    }

    /**
     * Returns a non-null safe string for template replacement.
     *
     * @param value the original string value, which may be {@code null} or blank
     * @return "-" if the value is {@code null} or blank; otherwise the original value
     */
    private static String nullSafe(String value) {
        return value == null || value.isBlank() ? "-" : value;
    }

    /**
     * Escapes basic HTML entities to prevent injection.
     *
     * @param input the raw input string, which may be {@code null}
     * @return the escaped string safe for HTML contexts, or an empty string if input is {@code null}
     */
    private static String escapeHtml(String input) {
        if (input == null)
            return "";
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
