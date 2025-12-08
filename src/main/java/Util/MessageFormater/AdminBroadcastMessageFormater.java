package Util.MessageFormater;

import DTO.UserDTO.UserContactDTO;
import Service.BroadcastMessage.AdminBroadcastMessageData;
import org.springframework.stereotype.Component;

@Component
public class AdminBroadcastMessageFormater implements IMessageFormater<AdminBroadcastMessageData> {

    @Override
    public String formatMessage(AdminBroadcastMessageData data) {
        if (data == null || data.userContactDTO() == null) {
            return "";
        }

        UserContactDTO user = data.userContactDTO();
        String message = data.message() == null ? "" : data.message();
        String userName = (user.getName() != null && !user.getName().isBlank())
                ? user.getName()
                : "Valued Reader";

        String escapedMessage = escapeHtml(message).replace("\n", "<br/>");

        String htmlTemplate = """
                <!DOCTYPE html>
                <html lang="en" dir="ltr">
                <head>
                    <meta charset="UTF-8">
                    <title> Library Management System · Admin Message</title>
                    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                    <style>
                        :root {
                            --bg: #020617;
                            --card-bg: #020617;
                            --accent: #38bdf8;
                            --accent-soft: rgba(56, 189, 248, 0.12);
                            --accent-strong: #eeeeee;
                            --text-main: #eeeeee;
                            --text-muted: #eeeeee;
                            --border-subtle: rgba(148, 163, 184, 0.35);
                            --radius-xl: 18px;
                        }

                        * { box-sizing: border-box; }

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
                                    radial-gradient(circle at 80% 80%, #38bdf8, transparent 55%);
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

                        .title {
                            font-size: 22px;
                            font-weight: 700;
                            letter-spacing: 0.03em;
                            text-transform: uppercase;
                            margin-bottom: 6px;
                        }

                        .subtitle {
                            font-size: 13px;
                            color: var(--text-muted);
                            margin-bottom: 18px;
                        }

                        .greeting {
                            margin-bottom: 12px;
                            font-size: 14px;
                            line-height: 1.6;
                        }

                        .message-box {
                            margin-top: 10px;
                            padding: 14px 16px;
                            border-radius: 18px;
                            border: 1px solid rgba(148, 163, 184, 0.35);
                            background: radial-gradient(circle at top left, rgba(15, 23, 42, 0.96), #020617);
                            font-size: 14px;
                            line-height: 1.7;
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
                        <div class="subtitle">
                            An important announcement from MOBO Library administration.
                        </div>

                        <div class="greeting">
                            Hello <strong>__USER_NAME__</strong>,
                        </div>

                        <div class="message-box">
                            __MESSAGE_BODY__
                        </div>

                        
                    </div>
                </div>
                </body>
                </html>
                """;

        return htmlTemplate
                .replace("__USER_NAME__", escapeHtml(userName))
                .replace("__MESSAGE_BODY__", escapedMessage);
    }

    private static String escapeHtml(String input) {
        if (input == null) return "";
        return input
                .replace("&","&amp;")
                .replace("<","&lt;")
                .replace(">","&gt;")
                .replace("\"","&quot;");
    }
}