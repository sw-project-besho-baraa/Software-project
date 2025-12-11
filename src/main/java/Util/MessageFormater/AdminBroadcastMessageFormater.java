package Util.MessageFormater;

import DTO.UserDTO.UserContactDTO;
import Service.BroadcastMessage.AdminBroadcastMessageData;
import org.springframework.stereotype.Component;

/**
 * Formats HTML email messages for admin broadcasts sent to users.
 * <p>
 * This formatter generates a styled email template that includes
 * the admin’s message and the recipient’s name.
 */
@Component
public class AdminBroadcastMessageFormater implements IMessageFormater<AdminBroadcastMessageData> {

    /**
     * Builds an HTML-formatted admin broadcast message.
     *
     * @param data the message data containing the recipient and message text
     * @return a complete HTML string ready for sending by email
     */
    @Override
    public String formatMessage(AdminBroadcastMessageData data) {
        if (data == null || data.userContactDTO() == null) {
            return "";
        }

        UserContactDTO user = data.userContactDTO();
        String message = data.message() == null ? "" : data.message();
        String userName = (user.getName() != null && !user.getName().isBlank()) ? user.getName() : "Valued Reader";

        String escapedMessage = escapeHtml(message).replace("\n", "<br/>");

        String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="en" dir="ltr">
                <head>
                    <meta charset="UTF-8">
                    <title>Library Management System · Admin Message</title>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <style>
                        /* simplified Tailwind-like theme */
                        :root {
                            --bg: #020617;
                            --card-bg: #020617;
                            --accent: #38bdf8;
                            --text-main: #eeeeee;
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
                            background: var(--card-bg);
                            border: 1px solid var(--border-subtle);
                            border-radius: 28px;
                            padding: 28px 24px;
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
                        .title { font-size: 22px; font-weight: 700; text-transform: uppercase; }
                        .message-box {
                            margin-top: 10px;
                            padding: 14px 16px;
                            border-radius: 18px;
                            border: 1px solid var(--border-subtle);
                            background: rgba(15, 23, 42, 0.9);
                        }
                    </style>
                </head>
                <body>
                    <div class="wrapper">
                        <div class="card">
                            <div class="badge">
                                <div class="badge-logo">MO</div>
                                <div class="badge-text">MOBO LIBRARY · ADMIN</div>
                            </div>
                            <div class="title">Admin message</div>
                            <p>Hello <strong>__USER_NAME__</strong>,</p>
                            <div class="message-box">__MESSAGE_BODY__</div>
                        </div>
                    </div>
                </body>
                </html>
                """;

        return htmlTemplate
                .replace("__USER_NAME__", escapeHtml(userName))
                .replace("__MESSAGE_BODY__", escapedMessage);
    }

    /**
     * Escapes HTML special characters to prevent injection.
     *
     * @param input the input string
     * @return an escaped HTML-safe string
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
